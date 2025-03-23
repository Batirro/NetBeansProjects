package po25_lab4_brutkowski;

public class PO25_Lab4_BRutkowski {

    public static void main(String[] args) {
        
        Triangle tr = new Triangle();
        System.out.println("Trójkąt Pascala dla N=5:");
        tr.print_triangle(5);
        
        System.out.println("\n--- Zadanie 3.1 - Tour ---");
        
        Tour t = new Tour();
        
        t.printCountriesWithCapitals();
        t.printVisitedCities();
        t.printUniqueCities();
        t.checkSwedenInCountries();
        t.checkParisInVisitedCities();
        t.checkRomeInUniqueCities();
        t.printCollectionSizes();
    }
    
}
