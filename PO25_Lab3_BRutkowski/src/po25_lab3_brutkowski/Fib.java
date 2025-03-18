package po25_lab3_brutkowski;

/**
 *
 * @author Bartłomiej Rutkowski
 */
public class Fib {
    public static int fibbI(int n){
        if (n == 0) return 0;
        if (n == 1) return 1;

        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
    public static int fibbR(int n){
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibbR(n - 1) + fibbR(n - 2);
    }
}
