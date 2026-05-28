public class SimpleTask2MatrixMax {

    static int[] rowMax;

    public static void main(String[] args) throws InterruptedException {
        int[][] matrix = {
                {5, 14, 2, 8},
                {19, 3, 7, 11},
                {6, 25, 1, 4},
                {13, 10, 17, 9}
        };

        rowMax = new int[matrix.length];
        Thread[] threads = new Thread[matrix.length];

        for (int row = 0; row < matrix.length; row++) {
            final int r = row; 

            threads[row] = new Thread(() -> {
                int max = matrix[r][0];
                for (int col = 0; col < matrix[r].length; col++) {
                    if (matrix[r][col] > max) {
                        max = matrix[r][col];
                    }
                }
                rowMax[r] = max;
            });

            threads[row].start();
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        int answer = rowMax[0];
        for (int i = 0; i < rowMax.length; i++) {
            if (rowMax[i] > answer) {
                answer = rowMax[i];
            }
        }

        System.out.println("Наибольший элемент матрицы: " + answer);
    }
}
