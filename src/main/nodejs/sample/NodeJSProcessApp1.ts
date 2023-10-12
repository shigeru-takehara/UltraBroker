import { ProcessOperation } from './ProcessMessageExchangeClient';

function processRequest(request: string): string {
    return "!!!!client echo:" + request;
}

ProcessOperation(processRequest);
