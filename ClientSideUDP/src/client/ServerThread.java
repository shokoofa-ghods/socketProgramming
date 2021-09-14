package client;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    String linem;

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        System.out.println("Server has received a message from IP number: " + socket.getInetAddress().getHostAddress() +
                " and port number: " + socket.getPort());
    }

    @Override
    public void run() {
        while (true) {
            try {
            linem = reader.readLine();
            System.out.println("the message is '" + linem + "' from port: "+socket.getPort()+"\n");
            System.out.println("type the reply message for client");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            linem = br.readLine();
            System.out.println("server sent client: "+linem);
            writer.write(linem + "\n");
            writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
