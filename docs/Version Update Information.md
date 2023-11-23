# Version Update Inforamtion

Version 0.4 (11/23/2023)

- Support thread or http based applications and support REST oeprations.
- The worker ID is moved onto a part of URL.
  
Version 0.31 (10/22/2023)

Python communicationExchange component. Now we can run multiple Python workers.


Version 0.3 (10/11/2023)

Message communcation is updated to use process input and output stream instead of sockets, which makes UltraBraker:
- much faster
- resolve issues with Powershell and NodeJS where socket communication stops working.

Also all sample programs are updated.

Version 0.2 (9/17/2023)

UltraBroker can
- control the number of workers running (based on amount of access, the number of runnng workers is managed between 1 to maximum processes)
- allow to refresh workers
- share connections of workers
  
Added Performance testing with JMeter.

Added NodeJS communicationExchange components.

Basically, Broker becomes more practical.

Version 0.1 (9/3/2023)

This is the first release and it's more prototype. One broker thread can 
- register workers
- run workers
- stop workers
- Java communicationExchange components
- Powerscript communicationExchange components
- Sample programs for Java and PowerShell scripts
  
Go back to main document: https://github.com/shigeru-takehara/UltraBroker/blob/main/README.md
