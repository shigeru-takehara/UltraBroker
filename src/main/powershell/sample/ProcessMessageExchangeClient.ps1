# MessageProcessing.ps1

param (
    [ScriptBlock] $ProcessRequest
)

# Define constants
$MESSAGE_DELIMITER = "__DEL__"
$MESSAGE_END_OF_PROCESS = "__EOP__"

# Function to get a request from the user (stdin)
function GetServerRequest {
    $inputData = [Console]::ReadLine()
    return $inputData
}

# Function to reply with a response and delimiter
function ReplyResponse($response) {
    Write-Host "$response"
    Write-Host "$MESSAGE_DELIMITER"
}

# Function to process messages
function ProcessMessage {
    try {
         $message = ""

        while ($true) {
            $inputData = GetServerRequest

            if ($inputData -eq $null) {
                break
            }

            if ($inputData -eq $MESSAGE_END_OF_PROCESS) {
                break
            }

            if ($inputData -eq $MESSAGE_DELIMITER) {
                $message = & $ProcessRequest $message
                ReplyResponse $message
                $message = ""
                continue
            }

            if ($message -eq "") {
              $message = $inputData
            }
            else {
              $message +=  "`n$inputData"
            }
        }
    } catch {
        Write-Host $_.Exception.Message
    }
}

# Call the main message processing function
ProcessMessage
