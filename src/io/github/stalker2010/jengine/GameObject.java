package io.github.stalker2010.jengine;

import io.github.stalker2010.jengine.modules.Resources;

import java.awt.*;
import java.util.Comparator;

public class GameObject {
    public String sprite = "";
    public String name = "";
    public int depth = 0;
    public boolean visible = true;
    public double x = 0;
    public double y = 0;
    public boolean redraw = true;

    public GameObject() {
    }

    public GameObject(String name) {
        this.name = name;
        this.sprite = name + "_synth_anm";
        final Animation animation = new Animation(this.sprite);
        animation.currentStep = 0;
        animation.isPlaying = false;
        Game.instance.resources.animations.generated.put(this.sprite, animation);
    }

    protected void noSprite() {
        final Animation a = Resources.animation(sprite);
        a.steps.clear();
        a.isLooped = false;
        a.isPlaying = false;
    }

    public void update() {
        Resources.animation(sprite).update();
    }

    public void render(Graphics g) {
        render(g, 1.0f);
    }

    public void render(Graphics g, float scale) {
        final Animation animation = Resources.animation(sprite);
        if (animation != null) {
            Animation.Step step = animation.getStep();
            if ((step != null) && (step.sprite != null)) {
                Resources.sprite(step.sprite).draw(g, x, y);
            }
        }
    }

    /**
     * When key is NOW pressed
     *
     * @param keycode Which key is pressed
     */
    public void onKeyPress(int keycode) {

    }

    /**
     * When key was pressed and released.
     *
     * @param keycode Which key is pressed
     */
    public void onKey(int keycode) {

    }

    public boolean onMouseClick() {
        System.out.println("Clicked object " + name);
        return false;
    }

    public boolean onGlobalMouseClick(final double x, final double y) {
        if (visible) {
            if ((x >= this.x) && (y >= this.y)) {
                final Animation.Step st = Resources.animation(sprite).getStep();
                if (st != null) {
                    final Sprite spr = Resources.sprite(st.sprite);
                    if (spr != null) {
                        if ((this.x + spr.getWidth() > x)
                                && (this.y + spr.getHeight() > y)) {
                            if (onMouseClick()) return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static final Comparator<GameObject> compareByDepth = (o1, o2) -> -Integer.compare(o1.depth, o2.depth);
}
