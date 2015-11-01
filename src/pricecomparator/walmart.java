/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pricecomparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author pc
 */

public class walmart {
    private final static String url = "http://www.walmart.ca/en/clearance-event/N-22993";
    private final static String shortUrl = "http://www.walmart.ca/en/ip";
    public static ArrayList<itemWalmart> result = new ArrayList<itemWalmart>();
    private static String startKey = "<h1><a href=\"/en/ip";
    private static String endKey = "\">";
    private static String startTitle = "prod_name_en";
    private static String endInPage = "\"],";
    private static String startUpc = "upc_nbr";
    private static String startSku = "sku_id";
    private static String startPrice = "price_store_price";
    
    public static void main() {
        System.out.println("com.mycompany.pricecomparator.walmart");
        result.clear();
        sendRequest(url, false);   
   } 
   
    private static void sendRequest(String urlRequest, boolean flag) {
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            if (conn.getResponseCode() != 200) {
                throw new IOException(conn.getResponseMessage());
            }
            
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
                if(flag == false)
                    mainParser(sb.toString());
                else
                    pageParser(sb.toString(), urlRequest);                
            } catch(Exception e) {
                System.err.println("com.mycompany.pricecomparator.walmart.sendRequest: " + e);
            }
        } catch(Exception e) {
            System.err.println("com.mycompany.pricecomparator.walmart.sendRequest: " + e);
        }
    }
   
    private static void mainParser(String html) {
        try {
            int index = 0;  
            ArrayList urls = new ArrayList();
            while(true) {
                index = html.indexOf(startKey, index);               
                if(index == -1) break;                          
                urls.add(shortUrl+html.substring(index+startKey.length(), html.indexOf(endKey, index)));            
                index++;          
            }
        
            ArrayList clearUrls = new ArrayList();
            clearUrls.add(urls.get(0));
            boolean repeat = false;
            for(int i=1; i<urls.size(); i++) {
                repeat = false;
                for(int j=0; j<clearUrls.size(); j++) {
                    if(clearUrls.get(j).equals(urls.get(i))) {
                        repeat = true;                    
                    }                
                }
                if(repeat == false) clearUrls.add(urls.get(i));
            }
            urls.clear();                                
            for(int i=0; i<clearUrls.size(); i++) {            
                sendRequest((String)clearUrls.get(i), true);
            }   
        } catch(Exception e){System.err.println("com.mycompany.pricecomparator.walmart.mainParser: " + e);}
    }
    
    private static void pageParser(String html, String pageUrl) {
        try {            
            itemWalmart item = new itemWalmart();            
            item.url = pageUrl;            
            //item.price
            int indexPrice1 = html.indexOf(startPrice);
            int indexPrice2 = html.indexOf(endInPage, indexPrice1+startPrice.length()+4);                                                                    
            item.price = html.substring(indexPrice1+startPrice.length()+4, indexPrice2-4);            
            //item.title
            int indexTitle1 = html.indexOf(startTitle);
            int indexTitle2 = html.indexOf(endInPage, indexTitle1+startTitle.length()+4);                                                        
            item.title = html.substring(indexTitle1+startTitle.length()+4, indexTitle2);            
            //item.sku
            int indexSku1 = html.indexOf(startSku);
            int indexSku2 = html.indexOf(endInPage, indexSku1+startSku.length()+4);                                                                                
            item.sku = html.substring(indexSku1+startSku.length()+4, indexSku2);            
            //item.upc        
            int indexUpc1 = html.indexOf(startUpc);
            int indexUpc2 = html.indexOf(endInPage, indexUpc1+startUpc.length()+4);                                                                    
            item.upc = html.substring(indexUpc1+startUpc.length()+4, indexUpc2);            
            result.add(item);
        } catch(Exception e) {System.err.println("com.mycompany.pricecomparator.walmart.pageParser" + e);}
    }   
   
}
