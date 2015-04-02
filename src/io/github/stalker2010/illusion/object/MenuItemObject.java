package io.github.stalker2010.illusion.object;

import io.github.stalker2010.illusion.IllusionGame;
import io.github.stalker2010.illusion.IllusionRender;
import io.github.stalker2010.jengine.DB;
import io.github.stalker2010.jengine.GameObject;
import io.github.stalker2010.jengine.Room;

import java.awt.*;

import static io.github.stalker2010.illusion.object.Utils.d2i;

/**
 * @author STALKER_2010
 */
public class MenuItemObject extends GameObject {

    public MenuItemObject() {
        super();
    }

    public MenuItemObject(String name) {
        super(name);
        visible = true;
        depth = 5;
        x = 400;
        y = 400;
    }

    @Override
    public void update() {
        super.update();
    }

    public boolean moving = false;

    @Override
    public boolean onMouseClick() {
        super.onMouseClick();
        final String prevRoom = IllusionGame.instance.currentRoom;
        final Room from = DB.db.rooms.get(prevRoom);
        IllusionGame.instance.currentRoom = menuNameToRoomName();
        DB.db.rooms.get(prevRoom).onRoomLeave();
        final Room to = DB.db.rooms.get(IllusionGame.instance.currentRoom);
        DB.db.rooms.get(to.name).onRoomEnter(prevRoom);
        ((IllusionGame) IllusionGame.instance).changeIntoRoom(from, to);
        IllusionGame.instance.sheduleReset = true;
        return true;
    }

    @Override
    public boolean onGlobalMouseClick(double x, double y) {
        if (visible) {
            if (moving) {
                moving = false;
            } else {
                if ((x >= (this.x)) && (y >= (this.y)) && (x <= (this.x + WIDTH)) && (y <= (this.y + HEIGHT))) {
                    if (onMouseClick()) return true;
                }
            }
        }
        return false;
    }

    public static final int WIDTH = 294;
    public static final int HEIGHT = 200;
    public static final float SCALE_CONST = 200f / 768f;

    @Override
    public void render(Graphics g, float scale) {
        final IllusionRender render = (IllusionRender) IllusionGame.instance.render;
        final Shape clipDefault = g.getClip();
        render.strategies.stream()
                .filter(IllusionRender.RoomRender::isCorrect)
                .filter(o -> o.getRoom().name.equals(menuNameToRoomName()))
                .forEach(o -> {
                    final Room r = o.getRoom();
                    g.setClip(d2i(x), d2i(y), 294, 200);
                    g.translate(d2i(x), d2i(y));
                    r.render(g, SCALE_CONST);
                    render.objs.stream()
                            .filter(o1 -> r.objectsIDs.contains(o1.name))
                            .filter(o1 -> o1.visible)
                            .forEach(o1 -> {
                                try {
                                    o1.render(g, SCALE_CONST);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                    g.setClip(clipDefault);
                    g.translate(-d2i(x), -d2i(y));
                });
    }

    public static String menuNameToRoomName(String name) {
        switch (name) {
            case "menu_object_parabola":
                return "parabola_room";
            case "menu_object_ellipse":
                return "ellipse_room";
            case "menu_object_astroid":
                return "astroid_room";
            case "menu_object_deltoid":
                return "deltoid_room";
            default:
                return null;
        }
    }

    public static String roomNameToMenuName(String name) {
        switch (name) {
            case "parabola_room":
                return "menu_object_parabola";
            case "ellipse_room":
                return "menu_object_ellipse";
            case "astroid_room":
                return "menu_object_astroid";
            case "deltoid_room":
                return "menu_object_deltoid";
            default:
                return null;
        }
    }

    public String menuNameToRoomName() {
        return menuNameToRoomName(name);
    }

}
