package de.mircowerner.seamcarving;

public final class CutPath {
    private CutPath() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class constructor");
    }

    public static float[][] getPathMatrix(float[][] energy, int width, int height) {
        float[][] matrix = new float[width][height];

        // init
        for (int x = 0; x < width; x++) {
            matrix[x][0] = energy[x][0];
        }

        // Go for each row from top to bottom.
        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Test which one of the three pixels above of (x,y) has the lowest energy value. That is the pixel on
                // the optimal path from the top to (x,y).
                int minRowAbove = 0;
                if (x > 0 && matrix[x - 1][y - 1] < matrix[x][y - 1]) {
                    minRowAbove = -1;
                }
                if (x < width - 1 && matrix[x + 1][y - 1] < matrix[x + minRowAbove][y - 1]) {
                    minRowAbove = 1;
                }
                // Save the sum of the energies of the optimal path and the energy value of (x,y) in the matrix entry
                // for (x,y).
                matrix[x][y] = matrix[x + minRowAbove][y - 1] + energy[x][y];
            }
        }

        return matrix;
    }

    public static int[] pathMatrixToOptimalPath(float[][] matrix, float[][] energy, int width, int height) {
        // Find the pixel with the lowest sum of energies at the bottom. That is the end pixel of the optimal path.
        int xPos = 0;
        for (int x = 1; x < width; x++) {
            if (matrix[x][height - 1] < matrix[xPos][height - 1]) {
                xPos = x;
            }
        }

        // Reconstruct the optimal path from bottom to top.
        int[] path = new int[height];
        path[height - 1] = xPos;
        for (int y = height - 2; y >= 0; y--) {
            // Check which pixel is the predecessor of the pixel in the row one lower.
            float diff = matrix[path[y + 1]][y + 1] - energy[path[y + 1]][y + 1];
            if (path[y + 1] > 0 && diff == matrix[path[y + 1] - 1][y]) {
                path[y] = path[y + 1] - 1;
            } else if (path[y + 1] < width - 1 && diff == matrix[path[y + 1] + 1][y]) {
                path[y] = path[y + 1] + 1;
            } else {
                path[y] = path[y + 1];
            }
        }

        return path;
    }
}
