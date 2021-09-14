package client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DataPacket {


    public DatagramPacket send(String line) throws UnknownHostException {
        DatagramPacket packet = new DatagramPacket(line.getBytes(), line.getBytes().length,
                InetAddress.getByName("localhost"), 20000);
        return packet;
    }

    public DatagramPacket recieve(){
        byte[] buffer = new byte[2048];
        DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
        return receivedPacket;
    }
}
