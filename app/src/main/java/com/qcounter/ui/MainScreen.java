package com.qcounter.ui;

import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qcounter.util.ClientConnector;
import com.qcounter.R;
import com.qcounter.util.Loader;
import com.qcounter.util.Queue;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    // This arraylist contains EVERY ClientConnector, regardless if active or not.
    private ArrayList<ClientConnector> connectors;

    // This arraylist contains every ACTIVE ClientConnector
    private ArrayList<ClientConnector> activeConnectors;


    /* ------------------------------------------------------------------------
     * This section general methods related to the activity.
     * ------------------------------------------------------------------------
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        activeConnectors = new ArrayList<>();
        // Load every stored connection
        ConnectorLoader loaderTask = new ConnectorLoader();
        loaderTask.execute();
    }

    /* ------------------------------------------------------------------------
     * This section is for ClientConnector and Queue management methods.
     * This includes methods to update UI and load/add/remove ClientConnectors.
     * ------------------------------------------------------------------------
     */

    // This private class loads all ClientConnectors async on /res/xml/connections.xml
    // to ArrayList<ClientConnector> connectors.
    private class ConnectorLoader extends AsyncTask<Void, Void, ArrayList<ClientConnector>> {

        @Override
        protected ArrayList<ClientConnector> doInBackground(Void... params) {
            ArrayList<ClientConnector> result = null;
            Loader loader = new Loader(MainScreen.this);
            try {
                XmlResourceParser parser = getApplicationContext().getResources().getXml(R.xml.connections);
                result = loader.loadLocalXML(parser);
            } catch (XmlPullParserException | IOException e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<ClientConnector> result) {
            connectors = result;
        }
    }

    // This method makes a connector active, adding to active array and starts the polling.
    public void addActiveConnector(ClientConnector connector){
        activeConnectors.add(connector);
        connector.startPolling();
    }

    // This method makes a connector inactive, removing from active array and stops the polling.
    public void removeActiveConnector(ClientConnector connector){
        activeConnectors.remove(connector);
        connector.stopPolling();
    }

    // This method is called by ClientConnector to update UI with updated queues.
    // Only active connectors will call this method.
    public void updateUIQueues(ClientConnector connector, Queue[] queues){
        String connectionName = connector.getConnectionName();
        //TODO
    }


}
