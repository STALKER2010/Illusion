package io.github.stalker2010.illusion.object;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author STALKER_2010
 */
public class GradientHelper {
    private static final java.util.List<Color> gradient = new ArrayList<>();

    static {
        final int steps = 20 * 20;
        Color color1 = new Color(255, 255, 255);
        Color color2 = Color.yellow;
        for (int i = 0; i < steps; i++) {
            float ratio = (float) i / (float) steps;
            int red = (int) (color2.getRed() * ratio + color1.getRed() * (1 - ratio));
            int green = (int) (color2.getGreen() * ratio + color1.getGreen() * (1 - ratio));
            int blue = (int) (color2.getBlue() * ratio + color1.getBlue() * (1 - ratio));
            Color stepColor = new Color(red, green, blue);
            gradient.add(stepColor);
        }
        color1 = Color.yellow;
        color2 = new Color(255, 255, 255);
        for (int i = 0; i < steps; i++) {
            float ratio = (float) i / (float) steps;
            int red = (int) (color2.getRed() * ratio + color1.getRed() * (1 - ratio));
            int green = (int) (color2.getGreen() * ratio + color1.getGreen() * (1 - ratio));
            int blue = (int) (color2.getBlue() * ratio + color1.getBlue() * (1 - ratio));
            Color stepColor = new Color(red, green, blue);
            gradient.add(stepColor);
        }
    }

    private static int gradientStep = 0;

    public static void update() {
        gradientStep++;
        if (gradientStep >= 20 * 40) {
            gradientStep = 0;
        }
    }

    public static Color get() {
        return gradient.get(gradientStep);
    }
}
