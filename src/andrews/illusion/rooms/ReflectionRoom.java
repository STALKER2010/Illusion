package andrews.illusion.rooms;

import andrews.jengine.Room;

/**
 * @author STALKER_2010
 */
public class ReflectionRoom extends Room {
    public ReflectionRoom() {
        super();
    }

    public ReflectionRoom(String name) {
        super(name);
        background = "game_bg";
        objectsIDs.add("main_point");
        objectsIDs.add("refl_line");
        objectsIDs.add("controller");
    }
}
