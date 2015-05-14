package io.github.stalker2010.illusion;

import io.github.stalker2010.jengine.DB;
import io.github.stalker2010.jengine.Game;
import io.github.stalker2010.jengine.Room;
import io.github.stalker2010.jengine.render.BaseRender;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author STALKER_2010
 */
public class IllusionRender extends BaseRender {
    public IllusionRender(Game game) {
        super(game);
    }

    public List<RoomRender> strategies = new CopyOnWriteArrayList<>();
    public BufferStrategy mainBS;
    public Graphics mainGL;

    @Override
    public boolean init() {
        if (!super.init()) {
            return false;
        }
        strategies.add(new RoomRender(DB.db.rooms.get("parabola_room")));
        strategies.add(new RoomRender(DB.db.rooms.get("ellipse_room")));
        strategies.add(new RoomRender(DB.db.rooms.get("astroid_room")));
        strategies.add(new RoomRender(DB.db.rooms.get("deltoid_room")));
        mainBS = game.getBufferStrategy();
        if (mainBS == null) {
            game.createBufferStrategy(2);
            game.requestFocus();
            mainBS = game.getBufferStrategy();
        }
        if (mainBS == null) {
            System.err.println("Simple2DRender: Can't create buffer strategy");
            return false;
        }
        if (mainGL == null) {
            mainGL = mainBS.getDrawGraphics();
        }
        return true;
    }

    @Override
    public void render(Room room) {
        mainGL = mainBS.getDrawGraphics();
        mainGL.setColor(Color.black);
        room.render(mainGL, 1f);
        strategies.stream()
                .filter(RoomRender::isCorrect)
                .forEach(o -> {
                    final Room r = o.getRoom();
                    if (r.name.equals(room.name)) {
                        objs.stream()
                                .filter(o1 -> (r.objectsIDs.contains(o1.name) || o1.name.startsWith("menu_object_")))
                                .filter(o1 -> o1.visible)
                                .forEach(o1 -> {
                                    try {
                                        o1.render(mainGL);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                    }
                });
        mainBS.show();
//        mainBS.dispose();
        mainGL.dispose();
    }

    public void resetAndFixEverything() {
        if (mainGL != null) {
            mainGL.dispose();
            mainGL = null;
        }
        if (mainBS != null) {
            mainBS.dispose();
            mainBS = null;
        }
        strategies.clear();
        init();
    }

    public class RoomRender {
        public WeakReference<Room> room;

        public RoomRender(final Room room) {
            this.room = new WeakReference<>(room);
        }

        public boolean isCorrect() {
            return room.get() != null;
        }

        public Room getRoom() {
            return room.get();
        }
    }
}
