package com.qcounter;

/**
 * ClientConnector.java
 * Class that handles connections and requests with target server.
 *
 * TODO: Implement class, Remove TODO comments and add JDoc.
 */
public class ClientConnector {

    // Variables for connection
    // Maybe change this to arguments on constructor instead.
    public static final String serverIP = "";
    public static final String serverPort = "";


    // Sets up connection with target server.
    public void setUpConnection(){
        //...

        setUpPolling(2000);
    }

    // Creates a daemon thread that always runs as long as there's a connection to the server.
    // This thread polls/requests the queue number at a fixed interval (milliseconds).
    // E.g. Request queue number from this server every 2 seconds (2000 ms).
    public void setUpPolling(int interval){

    }

    // Requests the current number from a queue from this server.
    // E.g. Q: "What's the current queue number of line 3 at Continente Mem martins?"; A:"4"
    // Maybe include an ID, if there's more than one queue in a particular server.
    public int getQueueNumber(int id){
        return -1;
    }
}
