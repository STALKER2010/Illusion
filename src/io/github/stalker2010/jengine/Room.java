package io.github.stalker2010.jengine;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.stalker2010.jengine.DB.db;

public class Room implements Iterable<String> {
    public String background = "";
    public List<String> objectsIDs = new ArrayList<>();
    public String name = "";

    public Room() {

    }

    public Room(String name) {
        this.name = name;
    }

    @Override
    public Iterator<String> iterator() {
        return objectsIDs.listIterator();
    }

    public void update() {
    }

    public void onRoomEnter(String previousRoomName) {
    }

    public void onRoomLeave() {

    }

    public void render(Graphics g, float scale) {
        if (background != null) {
            final Background b = db.backgrounds.get(background);
            if (b != null) {
                b.draw(g);
            }
        }
    }
}
