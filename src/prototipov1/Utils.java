package prototipov1;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Utils {
    
    static public Document getDocument(String url, String ano) throws MalformedURLException, IOException{
        Document doc;
        
        if(Integer.parseInt(ano) < 2017){
            doc = Jsoup.parse(new URL(url).openConnection().getInputStream(), "utf-8", url);
        } else{
            doc = Jsoup.connect(url).get();
        }
        
        return doc;
    }
}
