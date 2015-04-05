package io.github.stalker2010.illusion.object.astroid;

import io.github.stalker2010.illusion.object.Controller;
import io.github.stalker2010.illusion.object.GradientHelper;
import io.github.stalker2010.jengine.DB;
import io.github.stalker2010.jengine.GameObject;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;

import static io.github.stalker2010.illusion.object.Utils.d2i;

/**
 * @author STALKER_2010
 */
public class Communicator extends GameObject {

    public Communicator() {
        super();
    }

    public Communicator(String name) {
        super(name);
        noSprite();
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
//        visible = !IllusionGame.instance.currentRoom.equals("reflection_room");
        current_pos++;
        if (current_pos >= lines.size()) {
            current_pos = 0;
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

    public static final int DIFF_AXIS = 300;

    public int current_pos = 0;

    private static final int BASE_X = 500;
    private static final int BASE_Y = 300;

    public java.util.List<Pair<Point, Point>> lines = new ArrayList<>();

    @Override
    public void render(Graphics g, float scale) {
        g.setColor(GradientHelper.get());
        if (ctrl == null) {
            ctrl = (Controller) DB.db.objects.get("controller");
        }
        int iStart = (Controller.isLimited) ? (current_pos - 40) : 0;
        for (int i = iStart; i <= current_pos; i++) {
            final Pair<Point, Point> cur = lines.get((i < 0) ? ((40 + i) + 360) : i);
            g.drawLine(d2i(cur.getKey().x * scale), d2i(cur.getKey().y * scale), d2i(cur.getValue().x * scale), d2i(cur.getValue().y * scale));
        }
        g.setColor(Color.lightGray);
    }
}
