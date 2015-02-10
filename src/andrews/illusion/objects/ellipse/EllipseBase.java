package andrews.illusion.objects.ellipse;

import andrews.illusion.IllusionGame;
import andrews.jengine.Animation;
import andrews.jengine.GameObject;
import andrews.jengine.modules.Resources;

import java.awt.*;

/**
 * @author STALKER_2010
 */
public class EllipseBase extends GameObject {

    public EllipseBase() {
        super();
    }

    public EllipseBase(String name) {
        super(name);
        final Animation a = Resources.animation(sprite);
        a.steps.clear();
        a.isLooped = false;
        a.isPlaying = false;
        visible = true;
    }

    @Override
    public void update() {
        super.update();
        visible = IllusionGame.instance.currentRoom.equals("ellipse_room");
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
        g.drawOval(200, 100, 400, 400);
    }
}
