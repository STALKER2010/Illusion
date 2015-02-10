package andrews.illusion.rooms;

import andrews.jengine.Room;

/**
 * @author STALKER_2010
 */
public class ParabolaRoom extends Room {
    public ParabolaRoom() {
        super();
    }

    public ParabolaRoom(String name) {
        super(name);
        background = "game_bg";
        objectsIDs.add("parabola_mouse_point");
        objectsIDs.add("parabola_base");
        objectsIDs.add("controller");
    }
}
