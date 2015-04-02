package io.github.stalker2010.jengine.render;

import io.github.stalker2010.jengine.Game;
import io.github.stalker2010.jengine.GameObject;
import io.github.stalker2010.jengine.Room;

import java.util.ArrayList;

/**
 * @author STALKER_2010
 */
public abstract class BaseRender {
    protected final Game game;

    public BaseRender(Game game) {
        this.game = game;
    }

    public boolean isInitialized = false;

    public boolean init() {
        System.setProperty("sun.java2d.transaccel", "True");
        System.setProperty("sun.java2d.d3d", "True");
        System.setProperty("sun.java2d.ddforcevram", "True");
        isInitialized = true;
        return true;
    }

    public final java.util.List<GameObject> objs = new ArrayList<>();

    public abstract void render(final Room room);
}
