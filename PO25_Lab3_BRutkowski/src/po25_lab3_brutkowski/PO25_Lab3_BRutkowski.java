package po25_lab3_brutkowski;

import java.util.Scanner;

/**
 *
 * @author Bartłomiej Rutkowski
 */
public class PO25_Lab3_BRutkowski {
    public static void main(String[] args) {
        // Input handler
        Scanner input = new Scanner(System.in);
        // Zadanie 1.0
        System.out.println("Podaj N: ");
        int N = input.nextInt();
        Printer p = new Printer();
        p.print_even(N);
        // Zadanie 1.1
        System.out.println("Podaj n: ");
        int n = input.nextInt();
        Fib f = new Fib();
        if (n < 0)
        {
            System.out.println("Liczba n nie może być mniejsza od 0.");
        }
        else
        {
            System.out.println(f.fibbI(n));
            System.out.println(f.fibbR(n+1));
        }
    }
    
}
