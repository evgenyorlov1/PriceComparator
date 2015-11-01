/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pricecomparator;

/**
 *
 * @author pc
 */
public class itemAmazon extends item{    
    public String sku;    
    public String rank;
    public String ASIN;
    
    public itemAmazon(String upc, String title, String url, String price, String sku, String rank, String ASIN) {        
        this.upc = upc;
        this.title = title;
        this.url = url;
        this.price = price;
        this.sku = sku;
        this.rank = rank;
        this.ASIN = ASIN;
    }
    
    public itemAmazon() {}
}
