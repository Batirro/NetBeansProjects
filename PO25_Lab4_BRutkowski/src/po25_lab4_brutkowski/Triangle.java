package po25_lab4_brutkowski;
public class Triangle {
    public static void print_triangle(int N) {
        // Przechowywanie liczb w tablicy
        int[][] triangle = new int[N][N];
        
        // Wypełnianie tablicy wartościami trójkąta Pascala
        for (int i = 0; i < N; i++) {
            // Pierwsza wartość w każdym rzędzie to 1
            triangle[i][0] = 1;
            
            for (int j = 1; j <= i; j++) {
                // Każda wartość to suma dwóch liczb z poprzedniego rzędu
                triangle[i][j] = triangle[i-1][j-1] + (j < i ? triangle[i-1][j] : 0);
            }
        }
        
        // Wypisanie trójkąta Pascala
        for (int i = 0; i < N; i++) {
            // Dodanie odstępów dla wyrównania
            for (int space = 0; space < N - i - 1; space++) {
                System.out.print("  ");
            }
            
            // Wypisanie wartości dla danego rzędu
            for (int j = 0; j <= i; j++) {
                System.out.printf("%4d", triangle[i][j]);
            }
            System.out.println();
        }
    }
}
