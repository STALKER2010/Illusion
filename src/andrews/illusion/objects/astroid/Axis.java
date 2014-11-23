package andrews.illusion.objects.astroid;

import andrews.illusion.IllusionGame;
import andrews.illusion.objects.Controller;
import andrews.jengine.Animation;
import andrews.jengine.DB;
import andrews.jengine.GameObject;
import andrews.jengine.modules.Resources;

import java.awt.*;

/**
 * @author STALKER_2010
 */
public class Axis extends GameObject {

    public Axis() {
        super();
    }

    public Axis(String name) {
        super("axis");
        final Animation a = Resources.animation(sprite);
        a.steps.clear();
        a.isLooped = false;
        a.isPlaying = false;
        visible = true;
    }

    @Override
    public void update() {
        super.update();
        visible = !IllusionGame.instance.currentRoom.equals("reflection_room");
    }


    @Override
    public void onMouseClick() {
    }

    @Override
    public void onGlobalMouseClick(double x, double y) {
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.lightGray);
        if (((Controller) DB.db.objects.get("controller")).mode == Controller.MODE_ASTROID) {
            g.drawLine(0, 300, 2000, 300);
            g.drawLine(500, 0, 500, 800);
        }
    }
}
