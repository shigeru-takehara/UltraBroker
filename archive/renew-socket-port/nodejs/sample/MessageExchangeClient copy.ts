import * as net from 'net';

export function ProcessOperation(callBack: (message: string)=>string) {
  let port: number;
  const args = process.argv.slice(2);
  if (args.length > 0) {
  //  console.log('Run time parameter : ' + args[0]);
  }
  else {
    console.log('no param');
    throw new Error('Need to provide socket port number.');
  }

  const HOST = 'localhost';
  const MAX_RETRIES = 600; // Adjust the number of retries as needed
  const RETRY_INTERVAL = 1000; // Milliseconds
  const END_MESSAGE = '\n';
  const MESSAGE_END_OF_PROCESS = "__EOP__";
  const MESSAGE_RENEW_PORT_NUMBER = "__RPN__";

  let client = new net.Socket();
  let client2 = new net.Socket();
  port = parseInt(args[0], 10);

  let retries = 0;

  const doConnect = () => {
//    console.log('Do connect will be called. try=' + retries);
    client.connect(port, HOST, () => {
    });
  }
  doConnect();

  client.on('error', (err) => {
    console.log('error occurred:' + err.message)
    if (retries < MAX_RETRIES) {
      retries++;
      console.log(`Retrying in ${RETRY_INTERVAL}ms (Attempt ${retries} of ${MAX_RETRIES})`);
      setTimeout(doConnect, RETRY_INTERVAL);
    } 
    else {
//      throw new Error('Max retries reached. Exiting.');
    }
  });

  client.on('data', (data) =>{
    let msg = getMessage(data.toString())
    if (msg == MESSAGE_END_OF_PROCESS) { // this is server's "closing" message
      return
    }

    else if (msg.search(MESSAGE_RENEW_PORT_NUMBER) > -1) { // this is server's "closing" message
      msg = msg.replace(MESSAGE_RENEW_PORT_NUMBER, "");
      port = parseInt(msg, 10);
//      console.log('new port =' + port);
      client.end();
      client2.connect(port, HOST, () => {
      })
      ;
    }
    else {
     let resp = callBack(msg);
//     console.log('client info=' + client.localPort)
     client.write(resp + END_MESSAGE);
     console.log('sent response to server:' + resp);
    }
  })

  client.on('close', () =>{
//    console.log('closed ')
  })

  client.on('connect', () => {
//    console.log('Connected')
  })

  client2.on('data', (data) =>{
    let msg = getMessage(data.toString())
    if (msg == MESSAGE_END_OF_PROCESS) { // this is server's "closing" message
      return
    }

    else if (msg.search(MESSAGE_RENEW_PORT_NUMBER) > -1) { // this is server's "closing" message
      msg = msg.replace(MESSAGE_RENEW_PORT_NUMBER, "");
      port = parseInt(msg, 10);
//      console.log('new port =' + port);
      client2.end();
      client.connect(port, HOST, () => {
      })
      ;
    }
    else {
     let resp = callBack(msg);
//     console.log('client info=' + client2.localPort)
     client2.write(resp + END_MESSAGE);
     console.log('sent response to server:' + resp);
    }
  })

  client2.on('close', () =>{
//    console.log('closed ')
  })

  client2.on('connect', () => {
//    console.log('Connected')
  })
}

function getMessage(val: String): string {
  return val.trim();
}
