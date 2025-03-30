package com.example.html.removing.application;

import com.example.html.remover.PageDownloadingUtility;
import com.example.html.remover.StringHtmlCleaner;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak podanego adresu URL. Użycie: java Application <url>");
            return;
        }
        
        String url = args[0];
        try {
            String htmlContent = PageDownloadingUtility.download(url);
            
            StringHtmlCleaner cleaner = new StringHtmlCleaner();
            String cleanedText = cleaner.clean(htmlContent);
            
            System.out.println(cleanedText);
        } catch (Exception e) {
            System.err.println("Wystąpił błąd podczas przetwarzania strony: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
