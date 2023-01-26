package de.mircowerner.seamcarving;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Seam Carving algorithm.
 * Inspired by this lecture https://www.youtube.com/watch?v=rpB6zQNsbQU by Grant Sanderson.
 * Wiki: https://en.wikipedia.org/wiki/Seam_carving
 *
 * @author Mirco Werner
 */
public class Main {
    private static final String IMAGE_TYPE = "png";

    private static final String FOLDER_SEPARATOR = "/";

    private static final String LUMINANCE = "luminance";
    private static final String GRADIENT = "gradient";
    private static final String CUT_PATH = "cut_path";
    private static final String OUTPUT = "output";

    public static void main(String[] args) throws IOException {
        if (args.length == 5) {
            BufferedImage image = ImageIO.read(new File(args[0]));
            createTargetFolders(args[1]);
            cutPath(image, args[1], Integer.parseInt(args[2]), Boolean.parseBoolean(args[3].toLowerCase()), Boolean.parseBoolean(args[4].toLowerCase()));
        } else {
            System.out.println("Missing arguments: <input image> <output folder> <iterations> <store debug images> <store final result only>");
        }
    }

    private static void createTargetFolders(String folderLocation) throws IOException {
        createFolder(folderLocation);
        createFolder(folderLocation + FOLDER_SEPARATOR + LUMINANCE);
        createFolder(folderLocation + FOLDER_SEPARATOR + GRADIENT);
        createFolder(folderLocation + FOLDER_SEPARATOR + CUT_PATH);
        createFolder(folderLocation + FOLDER_SEPARATOR + OUTPUT);
    }

    private static void createFolder(String folderLocation) throws IOException {
        File folder = new File(folderLocation);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                throw new IOException("Folder at " + folderLocation + " cannot be created.");
            }
        }
    }

    private static void cutPath(BufferedImage image, String folder, int iterations, boolean storeDebugImages, boolean storeFinalResultOnly) throws IOException {
        for (int i = 1; i <= iterations; i++) {
            System.out.println("Iteration... " + i + "/" + iterations);
            int[][] luminanceValues = Luminance.luminanceValues(image); // convert to grayscale image
            float[][] gradientValues = Gradients.gradientValues(luminanceValues, image.getWidth(), image.getHeight()); // calculate gradients of grayscale image
            float[][] pathMatrix = CutPath.getPathMatrix(gradientValues, image.getWidth(), image.getHeight()); // compute energies of paths (dynamic programming)
            int[] path = CutPath.pathMatrixToOptimalPath(pathMatrix, gradientValues, image.getWidth(), image.getHeight()); // get path with the lowest energy

            if (storeDebugImages && (!storeFinalResultOnly || i == iterations)) {
                ImageUtils.save(ImageUtils.toBufferedImage(luminanceValues, image.getWidth(), image.getHeight()), IMAGE_TYPE, imageName(folder, LUMINANCE, i));
                ImageUtils.save(ImageUtils.toBufferedImage(gradientValues, image.getWidth(), image.getHeight()), IMAGE_TYPE, imageName(folder, GRADIENT, i));
                for (int y = 0; y < image.getHeight(); y++) {
                    image.setRGB(path[y], y, Color.MAGENTA.getRGB());
                }
                ImageUtils.save(image, IMAGE_TYPE, imageName(folder, CUT_PATH, i));
            }

            image = ImageUtils.cutOutPath(image, path);

            if (!storeFinalResultOnly || i == iterations) {
                ImageUtils.save(image, IMAGE_TYPE, imageName(folder, OUTPUT, i));
            }
        }
        System.out.println("Done!");
    }

    private static String imageName(String folder, String subFolder, int iteration) {
        return folder + FOLDER_SEPARATOR + subFolder + FOLDER_SEPARATOR + subFolder + "_" + iteration + "." + IMAGE_TYPE;
    }
}
