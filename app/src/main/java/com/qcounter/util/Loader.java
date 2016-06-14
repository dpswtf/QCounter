package com.qcounter.util;

import android.content.res.XmlResourceParser;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Class that is responsible for loading ClientConnectors from either local or remote locations.
 */
public class Loader {

    // Load ClientConnector list from local file to ArrayList.
    // Loads from res/values/connections.xml
    public static ArrayList<ClientConnector> loadLocalXML(XmlResourceParser parser) throws XmlPullParserException, IOException {
        ArrayList<ClientConnector> connectors = null;
        try{
            parser.nextTag();
            connectors = parseXML(parser);
        } finally {
            parser.close();
        }
        return connectors;
    }

    // Parses XML and returns an ArrayList of ClientConnector.
    // Tags for each connection: name; ip; port; type; param; polling.
    // If a field is not present the value given is null. Only port and param can be null.
    private static ArrayList<ClientConnector> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<ClientConnector> connectors = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, null, "resources");
        while(parser.next() != XmlPullParser.END_TAG) {
            String tagName = parser.getName();
            if (parser.equals("connection")){
                parser.require(XmlPullParser.START_TAG, null, "connection");
                String name = null;
                String ip = null;
                String port = null;
                ClientConnector.ConnectionType type = null;
                String param = null;
                int polling = -1;

                while (parser.next() != XmlPullParser.END_TAG) {
                    String secTagName = parser.getName();
                    if(secTagName.equals("name")){
                        if(parser.next() == XmlPullParser.TEXT){
                            name = parser.getText();
                            parser.nextTag();
                        }
                    }
                    else if(secTagName.equals("ip")){
                        if(parser.next() == XmlPullParser.TEXT){
                            ip = parser.getText();
                            parser.nextTag();
                        }
                    }
                    else if(secTagName.equals("port")){
                        if(parser.next() == XmlPullParser.TEXT){
                            port = parser.getText();
                            parser.nextTag();
                        }
                    }
                    else if(secTagName.equals("type")){
                        if(parser.next() == XmlPullParser.TEXT){
                            String text = parser.getText();
                            if(text.equals("HTTP")){
                                type = ClientConnector.ConnectionType.HTTP;
                            }
                            else if(text.equals("SOCKET")){
                                type = ClientConnector.ConnectionType.SOCKET;
                            }
                            parser.nextTag();
                        }
                    }
                    else if(secTagName.equals("param")){
                        param = parser.getText();
                        parser.nextTag();
                    }
                    else if(secTagName.equals("polling")){
                        polling = Integer.parseInt(parser.getText());
                        parser.nextTag();
                    }
                }

                if(polling != -1 && name != null && ip != null && type != null){
                    ClientConnector connector = new ClientConnector(name, ip, port, type, param, polling);
                    connectors.add(connector);
                }

            }

        }
        return connectors;
    }

}

