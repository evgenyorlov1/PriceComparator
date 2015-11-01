package pricecomparator;

import pricecomparator.SignedRequestsHelper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;


public class amazon {
        
    private Map upcMap = new HashMap<String, String>();        
    private Map skuMap = new HashMap<String, String>();        
    private DocumentBuilderFactory dbf;
    public ArrayList<itemAmazon> result;    
       
    
    public void main(ArrayList<item> items) {
        System.out.println("com.mycompany.pricecomparator.amazon");
        result = new ArrayList<itemAmazon>();
        result.clear();
        for(int i=0; i<items.size(); i++) {            
            upcMap.put("Service", "AWSECommerceService");
            upcMap.put("Version", "2013-08-01");        
            upcMap.put("Operation", "ItemLookup");
            upcMap.put("IdType", "UPC");        
            upcMap.put("SearchIndex", "All");                          
            upcMap.put("ItemId", items.get(i).upc); //upc
            upcMap.put("ResponseGroup", "Large");
            scrape(upcMap);        
        }
    }
    
    private void scrape(Map map) {        
        try {            
            SignedRequestsHelper signedH = new SignedRequestsHelper();            
            String url_Amazon = signedH.sign(map);
            System.out.println(url_Amazon);            
            sendRequest(url_Amazon);            
        } catch(Exception e) {System.err.println("com.mycompany.pricecomparator.amazon.scrape: " + e);}
    }
        
    private void sendRequest(String urlRequest) {        
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            if (conn.getResponseCode() != 200) {
                throw new IOException(conn.getResponseMessage());
            } else {                
                try {
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                    rd.close();
                    parser(sb.toString()); //call to parser
                } catch(Exception e) {
                    System.err.println(e);
                }
            }
        } catch(Exception e) {
            System.err.println("com.mycompany.pricecomparator.amazon.sendRequest: " + e);
            sendRequest(urlRequest);
        }
    }
    
    private void parser(String response) {
        
        dbf = DocumentBuilderFactory.newInstance();
        try {            
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
            itemAmazon item = new itemAmazon();
            try {
                //Amazon title
                item.title = doc.getElementsByTagName("Title").item(0).getTextContent();                
            } catch(Exception e) {
                item.title = "";                
                System.err.println("com.mycompany.pricecomparator.amazon.parser: " + e);
            }
            try {
                //Amazon url
                item.url = doc.getElementsByTagName("DetailPageURL").item(0).getTextContent();                
            } catch(Exception e) {
                item.url = "";                
                System.err.println("com.mycompany.pricecomparator.amazon.parser: " + e);
            }
            try {
                //Amazon price. Could be not the lowest
                item.price = doc.getElementsByTagName("FormattedPrice").item(0).getTextContent();                
            } catch(Exception e) {
                item.price = "";
                System.err.println("com.mycompany.pricecomparator.amazon.parser: " + e);
            }
            try {
                //Amazon sales rank
                item.rank = doc.getElementsByTagName("SalesRank").item(0).getTextContent();                
            } catch(Exception e) {
                item.rank = "";                
                System.err.println("com.mycompany.pricecomparator.amazon.parser: " + e);
            }
            try {
                //Amazon ASIN
                item.ASIN = doc.getElementsByTagName("ASIN").item(0).getTextContent();                
            } catch(Exception e) {
                item.ASIN = "";               
                System.err.println("com.mycompany.pricecomparator.amazon.parser: " + e);
            }
            result.add(item);
        } catch(Exception e) {            
            System.err.println("com.mycompany.pricecomparator.amazon.parser: " + e);
        }                
    }
}
