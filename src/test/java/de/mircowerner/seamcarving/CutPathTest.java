package de.mircowerner.seamcarving;

import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;

public class CutPathTest {

    @Test
    public void testCorrectPath() {
        BufferedImage bufferedImage = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
        int black = Color.BLACK.getRGB();
        int white = Color.WHITE.getRGB();

        CutPath cutPath = new CutPath(bufferedImage);

        assertEquals(0f, cutPath.getEnergy(black), 0.01f);
        assertEquals(1f, cutPath.getEnergy(white), 0.01f);
    }
}
