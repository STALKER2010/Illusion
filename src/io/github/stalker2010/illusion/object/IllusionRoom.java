package io.github.stalker2010.illusion.object;

import io.github.stalker2010.jengine.DB;
import io.github.stalker2010.jengine.Room;

/**
 * @author STALKER_2010
 */
public abstract class IllusionRoom extends Room {
    public IllusionRoom() {
        super();
    }

    public IllusionRoom(String name) {
        super(name);
        background = "game_bg";
        objectsIDs.add("controller");
    }

    @Override
    public void update() {
        super.update();
    }

    public abstract void recalculate();

    public MousePoint getPoint() {
        return (MousePoint) DB.db.objects.get("mouse_point");
    }
}
