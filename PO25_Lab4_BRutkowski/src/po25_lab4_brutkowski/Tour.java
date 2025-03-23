package po25_lab4_brutkowski;
import java.util.*;


public class Tour {
    // (a) Metoda wypisująca nazwy państw wraz z ich stolicami
    public static void printCountriesWithCapitals() {
        // Użycie struktury Map do definicji kolekcji
        Map<String, String> countriesWithCapitals = new HashMap<>();
        
        // Dodanie przykładowych państw i ich stolic
        countriesWithCapitals.put("Polska", "Warszawa");
        countriesWithCapitals.put("Niemcy", "Berlin");
        countriesWithCapitals.put("Francja", "Paryż");
        countriesWithCapitals.put("Włochy", "Rzym");
        countriesWithCapitals.put("Hiszpania", "Madryt");
        countriesWithCapitals.put("Szwecja", "Sztokholm");
        
        // Wypisanie państw i ich stolic
        System.out.println("Państwa i ich stolice:");
        for (Map.Entry<String, String> entry : countriesWithCapitals.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
    
    // (b) Metoda wypisująca odwiedzone miasta podczas całej trasy wycieczki
    public static void printVisitedCities() {
        // Użycie struktury List do definicji kolekcji
        List<String> visitedCities = new ArrayList<>();
        
        // Dodanie przykładowych miast (niektóre się powtarzają)
        visitedCities.add("Warszawa");
        visitedCities.add("Berlin");
        visitedCities.add("Paryż");
        visitedCities.add("Rzym");
        visitedCities.add("Madryt");
        visitedCities.add("Berlin"); // Powtórzone miasto
        visitedCities.add("Warszawa"); // Powtórzone miasto
        
        // Wypisanie wszystkich odwiedzonych miast
        System.out.println("\nWszystkie odwiedzone miasta:");
        for (String city : visitedCities) {
            System.out.println(city);
        }
    }
    
    // (c) Metoda wypisująca tylko unikalne nazwy miast
    public static void printUniqueCities() {
        // Użycie struktury Set do definicji kolekcji
        Set<String> uniqueCities = new HashSet<>();
        
        // Dodanie przykładowych miast (duplikaty zostaną automatycznie usunięte)
        uniqueCities.add("Warszawa");
        uniqueCities.add("Berlin");
        uniqueCities.add("Paryż");
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
    
    // Sprawdzenie czy kolekcja (a) zawiera Szwecję
    public static void checkSwedenInCountries() {
        Map<String, String> countriesWithCapitals = new HashMap<>();
        countriesWithCapitals.put("Polska", "Warszawa");
        countriesWithCapitals.put("Niemcy", "Berlin");
        countriesWithCapitals.put("Francja", "Paryż");
        countriesWithCapitals.put("Włochy", "Rzym");
        countriesWithCapitals.put("Hiszpania", "Madryt");
        countriesWithCapitals.put("Szwecja", "Sztokholm");
        
        System.out.println("\nCzy kolekcja (a) zawiera Szwecję: " + countriesWithCapitals.containsKey("Szwecja"));
    }
    
    // Sprawdzenie czy kolekcja (b) zawiera Paryż
    public static void checkParisInVisitedCities() {
        List<String> visitedCities = new ArrayList<>();
        visitedCities.add("Warszawa");
        visitedCities.add("Berlin");
        visitedCities.add("Paryż");
        visitedCities.add("Rzym");
        
        System.out.println("Czy kolekcja (b) zawiera Paryż: " + visitedCities.contains("Paryż"));
    }
    
    // Sprawdzenie czy kolekcja (c) zawiera Rzym
    public static void checkRomeInUniqueCities() {
        Set<String> uniqueCities = new HashSet<>();
        uniqueCities.add("Warszawa");
        uniqueCities.add("Berlin");
        uniqueCities.add("Paryż");
        uniqueCities.add("Rzym");
        
        System.out.println("Czy kolekcja (c) zawiera Rzym: " + uniqueCities.contains("Rzym"));
    }
    
    // Wypisanie ilości elementów w poszczególnych kolekcjach
    public static void printCollectionSizes() {
        Map<String, String> countriesWithCapitals = new HashMap<>();
        countriesWithCapitals.put("Polska", "Warszawa");
        countriesWithCapitals.put("Niemcy", "Berlin");
        countriesWithCapitals.put("Francja", "Paryż");
        countriesWithCapitals.put("Włochy", "Rzym");
        countriesWithCapitals.put("Hiszpania", "Madryt");
        countriesWithCapitals.put("Szwecja", "Sztokholm");
        
        List<String> visitedCities = new ArrayList<>();
        visitedCities.add("Warszawa");
        visitedCities.add("Berlin");
        visitedCities.add("Paryż");
        visitedCities.add("Rzym");
        visitedCities.add("Madryt");
        visitedCities.add("Berlin");
        visitedCities.add("Warszawa");
        
        Set<String> uniqueCities = new HashSet<>();
        uniqueCities.add("Warszawa");
        uniqueCities.add("Berlin");
        uniqueCities.add("Paryż");
        uniqueCities.add("Rzym");
        uniqueCities.add("Madryt");
        
        System.out.println("\nLiczba elementów w kolekcji (a): " + countriesWithCapitals.size());
        System.out.println("Liczba elementów w kolekcji (b): " + visitedCities.size());
        System.out.println("Liczba elementów w kolekcji (c): " + uniqueCities.size());
    }
}
