package andrews.illusion.rooms;

import andrews.jengine.Room;

/**
 * @author STALKER_2010
 */
public class AstroidRoom extends Room {
    public AstroidRoom() {
        super();
    }
    public AstroidRoom(String name) {
        super("astroid_room");
        background = "game_bg";
        objectsIDs.add("axis");
        objectsIDs.add("communicator");
        objectsIDs.add("controller");
    }
}
