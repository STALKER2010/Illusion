package andrews.illusion.objects.parabola;

import andrews.illusion.IllusionGame;
import andrews.jengine.Animation;
import andrews.jengine.GameObject;
import andrews.jengine.modules.Resources;

import java.awt.*;

/**
 * @author STALKER_2010
 */
public class ParabolaBase extends GameObject {

    public ParabolaBase() {
        super();
    }

    public ParabolaBase(String name) {
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
        visible = IllusionGame.instance.currentRoom.equals("parabola_room");
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
        g.drawLine(0, 500, 2000, 500);
    }
}
