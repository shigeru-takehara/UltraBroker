import { ProcessOperation } from "./MessageExchangeClient";

let counter: number = 0;

function MyFunction(message: String): string {
    console.log("Server sent:" + message)
    return 'Hello Server - ' + counter++;
}

ProcessOperation(MyFunction);