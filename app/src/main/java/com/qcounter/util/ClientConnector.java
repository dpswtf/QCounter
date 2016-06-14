package com.qcounter.util;

import android.os.AsyncTask;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;

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
    private String param;
    private int polling;

    private Handler handler;


    public ClientConnector(String connectionName, String serverIP, String serverPort, ConnectionType type, String param, int polling){
        this.connectionName = connectionName;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.type = type;
        this.param = param;
        this.polling = polling;

        handler = new Handler();
    }

    // Creates a daemon thread that always runs as long as there's a connection to the server.
    // This thread polls/requests the queue number at a fixed interval (milliseconds).
    // E.g. Request queue number from this server every 2 seconds (2000 ms).
    public void startPolling(){
        handler.postDelayed(new Runnable() {
            public void run(){
                GetQueuesTask task = new GetQueuesTask();
                task.execute();

                handler.postDelayed(this, polling);
            }
        }, polling);
    }

    public void stopPolling(){
        handler.removeCallbacksAndMessages(null);
    }

    // Requests the current number from a queue from this server.
    // E.g. Q: "What's the current queue number of line 3 at Continente Mem martins?"; A:"4"
    // For http request, the request will be http://serverIP:serverPort/param (where param could be ?number=2)+
    // This request uses AsyncTask for thread management.
    private class GetQueuesTask extends AsyncTask<Void, Void, Queue[]>{

        protected Queue[] doInBackground(Void... params) {
            Queue[] queues = null;
            switch(type){
                case HTTP:
                    String urlS = serverIP;
                    if (serverPort != null){
                        urlS += ":" +serverPort + "/";
                    }
                    urlS += param;
                    try{
                        queues = getQueuesFromHTTP(urlS);
                    } catch (Exception e){
                        e.printStackTrace();
                        return null;
                    }
                    break;
                case SOCKET:
                    // TODO
                    break;
                default:
                    break;
            }
            return queues;
        }

        protected void onPostExecute(Queue[] result) {
            // Chamar aqui updateUI(result).
            // super.onPostExecute(result);
        }
    }

    public void getQueues(){
        GetQueuesTask task = new GetQueuesTask();
        task.execute();
    }

    // If server connection is HTTP, handle it on this method.
    public Queue[] getQueuesFromHTTP(String urlS) throws IOException{
        HttpURLConnection conn = null;
        Queue[] queues = null;
        try{
            URL url = new URL(urlS);

            // Open connection and receive data
            conn = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());

            // Parse data
            String html = IOUtils.toString(in, "UTF-8");
            queues = Parser.parseFromUrl(html, urlS);

        } catch (Exception e){
            e.printStackTrace();
        } finally{
            if (conn != null){
                conn.disconnect();
            }
        }
        return queues;
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

}
