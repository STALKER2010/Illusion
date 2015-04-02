package io.github.stalker2010.illusion.room;

import io.github.stalker2010.illusion.object.*;
import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.github.stalker2010.illusion.object.Utils.d2i;

/**
 * @author STALKER_2010
 */
public class ParabolaRoom extends IllusionRoom {
    public ParabolaRoom(String name) {
        super(name);
        objectsIDs.add("parabola_base");
        objectsIDs.add("mouse_point");
    }

    @Override
    public void update() {
        super.update();
    }

    private final java.util.List<Ray> rays = new CopyOnWriteArrayList<>();
    public static final int LINES_LENGTH = 2048;

    @Override
    public void recalculate() {
        rays.clear();
        int incMode = 2;
        if (Controller.isLimited) incMode = incMode * 5;
        final int maxMode = (Controller.isLimited) ? (incMode * 4) + 1 : 360;
        for (float i = 0; i < maxMode; i += incMode) {
            final Ray r = new Ray();

            final double angle = Math.toRadians(i);
            final MousePoint mousePoint = getPoint();
            double endX = mousePoint.x + LINES_LENGTH * Math.sin(angle);
            double endY = mousePoint.y + LINES_LENGTH * Math.cos(angle);
            Pair<Double, Double> inter;
            inter = intersectWithLine(endX, endY);
            if (inter != null) {
                final int x2 = d2i(mousePoint.x);
                final int y2 = d2i(mousePoint.y);
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

    private Pair<Double, Double> intersectWithLine(final double endX, final double endY) {
        final MousePoint point = getPoint();
        if (Line2D.linesIntersect(point.x, point.y, endX, endY, 0, 500, 1094, 500)) {
            final Point r1 = new Point();
            final Point r2 = new Point();
            final Point l1 = new Point(0, 500);
            final Point l2 = new Point(1094, 500);
            {
                r1.setLocation(point.x, point.y);
                r2.setLocation(endX, endY);
            }

            double a1 = endY - point.y;
            if (endX - point.x != 0) {
                a1 = (endY - point.y) / (endX - point.x);
            }
            double b1 = point.y - a1 * point.x;
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

    @Override
    public void render(Graphics g, float scale) {
        super.render(g, scale);
        g.setColor(GradientHelper.get());
        for (final Ray r : rays) {
            r.draw(g, scale);
        }
        g.setColor(Color.lightGray);
    }
}
