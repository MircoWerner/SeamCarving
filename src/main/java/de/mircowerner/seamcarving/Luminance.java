package de.mircowerner.seamcarving;

import java.awt.Color;
import java.awt.image.BufferedImage;

public final class Luminance {
    private Luminance() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class constructor");
    }

    public static int[][] luminanceValues(BufferedImage image) {
        int[][] luminanceValues = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                luminanceValues[x][y] = luminanceValue(image.getRGB(x, y));
            }
        }
        return luminanceValues;
    }

    private static int luminanceValue(int rgba) {
        Color color = new Color(rgba, true);
        // Calculate the optical brightness of a color. Works pretty well. 0 = black, 1 = white.
        return (int)(color.getRed() * 0.299f + color.getGreen() * 0.587f + color.getBlue() * 0.114f);
    }
}
