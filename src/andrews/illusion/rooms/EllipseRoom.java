package andrews.illusion.rooms;

import andrews.jengine.Room;

/**
 * @author STALKER_2010
 */
public class EllipseRoom extends Room {
    public EllipseRoom() {
        super();
    }

    public EllipseRoom(String name) {
        super(name);
        background = "game_bg";
        objectsIDs.add("ellipse_mouse_point");
        objectsIDs.add("ellipse_base");
        objectsIDs.add("controller");
    }
}
