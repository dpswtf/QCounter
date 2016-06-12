package com.qcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainScreen extends AppCompatActivity {

    private ClientConnector connector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        connector = new ClientConnector();
    }
}
