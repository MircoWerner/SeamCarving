package de.mircowerner.seamcarving;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            BufferedImage image = ImageIO.read(new File(args[0]));
            cutPath(image, args[1]);
        } else {
            System.out.println("Missing arguments: <input image> <output location>");
        }
    }

    private static void cutPath(BufferedImage image, String out) throws IOException {
        int[][] luminanceValues = Luminance.luminanceValues(image);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int luminanceValue = luminanceValues[x][y];
                luminanceValues[x][y] = new Color(luminanceValue, luminanceValue, luminanceValue).getRGB();
            }
        }
        ImageUtils.save(ImageUtils.toBufferedImage(luminanceValues, image.getWidth(), image.getHeight()), "png", "luminance_" + out);

        int[] path = new CutPath(image).calculateCutPath();

        for (int y = 0; y < image.getHeight(); y++) {
            image.setRGB(path[y], y, Color.MAGENTA.getRGB());
        }

        ImageUtils.save(image, "png", out);
    }
}
