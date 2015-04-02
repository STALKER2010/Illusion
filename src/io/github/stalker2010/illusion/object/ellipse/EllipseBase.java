package io.github.stalker2010.illusion.object.ellipse;

import io.github.stalker2010.jengine.GameObject;

import java.awt.*;

import static io.github.stalker2010.illusion.object.Utils.d2i;

/**
 * @author STALKER_2010
 */
public class EllipseBase extends GameObject {

    public EllipseBase() {
        super();
    }

    public EllipseBase(String name) {
        super(name);
        noSprite();
        visible = true;
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

    @Override
    public void render(Graphics g, float scale) {
        g.setColor(Color.lightGray);
        g.drawOval(d2i(200 * scale), d2i(100 * scale), d2i(400 * scale), d2i(400 * scale));
    }
}
