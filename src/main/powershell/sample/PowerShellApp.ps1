. .\MessageExchangeClient.ps1

function MyFunction {
  
  Write-Host "[MyFunction] Received parameter: $RequestString"
  
  return $RequestString
}

$serverAddress = "127.0.0.1"

Process-Operation -operation { MyFunction } -serverAddress $serverAddress -serverPortString $Args[0] 