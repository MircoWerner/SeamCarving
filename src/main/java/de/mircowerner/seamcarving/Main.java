package de.mircowerner.seamcarving;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    private static final String IMAGE_TYPE = "png";

    private static final String FOLDER_SEPARATOR = "/";

    private static final String LUMINANCE = "luminance";
    private static final String GRADIENT = "gradient";
    private static final String PATH = "path";
    private static final String CUT_PATH = "cut_path";
    private static final String OUTPUT = "output";

    public static void main(String[] args) throws IOException {
        if (args.length == 3) {
            BufferedImage image = ImageIO.read(new File(args[0]));
            createTargetFolders(args[1]);
            cutPath(image, args[1], Integer.parseInt(args[2]));
        } else {
            System.out.println("Missing arguments: <input image> <output folder> <iterations>");
        }
    }

    private static void createTargetFolders(String folderLocation) throws IOException {
        createFolder(folderLocation);
        createFolder(folderLocation + FOLDER_SEPARATOR + LUMINANCE);
        createFolder(folderLocation + FOLDER_SEPARATOR + GRADIENT);
        createFolder(folderLocation + FOLDER_SEPARATOR + PATH);
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

    private static void cutPath(BufferedImage image, String folder, int iterations) throws IOException {
        for (int i = 1; i <= iterations; i++) {
            int[][] luminanceValues = Luminance.luminanceValues(image);
            float[][] gradientValues = Gradients.gradientValues(luminanceValues, image.getWidth(), image.getHeight());
            float[][] pathMatrix = CutPath.getPathMatrix(gradientValues, image.getWidth(), image.getHeight());
            int[] path = CutPath.pathMatrixToOptimalPath(pathMatrix, gradientValues, image.getWidth(), image.getHeight());

            ImageUtils.save(ImageUtils.toBufferedImage(luminanceValues, image.getWidth(), image.getHeight()), IMAGE_TYPE, imageName(folder, LUMINANCE, i));
            ImageUtils.save(ImageUtils.toBufferedImage(gradientValues, image.getWidth(), image.getHeight()), IMAGE_TYPE, imageName(folder, GRADIENT, i));
            ImageUtils.save(ImageUtils.toBufferedImage(pathMatrix, image.getWidth(), image.getHeight()), IMAGE_TYPE, imageName(folder, PATH, i));

            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(path[y], y, Color.MAGENTA.getRGB());
            }

            ImageUtils.save(image, IMAGE_TYPE, imageName(folder, CUT_PATH, i));

            image = ImageUtils.cutOutPath(image, path);

            ImageUtils.save(image, IMAGE_TYPE, imageName(folder, OUTPUT, i));
        }
    }

    private static String imageName(String folder, String subFolder, int iteration) {
        return folder + FOLDER_SEPARATOR + subFolder + FOLDER_SEPARATOR + subFolder + "_" + iteration + "." + IMAGE_TYPE;
    }
}
