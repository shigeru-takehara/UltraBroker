"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ProcessOperation = void 0;
const MESSAGE_DELIMITER = "__DEL__";
const MESSAGE_END_OF_PROCESS = "__EOP__";
function ProcessOperation(callBack) {
    let buffer = ''; // Buffer to collect incoming data
    let serverRequest = '';
    // Listen for data from the parent process via stdin
    process.stdin.on('data', (data) => {
        buffer += data.toString(); // Collect incoming data
        // Check if the received data contains newline characters
        if (buffer.includes('\n')) {
            // Split the data into lines
            const lines = buffer.split('\n');
            // Process each line
            for (let i = 0; i < lines.length - 1; i++) {
                const line = lines[i];
                if (line.includes(MESSAGE_DELIMITER)) {
                    let response = callBack(serverRequest);
                    response += MESSAGE_DELIMITER;
                    console.log(response);
                    serverRequest = '';
                }
                else if (!line.includes(MESSAGE_END_OF_PROCESS)) {
                    serverRequest += line + "\n";
                    //console.log('Child received message from parent:', line);
                }
                else if (buffer.includes(MESSAGE_END_OF_PROCESS)) {
                    process.exit(0);
                }
            }
            // Update the buffer with any remaining data
            buffer = lines[lines.length - 1];
        }
    });
    // Keep the process alive by running an empty loop
    setInterval(() => { }, 1000); // Adjust the interval as needed
}
exports.ProcessOperation = ProcessOperation;
