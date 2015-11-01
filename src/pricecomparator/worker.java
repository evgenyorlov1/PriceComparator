package pricecomparator;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;


class worker extends SwingWorker<Boolean, Void>
{
    private String path;
    private String shop;
    private JProgressBar progressBar;
    
    
    public worker(String shop, String path, JProgressBar progressBar) {
        this.path = path;
        this.shop = shop;
        this.progressBar = progressBar;
        
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            new main().scrape(shop, path);            
        } catch(Exception e) {            
        }   
        return false;
    }
    
    @Override
    protected void done() {
        boolean status;
        try {
            status = get(); 
            progressBar.setIndeterminate(status);
        } catch(Exception e) {}
    }
    
}
