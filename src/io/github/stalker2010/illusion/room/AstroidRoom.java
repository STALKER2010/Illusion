package io.github.stalker2010.illusion.room;

import io.github.stalker2010.illusion.object.IllusionRoom;

/**
 * @author STALKER_2010
 */
public class AstroidRoom extends IllusionRoom {
    public AstroidRoom(String name) {
        super(name);
        objectsIDs.add("axis");
        objectsIDs.add("communicator");
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void recalculate() {

    }
}
