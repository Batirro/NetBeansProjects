package po25_lab5_brutkowski;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class ZapisDoPlikow {
    
    public static void zapisDoPlikow(Tour tour) {
        zapisCountriesCities(tour);
        
        zapisCities(tour);
        
        zapisUniqueCities(tour);
    }
    
    private static void zapisCountriesCities(Tour tour) {
        String filename = "countries_cities.txt";
        
        File file = new File(filename);
        if (file.exists()) {
            System.out.println("Plik " + filename + " już istnieje. Czy chcesz go nadpisać? (tak/nie)");
            Scanner scanner = new Scanner(System.in);
            String odpowiedz = scanner.nextLine().toLowerCase();
            
            if (!odpowiedz.equals("tak")) {
                System.out.println("Anulowano zapis do pliku " + filename);
                return;
            }
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            Map<String, String> countriesCities = tour.getCountriesCities();
            
            writer.println("Kolekcja państw i ich stolic:");
            for (Entry<String, String> entry : countriesCities.entrySet()) {
                writer.println(entry.getKey() + " - " + entry.getValue());
            }
            
            writer.println("Czy kolekcja zawiera Szwecję? " + countriesCities.containsKey("Szwecja"));
            writer.println("Liczba elementów kolekcji: " + countriesCities.size());
            
            System.out.println("Plik " + filename + " został utworzony pomyślnie.");
        } catch (IOException e) {
            System.out.println("Błąd przy zapisie do pliku " + filename + ": " + e.getMessage());
        }
    }
    
    private static void zapisCities(Tour tour) {
        String filename = "cities.txt";
        
        File file = new File(filename);
        if (file.exists()) {
            System.out.println("Plik " + filename + " już istnieje. Czy chcesz go nadpisać? (tak/nie)");
            Scanner scanner = new Scanner(System.in);
            String odpowiedz = scanner.nextLine().toLowerCase();
            
            if (!odpowiedz.equals("tak")) {
                System.out.println("Anulowano zapis do pliku " + filename);
                return;
            }
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            List<String> cities = tour.getCities();
            
            writer.println("Kolekcja wszystkich odwiedzonych miast (mogą się powtarzać):");
            for (String city : cities) {
                writer.println(city);
            }
            
            writer.println("Czy kolekcja zawiera Paryż? " + cities.contains("Paryż"));
            writer.println("Liczba elementów kolekcji: " + cities.size());
            
            System.out.println("Plik " + filename + " został utworzony pomyślnie.");
        } catch (IOException e) {
            System.out.println("Błąd przy zapisie do pliku " + filename + ": " + e.getMessage());
        }
    }
    
    private static void zapisUniqueCities(Tour tour) {
        String filename = "unique_cities.txt";
        
        File file = new File(filename);
        if (file.exists()) {
            System.out.println("Plik " + filename + " już istnieje. Czy chcesz go nadpisać? (tak/nie)");
            Scanner scanner = new Scanner(System.in);
            String odpowiedz = scanner.nextLine().toLowerCase();
            
            if (!odpowiedz.equals("tak")) {
                System.out.println("Anulowano zapis do pliku " + filename);
                return;
            }
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            Queue<String> uniqueCities = tour.getUniqueCities();
            
            writer.println("Kolekcja unikalnych miast (bez powtórzeń):");
            for (String city : uniqueCities) {
                writer.println(city);
            }
            
            writer.println("Czy kolekcja zawiera Rzym? " + uniqueCities.contains("Rzym"));
            writer.println("Liczba elementów kolekcji: " + uniqueCities.size());
            
            System.out.println("Plik " + filename + " został utworzony pomyślnie.");
        } catch (IOException e) {
            System.out.println("Błąd przy zapisie do pliku " + filename + ": " + e.getMessage());
        }
    }
}
