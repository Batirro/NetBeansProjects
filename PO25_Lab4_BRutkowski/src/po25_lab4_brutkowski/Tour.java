package po25_lab4_brutkowski;
import java.util.*;

/*
   @TODO
 - Zmienić z statycznych funkcje na inne niestatyczne
 - Zamienić set na kolejkę w podpunkcie c)
*/


public class Tour {
    public static void print_countries_cities() {
        Map<String, String> countries_cities = new HashMap<>();
        
        countries_cities.put("Polska", "Warszawa");
        countries_cities.put("Niemcy", "Berlin");
        countries_cities.put("Francja", "Paryż");
        countries_cities.put("Włochy", "Rzym");
        countries_cities.put("Hiszpania", "Madryt");
        countries_cities.put("Szwecja", "Sztokholm");
        
        System.out.println("Państwa i ich stolice:");
        for (Map.Entry<String, String> entry : countries_cities.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println("Czy kolekcja zawiera Szwecję: " + countries_cities.containsKey("Szwecja"));
        System.out.println("Liczba elementów w kolekcji: " + countries_cities.size());
    }
    
    public static void print_cities() {
        List<String> cities = new ArrayList<>();
        
        cities.add("Warszawa");
        cities.add("Berlin");
        cities.add("Paryż");
        cities.add("Rzym");
        cities.add("Madryt");
        cities.add("Berlin");
        cities.add("Warszawa"); 
        
        System.out.println("Wszystkie odwiedzone miasta:");
        for (String city : cities) {
            System.out.println(city);
        }
        System.out.println("Czy kolekcja zawiera Paryż: " + cities.contains("Paryż"));
        System.out.println("Liczba elementów w kolekcji: " + cities.size());
    }
    
    public static void print_unique_cities() {
        Set<String> unique_cities = new HashSet<>();
        
        unique_cities.add("Warszawa");
        unique_cities.add("Berlin");
        unique_cities.add("Paryż");
        unique_cities.add("Rzym");
        unique_cities.add("Madryt");
        unique_cities.add("Berlin");
        unique_cities.add("Warszawa"); 
        
        System.out.println("Unikalne odwiedzone miasta:");
        for (String city : unique_cities) {
            System.out.println(city);
        }
        System.out.println("Czy kolekcja zawiera Rzym: " + unique_cities.contains("Rzym"));
        System.out.println("Liczba elementów w kolekcji: " + unique_cities.size());
    }
}
