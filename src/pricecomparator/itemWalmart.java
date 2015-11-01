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
public class itemWalmart extends item{     
    public String sku;    
    
    public itemWalmart() {}
    
    public itemWalmart(String upc, String sku, String title, String url, String price) {
        this.upc = upc;
        this.sku = sku;
        this.title = title;
        this.url = url;
        this.price = price;
    }    
}
