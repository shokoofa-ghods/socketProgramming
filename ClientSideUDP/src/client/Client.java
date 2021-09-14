package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class Client {

    static public int port;
    static public int hostport;
    static BufferedReader br;
    static String line;
    static DatagramPacket receivedPacket;
    static DatagramSocket clientSocket;
    static DataPacket data;

    public Client(int port) throws SocketException {
        this.port = port;
        clientSocket = new DatagramSocket(port);
        data = new DataPacket();
    }

    /**
     * client sends a packet contains (userName, IPAddress, portNumber) to the server
     */
    public void register() throws IOException, InterruptedException {

        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("please enter your userName");
        line = br.readLine();
        clientSocket.send(data.send("name " + line));
        System.out.println("client sent data");

        receivedPacket = data.recieve();
        clientSocket.receive(receivedPacket);
        System.out.println("Server replied: " + new String(receivedPacket.getData()) + "\n");
        select();

    }

    //This method is static because we want to use it in other classes without creating instance and new client socket
    public static void select() throws IOException, InterruptedException{
        while (true) {
            System.out.println("enter the userName who you want to chat with OR type 'wait' if you're waiting for someone's message OR type 'exit'");
            System.out.println("remember if you want to 'wait' for someone's message you have almost 1 minute to choose it otherwise the connection will not happen");
            line = br.readLine();
            if (line.startsWith("exit"))
                break;
            else if (line.startsWith("wait")) {
                System.out.println("So now you're known as server for TCP connection, wait for clients to send you messages");
                TCPserver tcpServer = new TCPserver(port);
                tcpServer.run();
            } else {
                clientSocket.send(data.send(line));
                System.out.println("Client asked server about " + line + "'s info");
                receivedPacket = data.recieve();
                clientSocket.receive(receivedPacket);
                System.out.println("Server replied: " + new String(receivedPacket.getData()) + "\n");
                String[] info = new String(receivedPacket.getData()).split(" ");
                if (info.length > 2) {
                    //this userName is not exist
                    select();
                }
                hostport = Integer.parseInt(info[1].trim());
                TCPClient tcpclient = new TCPClient(hostport, line, port,clientSocket);
                tcpclient.run();
            }
        }
        clientSocket.close();
    }



    public static void main(String[] args) {
        try {
            //change the port number to try connecting by different clients
            Client client = new Client(12000);
            client.register();

        } catch (InterruptedException e) {
                e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
