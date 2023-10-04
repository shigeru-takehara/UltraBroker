$MESSAGE_END_OF_PROCESS = "__EOP__"

function Get-TcpClientConnection {
    param (
        $serverAddress,
        $serverPort
    )
    
    $maxRetries = 60
    $retryCount = 0
    
    while ($retryCount -lt $maxRetries) {
        try {
            $client = New-Object System.Net.Sockets.TcpClient
            $client.Connect($serverAddress, $serverPort)

            if ($client.Connected) {
                Write-Host "Connected to $serverAddress on port $serverPort"
                return $client
            }
        } catch {
            Write-Host "Connection attempt $retryCount failed. Retrying..."
            $retryCount++
        }
        
        Start-Sleep -Seconds 1
    }
    
    Write-Host "Connection attempts exhausted. Exiting."
    return $null
}
$RequestString = '' # used in the passed operation (global variable)

function Process-Operation {
    param (
        [scriptblock]$operation,
        [string]$serverAddress,
        [string]$serverPortString
    )

  $serverPort = [int]$serverPortString
  
  $tcpConnection = Get-TcpClientConnection -serverAddress $serverAddress -serverPort $serverPort
  
  $tcpStream = $tcpConnection.GetStream()
  
  $writer = New-Object System.IO.StreamWriter($tcpStream)
  $writer.AutoFlush = $true

  $buffer = New-Object System.Byte[] 1024
  
  while ($true) {
    if ($tcpConnection.Connected) {
        while (!$tcpStream.DataAvailable ) {
            Start-Sleep -Milliseconds 100
        }

       $RequestString = ""
       while($tcpStream.DataAvailable ) {
         $byteRead = $tcpStream.read($Buffer, 0, $buffer.length)
         $RequestString = $RequestString + [System.Text.Encoding]::UTF8.GetString($buffer, 0, $byteRead)
       }
       $RequestString = $RequestString.Substring(0,$RequestString.Length-1)
       
        if ($RequestString.StartsWith($MESSAGE_END_OF_PROCESS)) {
            Write-Host "Received 'end of process' message. Exiting message exchange."
            break
        }

        $ResponseString = & $operation

        $writer.WriteLine($ResponseString)
 
    }
  }
  # Close the network stream and TcpClient
  
  $writer.Close()
  $tcpStream.Close()
  $tcpConnection.Close()
}
