package io.github.stalker2010.illusion.object;

import io.github.stalker2010.illusion.IllusionGame;
import io.github.stalker2010.jengine.GameObject;

import javax.swing.*;
import java.awt.*;

import static io.github.stalker2010.illusion.object.Utils.d2i;

/**
 * @author STALKER_2010
 */
public class MousePoint extends GameObject {
    public MousePoint() {
        super();
    }

    public MousePoint(String name) {
        super(name);
        noSprite();
        visible = true;
        x = 400;
        y = 300;
        toInit = true;
    }

    public Point lastPoint = new Point(400, 300);
    public boolean toInit = true;

    @Override
    public void update() {
        super.update();
        if (toInit) {
            ((IllusionGame) IllusionGame.instance).setPointsPosition(new Point(d2i(x), d2i(y)));
            toInit = false;
        }
        if (moving) {
            PointerInfo a = MouseInfo.getPointerInfo();
            Point point = new Point(a.getLocation());
            SwingUtilities.convertPointFromScreen(point, IllusionGame.instance);
            if (point.getX() < 900) {
                lastPoint = point;
                this.x = point.getX();
                this.y = point.getY();
                ((IllusionGame) IllusionGame.instance).setPointsPosition(point);
            }
        }
    }

    public boolean moving = false;

    @Override
    public boolean onMouseClick() {
        super.onMouseClick();
        moving = !moving;
        return true;
    }

    @Override
    public boolean onGlobalMouseClick(double x, double y) {
        if (visible) {
            if (moving) {
                if (onMouseClick()) return true;
            } else {
                if ((x >= (this.x - (SIZE / 2))) && (y >= (this.y - (SIZE / 2))) && (x <= (this.x + (SIZE / 2))) && (y <= (this.y + (SIZE / 2)))) {
                    if (onMouseClick()) return true;
                }
            }
        }
        return false;
    }

    public static final int SIZE = 16;

    @Override
    public void render(Graphics g, float scale) {
        g.setColor(Color.lightGray);
        g.fillOval(d2i((d2i(x) - (SIZE / 2)) * scale), d2i((d2i(y) - (SIZE / 2)) * scale), d2i(SIZE * scale), d2i(SIZE * scale));
    }
}
