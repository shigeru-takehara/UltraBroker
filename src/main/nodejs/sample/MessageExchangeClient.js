"use strict";
exports.__esModule = true;
exports.ProcessOperation = void 0;
var net = require("net");
function ProcessOperation(callBack) {
    var port;
    var args = process.argv.slice(2);
    if (args.length > 0) {
        //  console.log('Run time parameter : ' + args[0]);
    }
    else {
        console.log('no param');
        throw new Error('Need to provide socket port number.');
    }
    var HOST = 'localhost';
    var MAX_RETRIES = 600; // Adjust the number of retries as needed
    var RETRY_INTERVAL = 100; // Milliseconds
    var END_MESSAGE = '\n';
    var client = new net.Socket();
    port = parseInt(args[0], 10);
    var retries = 0;
    var doConnect = function () {
        //  console.log('Do connect will be called. try=' + retries);
        client.connect(port, HOST, function () {
        });
        client.setNoDelay(true);
    };
    doConnect();
    client.on('error', function (err) {
        if (retries < MAX_RETRIES) {
            retries++;
            //      console.log(`Retrying in ${RETRY_INTERVAL}ms (Attempt ${retries} of ${MAX_RETRIES})`);
            setTimeout(doConnect, RETRY_INTERVAL);
        }
        else {
            throw new Error('Max retries reached. Exiting.');
        }
    });
    client.on('data', function (data) {
        var msg = getMessage(data.toString());
        if (msg == '__EOP__') { // this is server's "closing" message
            return;
        }
        var resp = callBack(msg);
        client.write(resp + END_MESSAGE);
    });
    client.on('close', function () {
    });
}
exports.ProcessOperation = ProcessOperation;
function getMessage(val) {
    return val.trim();
}
