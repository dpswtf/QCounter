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

    private ArrayList<ClientConnector> connectors;

    // This private class loads all ClientConnectors async on /res/xml/connections.xml
    private class ConnectorLoader extends AsyncTask<Void, Void, ArrayList<ClientConnector>> {

        protected ArrayList<ClientConnector> doInBackground(Void... params) {
            ArrayList<ClientConnector> result = null;
            try {
                XmlResourceParser parser = getApplicationContext().getResources().getXml(R.xml.connections);
                connectors = Loader.loadLocalXML(parser);
            } catch (XmlPullParserException | IOException e){
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(ArrayList<ClientConnector> result) {
            connectors = result;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        ConnectorLoader loaderTask = new ConnectorLoader();
        loaderTask.execute();
    }


}
