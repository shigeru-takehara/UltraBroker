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
  const RETRY_INTERVAL = 100; // Milliseconds
  const END_MESSAGE = '\n';

  const client = new net.Socket();
  port = parseInt(args[0], 10);

  let retries = 0;

  const doConnect = () => {
  //  console.log('Do connect will be called. try=' + retries);
    client.connect(port, HOST, () => {
    });
    client.setNoDelay(true);
  }
  doConnect();

  client.on('error', (err) => {
    if (retries < MAX_RETRIES) {
      retries++;
//      console.log(`Retrying in ${RETRY_INTERVAL}ms (Attempt ${retries} of ${MAX_RETRIES})`);
      setTimeout(doConnect, RETRY_INTERVAL);
    } 
    else {
      throw new Error('Max retries reached. Exiting.');
    }
  });

  client.on('data', (data) =>{
    let msg = getMessage(data.toString())
    if (msg == '__EOP__') { // this is server's "closing" message
      return
    }

    let resp = callBack(msg);

    client.write(resp + END_MESSAGE);
  })

  client.on('close', () =>{
  })

}

function getMessage(val: String): string {
  return val.trim();
}
