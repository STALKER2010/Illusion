package andrews.illusion.objects.parabola;

import andrews.illusion.IllusionGame;
import andrews.illusion.objects.Controller;
import andrews.illusion.objects.GradientHelper;
import andrews.illusion.objects.Ray;
import andrews.jengine.Animation;
import andrews.jengine.DB;
import andrews.jengine.GameObject;
import andrews.jengine.modules.Resources;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import static andrews.illusion.objects.Utils.d2i;

/**
 * @author STALKER_2010
 */
public class ParabolaMousePoint extends GameObject {

    public ParabolaMousePoint() {
        super();
    }

    public ParabolaMousePoint(String name) {
        super(name);
        final Animation a = Resources.animation(sprite);
        a.steps.clear();
        a.isLooped = false;
        a.isPlaying = false;
        visible = true;
        x = 400;
        y = 400;
        recalculate();
    }

    private Controller ctrl = null;

    @Override
    public void update() {
        super.update();
        visible = IllusionGame.instance.currentRoom.equals("parabola_room");
        if (moving) {
            PointerInfo a = MouseInfo.getPointerInfo();
            Point point = new Point(a.getLocation());
            SwingUtilities.convertPointFromScreen(point, IllusionGame.instance);
            this.x = point.getX();
            this.y = point.getY();
            recalculate();
        }
    }

    @Override
    public void onKey(int keycode) {
        super.onKey(keycode);
        recalculate();
    }

    private void recalculate() {
        if (ctrl == null) {
            ctrl = (Controller) DB.db.objects.get("controller");
        }
        rays.clear();
        int incMode = 2;
        if (ctrl.isLimited) incMode = incMode * 5;
        final int maxMode = (ctrl.isLimited) ? (incMode * 4) + 1 : 360;
        for (float i = 0; i < maxMode; i += incMode) {
            final Ray r = new Ray();

            final double angle = Math.toRadians(i);
            double endX = x + LINES_LENGTH * Math.sin(angle);
            double endY = y + LINES_LENGTH * Math.cos(angle);
            Pair<Double, Double> inter;
            inter = intersectWithLine(endX, endY);
            if (inter != null) {
                final int x2 = d2i(x);
                final int y2 = d2i(y);
                final int x1 = d2i(inter.getKey());
                final int y1 = d2i(inter.getValue());
                r.f_part.set(x1, y1, x2, y2);
                double dx = x1 - x2;
                double dy = y1 - y2;
                double dist = Math.sqrt(dx * dx + dy * dy);
                dx /= dist;
                dy /= dist;
                double x3 = x1 + (LINES_LENGTH / 2) * dy;
                double y3 = y1 - (LINES_LENGTH / 2) * dx;
                double x4 = x1 - (LINES_LENGTH / 2) * dy;
                double y4 = y1 + (LINES_LENGTH / 2) * dx;
                r.s_part.set(x3, y3, x4, y4);
            }
            rays.add(r);
        }
    }

    private final List<Ray> rays = new ArrayList<>();

    public boolean moving = false;

    @Override
    public void onMouseClick() {
        super.onMouseClick();
        moving = !moving;
    }

    @Override
    public void onGlobalMouseClick(double x, double y) {
        if (visible) {
            if (moving) {
                moving = false;
            } else {
                if ((x >= (this.x - (SIZE / 2))) && (y >= (this.y - (SIZE / 2))) && (x <= (this.x + (SIZE / 2))) && (y <= (this.y + (SIZE / 2)))) {
                    onMouseClick();
                }
            }
        }
    }

    @JsonIgnore
    public static final int SIZE = 16;
    @JsonIgnore
    public static final int LINES_LENGTH = 2048;

    @Override
    public void render(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillOval(Long.valueOf(Math.round(x)).intValue() - (SIZE / 2), Long.valueOf(Math.round(y)).intValue() - (SIZE / 2), SIZE, SIZE);
        g.setColor(GradientHelper.get());
        for (final Ray r : rays) {
            r.draw(g);
        }
        g.setColor(Color.lightGray);
    }

    private Pair<Double, Double> intersectWithLine(final double endX, final double endY) {
        if (Line2D.linesIntersect(x, y, endX, endY, 0, 500, 1094, 500)) {
            final Point r1 = new Point();
            final Point r2 = new Point();
            final Point l1 = new Point(0, 500);
            final Point l2 = new Point(1094, 500);
            {
                r1.setLocation(x, y);
                r2.setLocation(endX, endY);
            }

            double a1 = endY - y;
            if (endX - x != 0) {
                a1 = (endY - y) / (endX - x);
            }
            double b1 = y - a1 * x;
            double a2 = l2.getY() - l1.getY();
            if (l2.getX() - l1.getX() != 0) {
                a2 /= (l2.getX() - l1.getX());
            }
            double b2 = l1.getY() - a2 * l1.getX();
            double lx = -(b1 - b2);
            if ((a1 - a2) != 0) {
                lx /= (a1 - a2);
            }
            double ly = Math.max(l1.getY(), l2.getY());

            return new Pair<>(lx, ly);
        }
        return null;
    }
}
