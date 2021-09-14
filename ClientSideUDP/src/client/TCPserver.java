package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPserver {

    ServerSocket serverSocket;
    ExecutorService pool;
    private final int localport;
    BufferedWriter writer;
     BufferedReader reader;

    public TCPserver(int localport) throws IOException {
        this.localport = localport;

        serverSocket = new ServerSocket(localport);
        pool = Executors.newFixedThreadPool(5);
    }

    public void run() throws IOException {
        while(true) {
            Socket socket = serverSocket.accept();

            ServerThread serverThread = new ServerThread(socket);
            pool.execute(serverThread);
        }

    }

}
