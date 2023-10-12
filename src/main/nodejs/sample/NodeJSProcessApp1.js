"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const ProcessMessageExchangeClient_1 = require("./ProcessMessageExchangeClient");
function processRequest(request) {
    return "!!!!client echo:" + request;
}
(0, ProcessMessageExchangeClient_1.ProcessOperation)(processRequest);
