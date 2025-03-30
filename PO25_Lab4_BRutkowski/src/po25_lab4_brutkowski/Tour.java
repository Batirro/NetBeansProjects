package po25_lab4_brutkowski;
import java.util.*;


public class Tour {
    // (a) Metoda wypisuj?ca nazwy pa?stw wraz z ich stolicami
    public static void printCountriesWithCapitals() {
        // U?ycie struktury Map do definicji kolekcji
        Map<String, String> countriesWithCapitals = new HashMap<>();
        
        // Dodanie przyk?adowych pa?stw i ich stolic
        countriesWithCapitals.put("Polska", "Warszawa");
        countriesWithCapitals.put("Niemcy", "Berlin");
        countriesWithCapitals.put("Francja", "Pary?");
        countriesWithCapitals.put("W?ochy", "Rzym");
        countriesWithCapitals.put("Hiszpania", "Madryt");
        countriesWithCapitals.put("Szwecja", "Sztokholm");
        
        // Wypisanie pa?stw i ich stolic
        System.out.println("Pa?stwa i ich stolice:");
        for (Map.Entry<String, String> entry : countriesWithCapitals.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
    
    // (b) Metoda wypisuj?ca odwiedzone miasta podczas ca?ej trasy wycieczki
    public static void printVisitedCities() {
        // U?ycie struktury List do definicji kolekcji
        List<String> visitedCities = new ArrayList<>();
        
        // Dodanie przyk?adowych miast (niekt?re si? powtarzaj?)
        visitedCities.add("Warszawa");
        visitedCities.add("Berlin");
        visitedCities.add("Pary?");
        visitedCities.add("Rzym");
        visitedCities.add("Madryt");
        visitedCities.add("Berlin"); // Powt?rzone miasto
        visitedCities.add("Warszawa"); // Powt?rzone miasto
        
        // Wypisanie wszystkich odwiedzonych miast
        System.out.println("\nWszystkie odwiedzone miasta:");
        for (String city : visitedCities) {
            System.out.println(city);
        }
    }
    
    // (c) Metoda wypisuj?ca tylko unikalne nazwy miast
    public static void printUniqueCities() {
        // U?ycie struktury Set do definicji kolekcji
        Set<String> uniqueCities = new HashSet<>();
        
        // Dodanie przyk?adowych miast (duplikaty zostan? automatycznie usuni?te)
        uniqueCities.add("Warszawa");
        uniqueCities.add("Berlin");
        uniqueCities.add("Pary?");
        uniqueCities.add("Rzym");
        uniqueCities.add("Madryt");
        uniqueCities.add("Berlin"); // Duplikat - zostanie zignorowany
        uniqueCities.add("Warszawa"); // Duplikat - zostanie zignorowany
        
        // Wypisanie unikalnych odwiedzonych miast
        System.out.println("\nUnikalne odwiedzone miasta:");
        for (String city : uniqueCities) {
            System.out.println(city);
        }
    }
    
    // Sprawdzenie czy kolekcja (a) zawiera Szwecj?
    public static void checkSwedenInCountries() {
        Map<String, String> countriesWithCapitals = new HashMap<>();
        countriesWithCapitals.put("Polska", "Warszawa");
        countriesWithCapitals.put("Niemcy", "Berlin");
        countriesWithCapitals.put("Francja", "Pary?");
        countriesWithCapitals.put("W?ochy", "Rzym");
        countriesWithCapitals.put("Hiszpania", "Madryt");
        countriesWithCapitals.put("Szwecja", "Sztokholm");
        
        System.out.println("\nCzy kolekcja (a) zawiera Szwecj?: " + countriesWithCapitals.containsKey("Szwecja"));
    }
    
    // Sprawdzenie czy kolekcja (b) zawiera Pary?
    public static void checkParisInVisitedCities() {
        List<String> visitedCities = new ArrayList<>();
        visitedCities.add("Warszawa");
        visitedCities.add("Berlin");
        visitedCities.add("Pary?");
        visitedCities.add("Rzym");
        
        System.out.println("Czy kolekcja (b) zawiera Pary?: " + visitedCities.contains("Pary?"));
    }
    
    // Sprawdzenie czy kolekcja (c) zawiera Rzym
    public static void checkRomeInUniqueCities() {
        Set<String> uniqueCities = new HashSet<>();
        uniqueCities.add("Warszawa");
        uniqueCities.add("Berlin");
        uniqueCities.add("Pary?");
        uniqueCities.add("Rzym");
        
        System.out.println("Czy kolekcja (c) zawiera Rzym: " + uniqueCities.contains("Rzym"));
    }
    
    // Wypisanie ilo?ci element?w w poszczeg?lnych kolekcjach
    public static void printCollectionSizes() {
        Map<String, String> countriesWithCapitals = new HashMap<>();
        countriesWithCapitals.put("Polska", "Warszawa");
        countriesWithCapitals.put("Niemcy", "Berlin");
        countriesWithCapitals.put("Francja", "Pary?");
        countriesWithCapitals.put("W?ochy", "Rzym");
        countriesWithCapitals.put("Hiszpania", "Madryt");
        countriesWithCapitals.put("Szwecja", "Sztokholm");
        
        List<String> visitedCities = new ArrayList<>();
        visitedCities.add("Warszawa");
        visitedCities.add("Berlin");
        visitedCities.add("Pary?");
        visitedCities.add("Rzym");
        visitedCities.add("Madryt");
        visitedCities.add("Berlin");
        visitedCities.add("Warszawa");
        
        Set<String> uniqueCities = new HashSet<>();
        uniqueCities.add("Warszawa");
        uniqueCities.add("Berlin");
        uniqueCities.add("Pary?");
        uniqueCities.add("Rzym");
        uniqueCities.add("Madryt");
        
        System.out.println("\nLiczba element?w w kolekcji (a): " + countriesWithCapitals.size());
        System.out.println("Liczba element?w w kolekcji (b): " + visitedCities.size());
        System.out.println("Liczba element?w w kolekcji (c): " + uniqueCities.size());
    }
}
