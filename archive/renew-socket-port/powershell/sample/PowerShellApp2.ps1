. .\MessageExchangeClient.ps1

function MyFunction {

# Define the path to your Excel file
$excelFilePath = "C:\0\Book1.xlsx"

# Create a new Excel application object
$excel = New-Object -ComObject Excel.Application

# Make Excel visible (optional, you can keep it hidden)
$excel.Visible = $false

# Open the Excel file
$workbook = $excel.Workbooks.Open($excelFilePath)

# Get the active worksheet (you can specify a specific worksheet if needed)
$worksheet = $workbook.ActiveSheet

# Get the last used row and column
$lastRow = $worksheet.UsedRange.Rows.Count
$lastColumn = $worksheet.UsedRange.Columns.Count

# Define today's date in the desired format
$todayDate = Get-Date -Format "yyyy-MM-dd"

# Iterate through rows to find today's message
$todayMessage = $null

for ($row = 1; $row -le $lastRow; $row++) {
    $cellValue = $worksheet.Cells.Item($row, 1).Text

    if ($cellValue -eq $todayDate) {
        $todayMessage = $worksheet.Cells.Item($row, 2).Text
        break
    }
}

# Close and quit Excel
$workbook.Close()
$excel.Quit()

# Release COM objects
[System.Runtime.Interopservices.Marshal]::ReleaseComObject($worksheet) | Out-Null
[System.Runtime.Interopservices.Marshal]::ReleaseComObject($workbook) | Out-Null
[System.Runtime.Interopservices.Marshal]::ReleaseComObject($excel) | Out-Null

# Display today's message
if ($todayMessage) {
    Write-Host "Today's Message: $todayMessage"
} else {
    Write-Host "No message found for today."
}

return $todayMessage
  
}

$serverAddress = "127.0.0.1"

Process-Operation -operation { MyFunction } -serverAddress $serverAddress -serverPortString $Args[0] 