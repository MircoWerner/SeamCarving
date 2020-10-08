package de.mircowerner.seamcarving;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class Gradients {
    private Gradients() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class constructor");
    }

    public static float[][] gradientValues(int[][] luminanceValues, int width, int height) {
        luminanceValues = addSurroundingArrayEntries(luminanceValues, width, height);
        float[][] gradientValues = new float[width][height];
        for (int x = 1; x < width + 1; x++) {
            for (int y = 1; y < height + 1; y++) {
                gradientValues[x - 1][y - 1] = gradientValue(
                        luminanceValues[x - 1][y - 1], luminanceValues[x][y - 1], luminanceValues[x + 1][y - 1],
                        luminanceValues[x - 1][y], luminanceValues[x][y], luminanceValues[x + 1][y],
                        luminanceValues[x - 1][y + 1], luminanceValues[x][y + 1], luminanceValues[x + 1][y + 1]);
            }
        }
        return gradientValues;
    }

    private static int[][] addSurroundingArrayEntries(int[][] luminanceValues, int width, int height) {
        int[][] largerArray = new int[width + 2][height + 2];
        for (int x = 1; x < width + 1; x++) {
            if (height + 1 - 1 >= 0)
                System.arraycopy(luminanceValues[x - 1], 0, largerArray[x], 1, height);
        }
        System.arraycopy(luminanceValues[0], 0, largerArray[0], 1, height);
        System.arraycopy(luminanceValues[width - 1], 0, largerArray[width + 1], 1, height);
        for (int x = 1; x < width + 1; x++) {
            largerArray[x][0] = luminanceValues[x - 1][0];
            largerArray[x][height + 1] = luminanceValues[x - 1][height - 1];
        }
        largerArray[0][0] = luminanceValues[0][0];
        largerArray[width + 1][0] = luminanceValues[width - 1][0];
        largerArray[0][height + 1] = luminanceValues[0][height - 1];
        largerArray[width + 1][height + 1] = luminanceValues[width - 1][height - 1];

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\mirco\\Desktop\\SeamCarving\\luminance_values.txt", false));
            for (int y = 0; y < height + 2; y++) {
                for (int x = 0; x < width + 2; x++) {
                    bufferedWriter.write(largerArray[x][y] + " ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return largerArray;
    }

    private static float gradientValue(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
        int xGradient = c - a + 2 * (f - d) + i - g;
        int yGradient = g - a + 2 * (h - b) + i - c;
        return (float) Math.sqrt(xGradient * xGradient + yGradient * yGradient);
    }
}
