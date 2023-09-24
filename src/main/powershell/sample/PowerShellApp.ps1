. .\MessageExchangeClient.ps1

$global:count = 0

function MyFunction {
  
  Write-Host "[MyFunction] Received parameter: $RequestString"
  $global:count++
  return $RequestString.Trim() + " " + $global:count
}

$serverAddress = "127.0.0.1"

Process-Operation -operation { MyFunction } -serverAddress $serverAddress -serverPortString $Args[0] 