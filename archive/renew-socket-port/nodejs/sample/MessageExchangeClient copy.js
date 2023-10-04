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
    var RETRY_INTERVAL = 1000; // Milliseconds
    var END_MESSAGE = '\n';
    var MESSAGE_END_OF_PROCESS = "__EOP__";
    var MESSAGE_RENEW_PORT_NUMBER = "__RPN__";
    var client = new net.Socket();
    var client2 = new net.Socket();
    port = parseInt(args[0], 10);
    var retries = 0;
    var doConnect = function () {
        //    console.log('Do connect will be called. try=' + retries);
        client.connect(port, HOST, function () {
        });
    };
    doConnect();
    client.on('error', function (err) {
        console.log('error occurred:' + err.message);
        if (retries < MAX_RETRIES) {
            retries++;
            console.log("Retrying in ".concat(RETRY_INTERVAL, "ms (Attempt ").concat(retries, " of ").concat(MAX_RETRIES, ")"));
            setTimeout(doConnect, RETRY_INTERVAL);
        }
        else {
            //      throw new Error('Max retries reached. Exiting.');
        }
    });
    client.on('data', function (data) {
        var msg = getMessage(data.toString());
        if (msg == MESSAGE_END_OF_PROCESS) { // this is server's "closing" message
            return;
        }
        else if (msg.search(MESSAGE_RENEW_PORT_NUMBER) > -1) { // this is server's "closing" message
            msg = msg.replace(MESSAGE_RENEW_PORT_NUMBER, "");
            port = parseInt(msg, 10);
            //      console.log('new port =' + port);
            client.end();
            client2.connect(port, HOST, function () {
            });
        }
        else {
            var resp = callBack(msg);
            //     console.log('client info=' + client.localPort)
            client.write(resp + END_MESSAGE);
            console.log('sent response to server:' + resp);
        }
    });
    client.on('close', function () {
        //    console.log('closed ')
    });
    client.on('connect', function () {
        //    console.log('Connected')
    });
    client2.on('data', function (data) {
        var msg = getMessage(data.toString());
        if (msg == MESSAGE_END_OF_PROCESS) { // this is server's "closing" message
            return;
        }
        else if (msg.search(MESSAGE_RENEW_PORT_NUMBER) > -1) { // this is server's "closing" message
            msg = msg.replace(MESSAGE_RENEW_PORT_NUMBER, "");
            port = parseInt(msg, 10);
            //      console.log('new port =' + port);
            client2.end();
            client.connect(port, HOST, function () {
            });
        }
        else {
            var resp = callBack(msg);
            //     console.log('client info=' + client2.localPort)
            client2.write(resp + END_MESSAGE);
            console.log('sent response to server:' + resp);
        }
    });
    client2.on('close', function () {
        //    console.log('closed ')
    });
    client2.on('connect', function () {
        //    console.log('Connected')
    });
}
exports.ProcessOperation = ProcessOperation;
function getMessage(val) {
    return val.trim();
}
