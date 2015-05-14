package io.github.stalker2010.illusion.room;

import io.github.stalker2010.illusion.object.*;
import io.github.stalker2010.illusion.object.deltoid.DeltoidBase;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static io.github.stalker2010.illusion.object.Utils.pow2;

/**
 * @author STALKER_2010
 */
public class DeltoidRoom extends IllusionRoom {
    public DeltoidRoom(String name) {
        super(name);
        objectsIDs.add("deltoid_base");
        objectsIDs.add("mouse_point");
    }

    private double angleInDegrees = 0;
    public List<DPoint> circlePoints = new ArrayList<>();
    private final List<Ray> rays = new CopyOnWriteArrayList<>();

    @Override
    public void recalculate() {
        if (circlePoints.isEmpty()) {
            final float mod = (Controller.isLimited) ? 60f : 1f;
            for (float i = 0; i < 360; i += mod) {
                final double angle = Math.toRadians(i);
                final double px = 400 + (200 * Math.cos(angle));
                final double py = 300 + (200 * Math.sin(angle));
                circlePoints.add(new DPoint(px, py));
            }
        }
        {
            final MousePoint mp = getPoint();
            double deltaY = mp.y - 265;
            double deltaX = mp.x - 400;
            double newAngleInDegrees = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
            if (newAngleInDegrees != angleInDegrees) {
                angleInDegrees = newAngleInDegrees;
                DeltoidBase.rotateTriangle(angleInDegrees);
            }
        }
        rays.clear();
        for (final DPoint cPoint : circlePoints) {
            final Ray r = new Ray();

            {
                final DPoint inter = intersectWithLine(cPoint, DeltoidBase.extendedSide1);
                final double x1 = inter.x;
                final double y1 = inter.y;
                final double x2 = cPoint.x;
                final double y2 = cPoint.y;
                r.f_part.set(x1, y1, x2, y2);
            }
            {
                final DPoint inter = intersectWithLine(cPoint, DeltoidBase.extendedSide2);
                final double x1 = inter.x;
                final double y1 = inter.y;
                final double x2 = cPoint.x;
                final double y2 = cPoint.y;
                r.s_part.set(x1, y1, x2, y2);
            }
            {
                final DPoint inter = intersectWithLine(cPoint, DeltoidBase.extendedSide3);
                final double x1 = inter.x;
                final double y1 = inter.y;
                final double x2 = cPoint.x;
                final double y2 = cPoint.y;
                r.t_part.set(x1, y1, x2, y2);
            }
            {
                double x1 = r.f_part.x1,
                        y1 = r.f_part.y1,
                        x2 = r.t_part.x1,
                        y2 = r.t_part.y1;
                double lengthAB = Math.sqrt(pow2(x1 - x2) + pow2(y1 - y2));
                if (lengthAB > 1) {
                    double x3 = x2 + (x2 - x1) / lengthAB * 1000;
                    double y3 = y2 + (y2 - y1) / lengthAB * 1000;
                    double x4 = x1 - (x2 - x1) / lengthAB * 1000;
                    double y4 = y1 - (y2 - y1) / lengthAB * 1000;
                    r.res.set(x3, y3, x4, y4);
                    rays.add(r);
                }
            }
        }
    }

    /**
     * @author STALKER_2010
     */
    public class Ray {
        public Line f_part = new Line();
        public Line s_part = new Line();
        public Line t_part = new Line();
        public Line res = new Line();

        public void draw(final Graphics g, final float scale) {
            if (Controller.isLimited) {
                f_part.draw(g, scale);
                s_part.draw(g, scale);
                t_part.draw(g, scale);
            }
            res.draw(g, scale);
        }

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

    public DPoint intersectWithLine(final DPoint r1, final Line l) {
        double k1 = ((l.y2 - l.y1) * (r1.x - l.x1)) - ((l.x2 - l.x1) * (r1.y - l.y1));
        double k2 = (pow2(l.y2 - l.y1) + pow2(l.x2 - l.x1));
        double k = k1;
        if (k2 != 0) {
            k /= k2;
        } else {
            return new DPoint(0, 0);
        }
        double x4 = r1.x - k * (l.y2 - l.y1);
        double y4 = r1.y + k * (l.x2 - l.x1);
        return new DPoint(x4, y4);
    }
}
