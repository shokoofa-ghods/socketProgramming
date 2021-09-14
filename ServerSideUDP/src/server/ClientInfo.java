package server;

import java.util.Objects;

public class ClientInfo {

    public String IPAddress;
    public int portNumber;
    public String userName;

    public ClientInfo(String userName, String IPAddress, int portNumber){
        this.userName = userName ;
        this.IPAddress = IPAddress ;
        this.portNumber = portNumber;
    }

    public String getUserName() {
        return userName;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getIPAddress() {
        return IPAddress;
    }


}
