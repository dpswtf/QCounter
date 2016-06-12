package com.qcounter;

/**
 * ClientConnector.java
 * Class that handles connections and requests with target server.
 *
 * TODO: Implement class, Remove TODO comments and add JDoc.
 */
public class ClientConnector {

    public enum ConnectionType { SOCKET, HTTP }

    private String connectionName;
    private ConnectionType type;
    private String serverIP;
    private String serverPort;

    private int[] serverIds; // E.g. {1 , 2}
    private String[] serverIdsNames; // E.g. {"Linha 1", "Linha 2"}


    // Sets up connection with target server.
    public void setUpConnection(String connectionName, String serverIP, String serverPort, int[] serverIds, String[] serverIdsNames, ConnectionType type){
        this.connectionName = connectionName;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.serverIds = serverIds;
        this.serverIdsNames = serverIdsNames;
        this.type = type;

        setUpPolling(2000);
    }

    // Creates a daemon thread that always runs as long as there's a connection to the server.
    // This thread polls/requests the queue number at a fixed interval (milliseconds).
    // E.g. Request queue number from this server every 2 seconds (2000 ms).
    public void setUpPolling(int interval){

    }

    // Requests the current number from a queue from this server.
    // E.g. Q: "What's the current queue number of line 3 at Continente Mem martins?"; A:"4"
    // For http request, the request will be http://serverIP:serverPort/?param=id
    public int getQueueNumber(int id, String param){
        return -1;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public ConnectionType getType() {
        return type;
    }

    public String getServerIP() {
        return serverIP;
    }

    public String getServerPort() {
        return serverPort;
    }

    public int[] getServerIds() {
        return serverIds;
    }

    public String[] getServerIdsNames() {
        return serverIdsNames;
    }
}
