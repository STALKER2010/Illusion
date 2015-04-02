package io.github.stalker2010.jengine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DB {
    public final Map<String, GameObject> objects = new ConcurrentHashMap<>();
    public final Map<String, Background> backgrounds = new ConcurrentHashMap<>();
    public final Map<String, Room> rooms = new ConcurrentHashMap<>();
    public boolean locked = false;
    public static final DB db = new DB();

    public void onGameLoaded(final Game game) {
    }

    public String getFreeName(String prefix) {
        int i = 0;
        while (objects.containsKey(prefix + i)) {
            i++;
        }
        return prefix + i;
    }

    public static void setLocked(final boolean lock) {
        db.locked = lock;
    }
}
