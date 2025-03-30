package org.example;

import org.example.PageDownloadingUtility;
import org.example.StringHtmlCleaner;

public class Application {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak podanego adresu URL. Użycie: java Application <url>");
            return;
        }

        String url = args[0];
        try {
            // Pobierz stronę za pomocą PageDownloadingUtility
            String htmlContent = PageDownloadingUtility.download(url);

            // Oczyść stronę z tagów HTML za pomocą StringHtmlCleaner
            StringHtmlCleaner cleaner = new StringHtmlCleaner();
            String cleanedText = cleaner.clean(htmlContent);

            // Wyświetl oczyszczoną stronę
            System.out.println(cleanedText);
        } catch (Exception e) {
            System.err.println("Wystąpił błąd podczas przetwarzania strony: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
