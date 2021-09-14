package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {

    DatagramSocket serverSocket;
    ArrayList<ClientInfo> clientArray;
    String response;
    int clientCounter=0;

    public Server() throws SocketException {
        serverSocket = new DatagramSocket(20000);
    }


    public ClientInfo run() throws IOException {
        /**
         * server receives a packet contains (userName, IpAddress, PortNumber) from client
         *
         */
        clientArray = new ArrayList<ClientInfo>();

        while (true) {
            byte[] byteArray = new byte[1024];

            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length);
            System.out.println("Server is listening \n");
            serverSocket.receive(packet);

            /**
             * client sends 2 form of messages ,
             *  1/starts with 'name' which describes the username of client
             *  2/starts with the name of username that client is searching for
             *
             */
            String[] name = (new String(packet.getData())).split(" ");
            if (name[0].startsWith("name")) {
                clientArray.add(new ClientInfo(name[1], packet.getAddress().getHostAddress(), packet.getPort()));

                System.out.println("Server has received a packet from " + clientArray.get(clientCounter).userName +
                        " with IP number: " + clientArray.get(clientCounter).IPAddress +
                        " and port number: " + clientArray.get(clientCounter).portNumber);
                System.out.println();

                response = "welcome " + clientArray.get(clientCounter).userName + " you have registered!";
                DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length,
                        packet.getAddress(), packet.getPort());

                serverSocket.send(sendPacket);
                clientCounter++;
            } else {
                System.out.println("Client has requested Server to find "+ name[0]+"\n");
                ClientInfo client = find(name[0]);
                if(client != null) {
                    System.out.println("Server has found " + client.getUserName() + "'s username with port number: " +client.getPortNumber());
                    response = client.getIPAddress() + " " + client.getPortNumber();
                    DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length,
                            packet.getAddress(), packet.getPort());
                    serverSocket.send(sendPacket);
                    System.out.println("Server sent the info\n");

//                    String reportMsg = "You are known as a server for TCP connection, wait for receiving messages";
//                    DatagramPacket report = new DatagramPacket(reportMsg.getBytes(), reportMsg.getBytes().length,
//                            InetAddress.getByName(client.getIPAddress()), client.getPortNumber() );
//                    serverSocket.send(report);
                }
                 else {
                    response = "This userName is not exist.";
                    System.out.println("This userName is not exist.\n");
                    DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length,
                            packet.getAddress(), packet.getPort());
                    serverSocket.send(sendPacket);
                }
            }
        }
    }

    public ClientInfo find(String name){
        for (ClientInfo clientarray : clientArray) {
            if (clientarray.getUserName().trim().equals(name.trim()) ) {
                return clientarray;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
