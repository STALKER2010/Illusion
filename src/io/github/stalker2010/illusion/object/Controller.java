package io.github.stalker2010.illusion.object;

import io.github.stalker2010.illusion.IllusionGame;
import io.github.stalker2010.jengine.DB;
import io.github.stalker2010.jengine.GameObject;
import io.github.stalker2010.jengine.Room;

import java.awt.event.KeyEvent;

public class Controller extends GameObject {
    public Controller() {
        super();
    }

    public Controller(String name) {
        super(name);
        visible = false;
        redraw = false;
    }

    public static final int MODE_PARABOLIC = 0;
    public static final int MODE_CIRCLE = 1;
    public static final int MODE_ASTROID = 2;
    public static final int MODE_SIMSON = 3;
    public static boolean isLimited = false;
    public static int mode = MODE_PARABOLIC;

    @Override
    public void update() {
        super.update();
        GradientHelper.update();
    }

    @Override
    public void onKey(int keycode) {
        super.onKey(keycode);
        final String prevRoom = IllusionGame.instance.currentRoom;
        final Room from = DB.db.rooms.get(prevRoom);
        switch (keycode) {
            case (KeyEvent.VK_1): {
                IllusionGame.instance.currentRoom = "parabola_room";
                DB.db.rooms.get(prevRoom).onRoomLeave();
                final Room to = DB.db.rooms.get(IllusionGame.instance.currentRoom);
                DB.db.rooms.get(to.name).onRoomEnter(prevRoom);
                ((IllusionGame)IllusionGame.instance).changeIntoRoom(from, to);
                mode = MODE_PARABOLIC;
                break;
            }
            case (KeyEvent.VK_2): {
                IllusionGame.instance.currentRoom = "ellipse_room";
                DB.db.rooms.get(prevRoom).onRoomLeave();
                final Room to = DB.db.rooms.get(IllusionGame.instance.currentRoom);
                DB.db.rooms.get(to.name).onRoomEnter(prevRoom);
                ((IllusionGame)IllusionGame.instance).changeIntoRoom(from, to);
                mode = MODE_CIRCLE;
                break;
            }
            case (KeyEvent.VK_3): {
                IllusionGame.instance.currentRoom = "astroid_room";
                DB.db.rooms.get(prevRoom).onRoomLeave();
                final Room to = DB.db.rooms.get(IllusionGame.instance.currentRoom);
                DB.db.rooms.get(to.name).onRoomEnter(prevRoom);
                ((IllusionGame)IllusionGame.instance).changeIntoRoom(from, to);
                mode = MODE_ASTROID;
                break;
            }
            case (KeyEvent.VK_4): {
                IllusionGame.instance.currentRoom = "deltoid_room";
                DB.db.rooms.get(prevRoom).onRoomLeave();
                final Room to = DB.db.rooms.get(IllusionGame.instance.currentRoom);
                DB.db.rooms.get(to.name).onRoomEnter(prevRoom);
                ((IllusionGame)IllusionGame.instance).changeIntoRoom(from, to);
                mode = MODE_SIMSON;
                break;
            }
            case (KeyEvent.VK_5): {
                isLimited = !isLimited;
                break;
            }
        }
    }


    @Override
    public boolean onMouseClick() {
        return false;
    }

    @Override
    public boolean onGlobalMouseClick(double x, double y) {
        return false;
    }

}