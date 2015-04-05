package io.github.stalker2010.illusion.room;

import io.github.stalker2010.illusion.object.*;
import javafx.util.Pair;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author STALKER_2010
 */
public class EllipseRoom extends IllusionRoom {
    public EllipseRoom(String name) {
        super(name);
        objectsIDs.add("ellipse_base");
        objectsIDs.add("mouse_point");
    }

    @Override
    public void update() {
        super.update();
    }

    private static final DPoint CIRCLE_CENTER = new DPoint(400d, 300d);

    @Override
    public void recalculate() {
        rays.clear();
        int incMode = 5;
        if (Controller.isLimited) incMode = incMode * 5;
        final int maxMode = (Controller.isLimited) ? (incMode * 4) + 1 : 360;
        for (float i = 0; i < maxMode; i += incMode) {
            final Ray r = new Ray();

            final double angle = Math.toRadians(i);
            final MousePoint point = getPoint();
            double endX = point.x + LINES_LENGTH * Math.sin(angle);
            double endY = point.y + LINES_LENGTH * Math.cos(angle);
            Pair<Double, Double> inter;
            final java.util.List<Pair<Double, Double>> interList = intersectWithCircle(
                    new DPoint(point.x, point.y),
                    new DPoint(endX, endY),
                    CIRCLE_CENTER, 200);
            if (!interList.isEmpty()) {
                inter = interList.get(0);
                final double x2 = point.x;
                final double y2 = point.y;
                final double x1 = inter.getKey();
                final double y1 = inter.getValue();
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

    public static final int LINES_LENGTH = 2048;
    private final java.util.List<Ray> rays = new CopyOnWriteArrayList<>();

    @Override
    public void render(Graphics g, float scale) {
        super.render(g, scale);
        g.setColor(GradientHelper.get());
        for (final Ray r : rays) {
            r.draw(g, scale);
        }
        g.setColor(Color.lightGray);
    }

    public static java.util.List<Pair<Double, Double>> intersectWithCircle(DPoint start, DPoint end, DPoint center, double radius) {
        double baX = end.getX() - start.getX();
        double baY = end.getY() - start.getY();
        double caX = center.x - start.getX();
        double caY = center.y - start.getY();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;

        Pair<Double, Double> p1 = new Pair<>(start.getX() - baX * abScalingFactor1, start.getY()
                - baY * abScalingFactor1);
        if (disc == 0) {
            return Collections.singletonList(p1);
        }
        double abScalingFactor2 = -pBy2 - tmpSqrt;
        Pair<Double, Double> p2 = new Pair<>(start.getX() - baX * abScalingFactor2, start.getY()
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }
}
