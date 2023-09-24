"use strict";
exports.__esModule = true;
var MessageExchangeClient_1 = require("./MessageExchangeClient");
var counter = 0;
function MyFunction(message) {
    console.log("Server sent:" + message);
    return 'Hello Server - ' + counter++;
}
(0, MessageExchangeClient_1.ProcessOperation)(MyFunction);
