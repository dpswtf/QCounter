package com.qcounter.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Class that contains all methods to parse from HTML to Queue[] from known domains.
 */
public class Parser {

    public static final String[] links = {"http://myticket.iscte-iul.pt/getTickets.jsp",
                                          "http://ciencias.ulisboa.pt/servicos/perfil/SenhasAtendimento/data"};


    public static Queue[] parseFromUrl(String html, String url){
        Queue[] queues = null;
        Document doc = Jsoup.parse(html);

        if(url.equals(links[0])){
            queues = parseISCTE(doc);
        }
        else if(url.equals(links[1])){
            queues = parseFCUL(doc);
        }
        else { // Default operation - only one queue and <body> has only queue number
            Queue queue = new Queue("Linha");
            queue.setCurrentNumberName(doc.select("body").text());
        }

        return queues;
    }

    private static Queue[] parseISCTE(Document doc){
        int size = doc.select("tbody").select("tr").size();
        Queue[] queues = new Queue[size-1];
        for(int i = 1; i<size; i++){
            Element row = doc.select("tbody").select("tr").get(i);
            String name = row.select(".ticketQueue").select("span").text();
            String numberName = row.select(".ticketNumber").text();
            String service = row.select(".ticketDesk").text();
            Queue queue = new Queue(name, service, numberName);
            queues[i-1] = queue;
        }
        return queues;
    }

    private static Queue[] parseFCUL(Document doc){
        // Parse...

        return null;
    }
}
