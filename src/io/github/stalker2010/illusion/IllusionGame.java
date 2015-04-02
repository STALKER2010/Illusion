package io.github.stalker2010.illusion;

import io.github.stalker2010.illusion.background.GameBackground;
import io.github.stalker2010.illusion.object.Controller;
import io.github.stalker2010.illusion.object.MenuItemObject;
import io.github.stalker2010.illusion.object.MousePoint;
import io.github.stalker2010.illusion.object.astroid.Axis;
import io.github.stalker2010.illusion.object.astroid.Communicator;
import io.github.stalker2010.illusion.object.deltoid.DeltoidBase;
import io.github.stalker2010.illusion.object.ellipse.EllipseBase;
import io.github.stalker2010.illusion.object.parabola.ParabolaBase;
import io.github.stalker2010.illusion.room.AstroidRoom;
import io.github.stalker2010.illusion.room.DeltoidRoom;
import io.github.stalker2010.illusion.room.EllipseRoom;
import io.github.stalker2010.illusion.room.ParabolaRoom;
import io.github.stalker2010.jengine.DB;
import io.github.stalker2010.jengine.Game;
import io.github.stalker2010.jengine.Room;

import java.awt.*;

/**
 * @author STALKER_2010
 */
public class IllusionGame extends Game {
    @Override
    public void update() {
        super.update();
    }

    @Override
    public boolean init() {
        if (super.init()) return true;

        currentRoom = "parabola_room";

        {
            final GameBackground b = new GameBackground("game_bg");
            DB.db.backgrounds.put(b.name, b);
        }
        {
            final Controller r = new Controller("controller");
            DB.db.objects.put(r.name, r);
        }
        {
            final ParabolaBase r = new ParabolaBase("parabola_base");
            DB.db.objects.put(r.name, r);
        }
        {
            final EllipseBase r = new EllipseBase("ellipse_base");
            DB.db.objects.put(r.name, r);
        }
        {
            final Axis r = new Axis("axis");
            DB.db.objects.put(r.name, r);
        }
        {
            final Communicator r = new Communicator("communicator");
            DB.db.objects.put(r.name, r);
        }
        {
            final DeltoidBase r = new DeltoidBase("deltoid_base");
            DB.db.objects.put(r.name, r);
        }
        {
            final MenuItemObject r = new MenuItemObject("menu_object_parabola");
            r.x = 900;
            r.y = 0;
            DB.db.objects.put(r.name, r);
        }
        {
            final MenuItemObject r = new MenuItemObject("menu_object_ellipse");
            r.x = 900;
            r.y = 200;
            DB.db.objects.put(r.name, r);
        }
        {
            final MenuItemObject r = new MenuItemObject("menu_object_astroid");
            r.x = 900;
            r.y = 400;
            DB.db.objects.put(r.name, r);
        }
        {
            final MenuItemObject r = new MenuItemObject("menu_object_deltoid");
            r.x = 900;
            r.y = 600;
            DB.db.objects.put(r.name, r);
        }
        {
            final ParabolaRoom r = new ParabolaRoom("parabola_room");
            DB.db.rooms.put(r.name, r);
        }
        {
            final EllipseRoom r = new EllipseRoom("ellipse_room");
            DB.db.rooms.put(r.name, r);
        }
        {
            final AstroidRoom r = new AstroidRoom("astroid_room");
            DB.db.rooms.put(r.name, r);
        }
        {
            final DeltoidRoom r = new DeltoidRoom("deltoid_room");
            DB.db.rooms.put(r.name, r);
        }
        {
            final MousePoint r = new MousePoint("mouse_point");
            DB.db.objects.put(r.name, r);
        }
        DB.db.rooms.get(currentRoom).onRoomEnter("");
        changeIntoRoom(null, DB.db.rooms.get(currentRoom));
        render = new IllusionRender(this);
        return false;
    }

    public void changeIntoRoom(Room from, Room to) {
        final MenuItemObject menu_parabola = (MenuItemObject) DB.db.objects.get("menu_object_parabola");
        final MenuItemObject menu_ellipse = (MenuItemObject) DB.db.objects.get("menu_object_ellipse");
        final MenuItemObject menu_astroid = (MenuItemObject) DB.db.objects.get("menu_object_astroid");
        final MenuItemObject menu_deltoid = (MenuItemObject) DB.db.objects.get("menu_object_deltoid");
        final MenuItemObject menu_prev = (from != null) ? (MenuItemObject) DB.db.objects.get(MenuItemObject.roomNameToMenuName(from.name)) : null;
        switch (to.name) {
            case "parabola_room": {
                moveToDefaultPlace(menu_prev);
                menu_parabola.y = 10000;
                menu_deltoid.y = 0;
                break;
            }
            case "ellipse_room": {
                moveToDefaultPlace(menu_prev);
                menu_ellipse.y = 10000;
                menu_deltoid.y = 200;
                break;
            }
            case "astroid_room": {
                moveToDefaultPlace(menu_prev);
                menu_astroid.y = 10000;
                menu_deltoid.y = 400;
                break;
            }
            case "deltoid_room": {
                moveToDefaultPlace(menu_prev);
                menu_deltoid.y = 10000;
                break;
            }
        }
    }

    public boolean moveToDefaultPlace(final MenuItemObject item) {
        if (item == null) return false;
        switch (item.name) {
            case "menu_object_parabola": {
                item.y = 0;
                return true;
            }
            case "menu_object_ellipse": {
                item.y = 200;
                return true;
            }
            case "menu_object_astroid": {
                item.y = 400;
                return true;
            }
            case "menu_object_deltoid": {
                return false;
            }
        }
        return false;
    }

    public void setPointsPosition(final Point p) {
        final ParabolaRoom mp_parabola = (ParabolaRoom) DB.db.rooms.get("parabola_room");
        final EllipseRoom mp_ellipse = (EllipseRoom) DB.db.rooms.get("ellipse_room");
        final AstroidRoom mp_astroid = (AstroidRoom) DB.db.rooms.get("astroid_room");
        final DeltoidRoom mp_deltoid = (DeltoidRoom) DB.db.rooms.get("deltoid_room");
        mp_parabola.recalculate();
        mp_ellipse.recalculate();
        mp_astroid.recalculate();
        mp_deltoid.recalculate();
    }
}
