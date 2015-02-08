package andrews.illusion.objects;

import andrews.illusion.IllusionGame;
import andrews.jengine.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Controller extends GameObject {
    public Controller() {
        super();
    }

    public Controller(String name) {
        super(name);
        visible = false;
        redraw = false;
    }

    public static final int MODE_PARABOLIC = 0;
    public static final int MODE_CIRCLE = 1;
    public static final int MODE_ASTROID = 2;
    public static final int MODE_SIMPSON = 3;
    public boolean isLimited = false;
    public int mode = MODE_PARABOLIC;

    @Override
    public void update() {
        super.update();
        GradientHelper.update();
    }

    @Override
    public void onKey(int keycode) {
        super.onKey(keycode);
        switch (keycode) {
            case (KeyEvent.VK_1): {
                IllusionGame.instance.currentRoom = "reflection_room";
                mode = MODE_PARABOLIC;
                break;
            }
            case (KeyEvent.VK_2): {
                IllusionGame.instance.currentRoom = "reflection_room";
                mode = MODE_CIRCLE;
                break;
            }
            case (KeyEvent.VK_3): {
                IllusionGame.instance.currentRoom = "astroid_room";
                mode = MODE_ASTROID;
                break;
            }
            case (KeyEvent.VK_4): {
                IllusionGame.instance.currentRoom = "deltoid_room";
                mode = MODE_SIMPSON;
                break;
            }
            case (KeyEvent.VK_5): {
                isLimited = !isLimited;
                break;
            }
        }
    }


    @Override
    public void onMouseClick() {
    }

    @Override
    public void onGlobalMouseClick(double x, double y) {
    }

    @Override
    public void render(Graphics g) {
    }
}