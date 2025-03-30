package com.example.html.remover;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class StringHtmlCleaner {
    
    public String clean(String textToClean) {
        if (textToClean == null || textToClean.isEmpty()) {
            return "";
        }
        
        Document document = Jsoup.parse(textToClean);
        String textWithoutHtml = document.text();
        
        return textWithoutHtml;
    }
}
