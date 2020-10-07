package de.mircowerner.seamcarving;

import javax.imageio.ImageIO;
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
                image.setRGB(x, y, pixels[x][y]);
            }
        }
        return image;
    }

    public static void save(BufferedImage image, String type, String file) throws IOException {
        ImageIO.write(image, type, new File(file));
    }
}
