package po25_lab5_brutkowski;

import java.util.*;

public class Tour {

    private Map<String, String> countriesCities;
    private List<String> cities;
    private Queue<String> uniqueCities;
    
    public Tour() {
        this.countriesCities = new HashMap<>();
        this.cities = new ArrayList<>();
        this.uniqueCities = new LinkedList<>();
        
        initializeCountriesCities();
        initializeCities();
        initializeUniqueCities();
    }
    
    private void initializeCountriesCities() {
        countriesCities.put("Polska", "Warszawa");
        countriesCities.put("Niemcy", "Berlin");
        countriesCities.put("Francja", "Paryż");
        countriesCities.put("Włochy", "Rzym");
        countriesCities.put("Hiszpania", "Madryt");
    }
    
    private void initializeCities() {
        cities.add("Warszawa");
        cities.add("Berlin");
        cities.add("Paryż");
        cities.add("Berlin");  // powtórzenie
        cities.add("Rzym");
        cities.add("Madryt");
        cities.add("Barcelona");
        cities.add("Wenecja");
        cities.add("Rzym");    // powtórzenie
    }
    
    private void initializeUniqueCities() {
        uniqueCities.add("Warszawa");
        uniqueCities.add("Berlin");
        uniqueCities.add("Paryż");
        uniqueCities.add("Rzym");
        uniqueCities.add("Madryt");
        uniqueCities.add("Barcelona");
        uniqueCities.add("Wenecja");
        uniqueCities.add("Berlin");  // powtórzenie
        uniqueCities.add("Rzym");    // powtórzenie
    }
    
    public Map<String, String> getCountriesCities() {
        return countriesCities;
    }
    
    public List<String> getCities() {
        return cities;
    }
    
    public Queue<String> getUniqueCities() {
        return uniqueCities;
    }
    
    public void printCountriesCities() {
        System.out.println("Kolekcja państw i ich stolic:");
        for (Map.Entry<String, String> entry : countriesCities.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        
        boolean containsSweden = countriesCities.containsKey("Szwecja");
        System.out.println("Czy kolekcja zawiera Szwecję? " + containsSweden);
        
        System.out.println("Liczba elementów kolekcji: " + countriesCities.size());
        System.out.println();
    }
    
    public void printCities() {
        System.out.println("Kolekcja wszystkich odwiedzonych miast (mogą się powtarzać):");
        for (String city : cities) {
            System.out.println(city);
        }
        
        boolean containsParis = cities.contains("Paryż");
        System.out.println("Czy kolekcja zawiera Paryż? " + containsParis);
        
        System.out.println("Liczba elementów kolekcji: " + cities.size());
        System.out.println();
    }
    
    public void printUniqueCities() {
        System.out.println("Kolekcja unikalnych miast (bez powtórzeń):");
        
        Set<String> printedCities = new HashSet<>();
        
        for (String city : uniqueCities) {
            if (!printedCities.contains(city)) {
                System.out.println(city);
                printedCities.add(city);
            }
        }
        
        boolean containsRome = uniqueCities.contains("Rzym");
        System.out.println("Czy kolekcja zawiera Rzym? " + containsRome);
        
        // Liczba unikalnych elementów
        System.out.println("Liczba unikalnych elementów kolekcji: " + printedCities.size());
        System.out.println("Całkowita liczba elementów w kolejce: " + uniqueCities.size());
        System.out.println();
    }
}