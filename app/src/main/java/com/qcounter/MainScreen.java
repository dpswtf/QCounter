package com.qcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    private ArrayList<ClientConnector> connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        connector = new ArrayList<>();
        loadConnectorList();
    }

    // Load ClientConnector list from local file or remote location to ArrayList.
    private void loadConnectorList(){
        ClientConnector iscte = new ClientConnector();
        iscte.setUpConnection("ISCTE", "http://myticket.iscte-iul.pt/", null , ClientConnector.ConnectionType.HTTP, "getTickets.jsp");
        iscte.setUpPolling(5000);
        iscte.getQueues();
    }
}
