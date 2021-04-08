package com.dhyego.demoajax.service;

import com.dhyego.demoajax.domain.SocialMetaTag;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Dhyego Pedroso
 */

@Service
public class SocialMetaTagService {
    
    public SocialMetaTag getOpenGraphByUrl (String url){
        
        SocialMetaTag tag = new SocialMetaTag();
        try {
            Document doc = Jsoup.connect(url).get();
            tag.setTitle(doc.head().select("meta[property=og:title]").attr("content"));
            tag.setSite(doc.head().select("meta[property=og:site_name]").attr("content"));
            tag.setImage(doc.head().select("meta[property=og:image]").attr("content"));
            tag.setUrl(doc.head().select("meta[property=og:url]").attr("content"));
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        
        return tag;
    }
    
    public SocialMetaTag getTwitterCardByUrl (String url){
        
        SocialMetaTag tag = new SocialMetaTag();
        try {
            Document doc = Jsoup.connect(url).get();
            tag.setTitle(doc.head().select("meta[name=twitter:title]").attr("content"));
            tag.setSite(doc.head().select("meta[name=twitter:site]").attr("content"));
            tag.setImage(doc.head().select("meta[name=twitter:image]").attr("content"));
            tag.setUrl(doc.head().select("meta[name=twitter:url]").attr("content"));
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        
        return tag;
    }

}
