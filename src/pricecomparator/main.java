package pricecomparator;

import java.util.ArrayList;

/**
 *
 * @author pc
 */
public class main {
    public void scrape(String shop, String path) { 
        System.out.println("com.mycompany.pricecomparator.main");
        amazon mzn = new amazon();                
        switch(shop) {
            case "Walmart":                 
                walmart wlmrt = new walmart();
                wlmrt.main(); 
                ArrayList walmartItems = wlmrt.result;                
                
                mzn.main(walmartItems);                
                ArrayList<itemAmazon> amazonItems = mzn.result;                                                
                new fileOutput(amazonItems, walmartItems, path, shop);                
                break;
            case "Ebay":
                break;
        }
    }
}
