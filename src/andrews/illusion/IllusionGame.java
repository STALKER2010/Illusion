package andrews.illusion;

import andrews.illusion.background.GameBackground;
import andrews.illusion.objects.Controller;
import andrews.illusion.objects.astroid.Axis;
import andrews.illusion.objects.astroid.Communicator;
import andrews.illusion.objects.reflection.MainPoint;
import andrews.illusion.objects.reflection.ReflLine;
import andrews.illusion.rooms.AstroidRoom;
import andrews.illusion.rooms.ReflectionRoom;
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
    public void init() {
        super.init();
        {
            final GameBackground b = new GameBackground("game_bg");
            DB.db.backgrounds.put(b.name, b);
        }
        {
            final Controller r = new Controller("controller");
            DB.db.objects.put(r.name, r);
        }
        {
            final MainPoint r = new MainPoint("main_point");
            DB.db.objects.put(r.name, r);
        }
        {
            final ReflLine r = new ReflLine("refl_line");
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
            final ReflectionRoom r = new ReflectionRoom("reflection_room");
            DB.db.rooms.put(r.name, r);
        }
        {
            final AstroidRoom r = new AstroidRoom("astroid_room");
            DB.db.rooms.put(r.name, r);
        }
        currentRoom = "reflection_room";

    }
}
