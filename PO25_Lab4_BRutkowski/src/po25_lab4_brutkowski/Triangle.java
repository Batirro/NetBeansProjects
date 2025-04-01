package po25_lab4_brutkowski;

public class Triangle {

    public static void print_triangle(int N) {
        int[][] pascale_values = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    pascale_values[i][j] = 1;
                } else {
                    pascale_values[i][j] = pascale_values[i - 1][j - 1] + pascale_values[i - 1][j];
                }
            }
        }

        int[][] triangle = new int[N][2 * N + 1];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                int col = (N - i) + 2 * j;
                triangle[i][col] = pascale_values[i][j];
            }
        }

        for (int i = 0; i < N; i++) {
            System.out.print("[");
            for (int j = 0; j < (2 * N + 1); j++) {
                System.out.print(triangle[i][j]);
                if (j < (2 * N)) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
}