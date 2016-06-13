package com.qcounter;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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


    // Sets up connection with target server.
    public void setUpConnection(String connectionName, String serverIP, String serverPort, ConnectionType type, String param){
        this.connectionName = connectionName;
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.type = type;
        this.param = param;
    }

    // Creates a daemon thread that always runs as long as there's a connection to the server.
    // This thread polls/requests the queue number at a fixed interval (milliseconds).
    // E.g. Request queue number from this server every 2 seconds (2000 ms).
    public void setUpPolling(int interval){

    }

    // Requests the current number from a queue from this server.
    // E.g. Q: "What's the current queue number of line 3 at Continente Mem martins?"; A:"4"
    // For http request, the request will be http://serverIP:serverPort/param (where param could be ?number=2)

    private class GetQueuesTask extends AsyncTask<Void, Void, Queue[]>{

        protected Queue[] doInBackground(Void... params) {
            Queue[] queues;
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

                    break;
                default:
                    break;
            }

            return null;
        }

        protected void onPostExecute(Queue[] result) {
            // Adicionar pós-execução aqui
            super.onPostExecute(result);
        }
    }

    public void getQueues(){
        GetQueuesTask task = new GetQueuesTask();
        task.execute();
    }

    public Queue[] getQueuesFromHTTP(String urlS) throws IOException{
        HttpURLConnection conn = null;
        Queue[] queues = null;
        try{
            URL url = new URL(urlS);
            System.out.println(urlS);
            conn = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            String html = IOUtils.toString(in, "UTF-8");

            switch(urlS){
                case "http://myticket.iscte-iul.pt/getTickets.jsp": // ISCTE
                    Document doc = Jsoup.parse(html);
                    int size = doc.select("tbody").select("tr").size();
                    queues = new Queue[size-1];
                    for(int i = 1; i<size; i++){
                        Element row = doc.select("tbody").select("tr").get(i);
                        String name = row.select(".ticketQueue").select("span").text();
                        String numberName = row.select(".ticketNumber").text();
                        String service = row.select(".ticketDesk").text();
                        Queue queue = new Queue(name, service, numberName);
                        queues[i-1] = queue;
                    }
                    break;
                default: break;
            }

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
