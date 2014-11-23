package andrews.illusion.objects.astroid;

import andrews.illusion.IllusionGame;
import andrews.illusion.objects.Controller;
import andrews.illusion.objects.GradientHelper;
import andrews.jengine.Animation;
import andrews.jengine.DB;
import andrews.jengine.GameObject;
import andrews.jengine.modules.Resources;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author STALKER_2010
 */
public class Communicator extends GameObject {

    public Communicator() {
        super();
    }

    public Communicator(String name) {
        super(name);
        final Animation a = Resources.animation(sprite);
        a.steps.clear();
        a.isLooped = false;
        a.isPlaying = false;
        visible = true;
        for (int i = 0; i < DIFF_AXIS; i += 3) {
            lines.add(new Pair<>(new Point(BASE_X - (i), BASE_Y), new Point(BASE_X, BASE_Y - (DIFF_AXIS - i))));
        }
        for (int i = DIFF_AXIS - 1; i >= 0; i -= 3) {
            lines.add(new Pair<>(new Point(BASE_X - (i), BASE_Y), new Point(BASE_X, BASE_Y + (DIFF_AXIS - i))));
        }
        for (int i = 0; i < DIFF_AXIS; i += 3) {
            lines.add(new Pair<>(new Point(BASE_X + (i), BASE_Y), new Point(BASE_X, BASE_Y + (DIFF_AXIS - i))));
        }
        for (int i = DIFF_AXIS - 1; i >= 0; i -= 3) {
            lines.add(new Pair<>(new Point(BASE_X + (i), BASE_Y), new Point(BASE_X, BASE_Y - (DIFF_AXIS - i))));
        }
    }

    private Controller ctrl = null;

    @Override
    public void update() {
        super.update();
        ctrl = (Controller) DB.db.objects.get("controller");
        visible = !IllusionGame.instance.currentRoom.equals("reflection_room");
        current_pos++;
        if (current_pos >= lines.size()) {
            current_pos = 0;
        }
    }


    @Override
    public void onMouseClick() {
    }

    @Override
    public void onGlobalMouseClick(double x, double y) {
    }

    @JsonIgnore
    public static final int DIFF_AXIS = 300;

    public int current_pos = 0;

    private static final int BASE_X = 500;
    private static final int BASE_Y = 300;

    public java.util.List<Pair<Point, Point>> lines = new ArrayList<>();

    @Override
    public void render(Graphics g) {
        g.setColor(GradientHelper.get());
        if (ctrl == null) {
            ctrl = (Controller) DB.db.objects.get("controller");
        }
        int iStart = (ctrl.isLimited) ? (current_pos - 40) : 0;
        for (int i = iStart; i <= current_pos; i++) {
            final Pair<Point, Point> cur = lines.get((i < 0) ? ((40 + i) + 360) : i);
            g.drawLine(cur.getKey().x, cur.getKey().y, cur.getValue().x, cur.getValue().y);
        }
        g.setColor(Color.lightGray);
    }
}
