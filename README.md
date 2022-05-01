## socket Programming
sample socket programming implementing UDP and TCP protocols with Java

In this project the connections between clients and server is UDP based and the conection between clients is TCP. 

### steps
+ clients send request to server 
+ server registers the clients and sends an accknowledge message to them so a UDP connection will be set up
+ after registraion, clients are asked to enter the name of other clients to start a chat made on TCP connection or wait for other clients messages 
+ if you send the first message for another client, you will be known as a client in TCP connection and if you recieve a msg you're gonna be server in TCP connection
+ if the server with accept() method was waiting for new connection, the chat will be started; otherwise, client waits for more 30seconds and after that it should choose for another name or wait as a server for new requests.

### details
+ using multi-thread option for TCP server implements Runnable Interface
+ DatagramPacket, DatagramSocket, Socket libraries 
