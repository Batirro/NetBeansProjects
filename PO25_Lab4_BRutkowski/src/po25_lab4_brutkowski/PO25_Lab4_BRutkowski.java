
package po25_lab4_brutkowski;
import java.util.*;

public class PO25_Lab4_BRutkowski {


    public static void main(String[] args) {
        System.out.println("<--- Zadanie 2.0 --->");
        Triangle tr = new Triangle();
        Scanner input = new Scanner(System.in);
        System.out.print("Podaj wysokość trójkąta: ");
        int N = input.nextInt();
        tr.print_triangle(N);
        System.out.println("<--- Zadanie 2.1 --->");
        Tour t = new Tour();
        System.out.println("--- Podpunkt a ---");
        t.print_countries_cities();
        System.out.println("--- Podpunkt b ---");
        t.print_cities();
        System.out.println("--- Podpunkt c ---");
        t.print_unique_cities();
    }
    
}
