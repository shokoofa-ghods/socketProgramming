package client;

import java.io.*;
import java.net.ConnectException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static client.Client.select;


public class TCPClient {

    private final int port;
    private final DatagramSocket clientSocket;
    private final int localport;
    private final String serverUN;
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    private BufferedReader br;

    public TCPClient(int port, String line, int localport, DatagramSocket clientSocket) throws IOException {
        this.localport = localport;
        this.clientSocket = clientSocket;
        this.port = port;
        this.serverUN = line;

    }

    public void run () throws IOException, InterruptedException {
        System.out.println("you're sending connection request to "+serverUN+"(server)");

        //client is waiting for server to listen for 1 minute
        //if server doesn't listen the client runs select() method to choose another person

        for(int i =0 ; i<2 ; i++){
            try {
                socket = new Socket("localhost", port, InetAddress.getByName("localhost"), localport );
                break;
            } catch (ConnectException err) {
                if(i == 0) {
                    System.out.println("server is not listening; you must wait for 30seconds!\n");
                    TimeUnit.SECONDS.sleep(10);
                }
                if(i==1) {
                    System.out.println(serverUN+" hasn't known as a server for TCP connection and connection refused\n");
                    select();
                }
            }
        }
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("you connected !");
        while (true) {
            System.out.println("write your message for "+serverUN );
            br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            writer.write(line+"\n");
            writer.flush();
            System.out.println("client sent server: " + line);
            line = reader.readLine();
            System.out.println("Server replied: " + line);
        }
    }
}
