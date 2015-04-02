package io.github.stalker2010.jengine.render;

import io.github.stalker2010.jengine.Game;
import io.github.stalker2010.jengine.Room;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ConcurrentModificationException;

/**
 * @author STALKER_2010
 */
public class Simple2DRender extends BaseRender {
    public BufferStrategy bss;
    public Graphics gl;

    public Simple2DRender(Game game) {
        super(game);
    }

    @Override
    public boolean init() {
        if (!super.init()) return false;
        bss = game.getBufferStrategy();
        if (bss == null) {
            game.createBufferStrategy(2);
            game.requestFocus();
            bss = game.getBufferStrategy();
        }
        if (bss == null) {
            System.err.println("Simple2DRender: Can't create buffer strategy");
            return false;
        }
        if (gl == null) {
            gl = bss.getDrawGraphics();
        }
        return true;
    }

    @Override
    public void render(Room room) {
        gl.setColor(Color.black);
        {
            room.render(gl, 1f);
            objs.stream()
                    .filter(o -> room.objectsIDs.contains(o.name))
                    .filter(o -> o.visible)
                    .forEach(o -> {
                        try {
                            o.render(gl);
                        } catch (Exception e) {
                            if (!(e instanceof ConcurrentModificationException)) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
        bss.show();
    }
}
