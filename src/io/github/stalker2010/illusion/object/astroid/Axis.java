package io.github.stalker2010.illusion.object.astroid;

import io.github.stalker2010.jengine.GameObject;

import java.awt.*;

import static io.github.stalker2010.illusion.object.Utils.d2i;

/**
 * @author STALKER_2010
 */
public class Axis extends GameObject {

    public Axis() {
        super();
    }

    public Axis(String name) {
        super("axis");
        noSprite();
        visible = true;
    }

    @Override
    public void update() {
        super.update();
//        visible = !IllusionGame.instance.currentRoom.equals("reflection_room");
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
        g.drawLine(d2i(0 * scale), d2i(300 * scale), d2i(2000 * scale), d2i(300 * scale));
        g.drawLine(d2i(500 * scale), d2i(0 * scale), d2i(500 * scale), d2i(800 * scale));
    }
}
