package de.mircowerner.seamcarving;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageUtils {
    private ImageUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class constructor");
    }

    public static BufferedImage toBufferedImage(int[][] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int c = Math.min(pixels[x][y], 255);
                image.setRGB(x, y, new Color(c, c, c).getRGB());
            }
        }
        return image;
    }

    public static BufferedImage toBufferedImage(float[][] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int c = Math.min((int) pixels[x][y], 255);
                image.setRGB(x, y, new Color(c, c, c).getRGB());
            }
        }
        return image;
    }

    public static void save(BufferedImage image, String type, String file) throws IOException {
        ImageIO.write(image, type, new File(file));
    }
}
