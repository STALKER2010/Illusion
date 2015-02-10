package andrews.illusion;

import andrews.illusion.background.GameBackground;
import andrews.illusion.objects.Controller;
import andrews.illusion.objects.astroid.Axis;
import andrews.illusion.objects.astroid.Communicator;
import andrews.illusion.objects.deltoid.DeltoidBase;
import andrews.illusion.objects.deltoid.DeltoidMousePoint;
import andrews.illusion.objects.ellipse.EllipseBase;
import andrews.illusion.objects.ellipse.EllipseMousePoint;
import andrews.illusion.objects.parabola.ParabolaBase;
import andrews.illusion.objects.parabola.ParabolaMousePoint;
import andrews.illusion.rooms.AstroidRoom;
import andrews.illusion.rooms.DeltoidRoom;
import andrews.illusion.rooms.EllipseRoom;
import andrews.illusion.rooms.ParabolaRoom;
import andrews.jengine.DB;
import andrews.jengine.Game;

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
        {
            final GameBackground b = new GameBackground("game_bg");
            DB.db.backgrounds.put(b.name, b);
        }
        {
            final Controller r = new Controller("controller");
            DB.db.objects.put(r.name, r);
        }
        {
            final ParabolaMousePoint r = new ParabolaMousePoint("parabola_mouse_point");
            DB.db.objects.put(r.name, r);
        }
        {
            final ParabolaBase r = new ParabolaBase("parabola_base");
            DB.db.objects.put(r.name, r);
        }
        {
            final EllipseMousePoint r = new EllipseMousePoint("ellipse_mouse_point");
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
            final DeltoidMousePoint r = new DeltoidMousePoint("deltoid_mouse_point");
            DB.db.objects.put(r.name, r);
        }
        {
            final DeltoidBase r = new DeltoidBase("deltoid_base");
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
        currentRoom = "parabola_room";
        return false;
    }
}
