/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pricecomparator;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;


public class fileOutput { 
    
    private XSSFWorkbook workbook; 
    private FileOutputStream stream;
    private ArrayList<itemAmazon> amazonItems;
    private String[] columns = {"upc", "Amazon Title", "Amazon url", "Amazon price", "Shop Title", "Shop sku", "Shop url","Shop price", "ASIN", "Sales rank"};
    private ArrayList<itemWalmart> shopItems;
    private String path;    
    
    
    public fileOutput(ArrayList amazonItems, ArrayList shopItems, String path, String shopName) {                
        System.out.println("pricecomparator.fileOutput");
        this.amazonItems = amazonItems;
        this.shopItems = shopItems;
        this.path = path;        
        columns[4] = shopName + " Title";
        columns[5] = shopName + " sku";
        columns[6] = shopName + " url";
        columns[7] = shopName + " price";        
        printOut();
    }
    
    private void printOut() {
        try {        
            //stream = new FileOutputStream(new File(System.getProperty("user.dir") + File.separator + "PriceCompare" +".xlsx"));            
            stream = new FileOutputStream(new File(path + File.separator + "PriceCompare" +".xlsx"));                        
            workbook = new XSSFWorkbook(); 
            XSSFSheet sheet = workbook.createSheet("Table");                        
            XSSFRow row;               
            Cell cell;            
            row = sheet.createRow(0);
            for(int i=0; i<columns.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(columns[i]);
            }               
            
            for(int i=1; i<shopItems.size(); i++) {
                row = sheet.createRow(i);
                for(int j=0; j<columns.length; j++) {
                    cell = row.createCell(j);
                    switch(j) {
                        case 0:
                            cell.setCellValue(shopItems.get(i-1).upc); //shop upc
                            break;
                        case 1:
                            cell.setCellValue(amazonItems.get(i-1).title); //Amazon Title                                              
                            break;
                        case 2:
                            cell.setCellValue(amazonItems.get(i-1).url); //Amazon url
                            break;
                        case 3:
                            cell.setCellValue(amazonItems.get(i-1).price); //Amazon price                            
                            break; 
                        case 4:                  
                            cell.setCellValue(shopItems.get(i-1).title); //shop Title
                            break;
                        case 5:
                            cell.setCellValue(shopItems.get(i-1).sku); //shop sku
                            break;
                        case 6:
                            cell.setCellValue(shopItems.get(i-1).url); //shop price                            
                            break;
                        case 7:
                            cell.setCellValue(shopItems.get(i-1).price); //ASIN
                            break;
                        case 8:
                            cell.setCellValue(amazonItems.get(i-1).ASIN); //Sales Rank
                            break;
                        case 9:
                            cell.setCellValue(amazonItems.get(i-1).rank); //Sales Rank
                            break;
                    }                    
                }
            }            
            workbook.write(stream);
            stream.close();
        } catch(Exception e) {System.err.println("com.mycompany.pricecomparator.fileOutput.printOut: " + e);}
    } 
}