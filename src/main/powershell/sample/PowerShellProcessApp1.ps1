$global:count = 0

# Example custom ProcessRequest function
$customProcessRequest = {
    param ($request)
    # Your custom processing logic here
	$global:count++
    return "Custom processing: $request " + $global:count
}

# Run MessageProcessing.ps1 with the custom ProcessRequest function
.\ProcessMessageExchangeClient.ps1 -ProcessRequest $customProcessRequest
