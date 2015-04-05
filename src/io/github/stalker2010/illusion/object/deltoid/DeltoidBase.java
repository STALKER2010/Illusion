package io.github.stalker2010.illusion.object.deltoid;

import io.github.stalker2010.illusion.object.GradientHelper;
import io.github.stalker2010.illusion.object.Line;
import io.github.stalker2010.jengine.GameObject;

import java.awt.*;

import static io.github.stalker2010.illusion.object.Utils.d2i;

/**
 * @author STALKER_2010
 */
public class DeltoidBase extends GameObject {

    public DeltoidBase() {
        super();
    }

    public DeltoidBase(String name) {
        super(name);
        noSprite();
        visible = true;
        rotateTriangle(0);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public boolean onMouseClick() {
        return false;
    }

    @Override
    public boolean onGlobalMouseClick(double x, double y) {
        return false;
    }

    public static final Line
            side1 = new Line().set(235, 414, 400, 100),
            side2 = new Line().set(400, 100, 566, 414),
            side3 = new Line().set(235, 414, 566, 414);
    public static final Line
            extendedSide1 = new Line(),
            extendedSide2 = new Line(),
            extendedSide3 = new Line();

    public static void rotateTriangle(double angle) {
        {
            angle = (float) Math.toRadians(angle); // Angle to rotate

            // Display coordinates where triangle will be drawn
            float centerX = 400;
            float centerY = 300;

            // Vertex's coordinates before rotating
            float x1 = 235;
            float y1 = 414;
            float x2 = 400;
            float y2 = 100;
            float x3 = 566;
            float y3 = 414;

            // Rotating
            double x1r = centerX + (x1 - centerX) * Math.cos(angle) - (y1 - centerY) * Math.sin(angle);
            double y1r = centerY + (x1 - centerX) * Math.sin(angle) + (y1 - centerY) * Math.cos(angle);
            double x2r = centerX + (x2 - centerX) * Math.cos(angle) - (y2 - centerY) * Math.sin(angle);
            double y2r = centerY + (x2 - centerX) * Math.sin(angle) + (y2 - centerY) * Math.cos(angle);
            double x3r = centerX + (x3 - centerX) * Math.cos(angle) - (y3 - centerY) * Math.sin(angle);
            double y3r = centerY + (x3 - centerX) * Math.sin(angle) + (y3 - centerY) * Math.cos(angle);

            side1.set(x1r, y1r, x2r, y2r);
            side2.set(x2r, y2r, x3r, y3r);
            side3.set(x1r, y1r, x3r, y3r);
        }
        {
            double x3 = side1.x2 + (side1.x2 - side1.x1);
            double y3 = side1.y2 + (side1.y2 - side1.y1);
            double x4 = side1.x1 - (side1.x2 - side1.x1);
            double y4 = side1.y1 - (side1.y2 - side1.y1);
            extendedSide1.set(x3, y3, x4, y4);
        }
        {
            double x3 = side2.x2 + (side2.x2 - side2.x1);
            double y3 = side2.y2 + (side2.y2 - side2.y1);
            double x4 = side2.x1 - (side2.x2 - side2.x1);
            double y4 = side2.y1 - (side2.y2 - side2.y1);
            extendedSide2.set(x3, y3, x4, y4);
        }
        {
            double x3 = side3.x2 + (side3.x2 - side3.x1);
            double y3 = side3.y2 + (side3.y2 - side3.y1);
            double x4 = side3.x1 - (side3.x2 - side3.x1);
            double y4 = side3.y1 - (side3.y2 - side3.y1);
            extendedSide3.set(x3, y3, x4, y4);
        }
    }

    @Override
    public void render(Graphics g, float scale) {
        g.setColor(GradientHelper.get());
        g.drawOval(d2i(200 * scale), d2i(100 * scale), d2i(400 * scale), d2i(400 * scale)); //center: 400; 300
        extendedSide1.draw(g, scale);
        extendedSide2.draw(g, scale);
        extendedSide3.draw(g, scale);
    }
}
