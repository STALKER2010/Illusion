package andrews.illusion.objects.deltoid;

import andrews.illusion.IllusionGame;
import andrews.illusion.objects.Controller;
import andrews.illusion.objects.DPoint;
import andrews.illusion.objects.GradientHelper;
import andrews.illusion.objects.Line;
import andrews.jengine.Animation;
import andrews.jengine.DB;
import andrews.jengine.GameObject;
import andrews.jengine.modules.Resources;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static andrews.illusion.objects.Utils.d2i;
import static andrews.illusion.objects.Utils.pow2;

/**
 * @author STALKER_2010
 */
public class DeltoidMousePoint extends GameObject {
    public DeltoidMousePoint() {
        super();
    }

    public DeltoidMousePoint(String name) {
        super(name);
        final Animation a = Resources.animation(sprite);
        a.steps.clear();
        a.isLooped = false;
        a.isPlaying = false;
        visible = true;
        x = 400;
        y = 265;
        for (int i = 0; i < 360; i++) {
            final double angle = Math.toRadians(i);
            final double px = 400 + (200 * Math.cos(angle));
            final double py = 300 + (200 * Math.sin(angle));
            circlePoints.add(new DPoint(px, py));
        }
        recalculate();
    }

    private Controller ctrl = null;
    private double angleInDegrees = 0;
    private boolean skipStep = true;

    @Override
    public void update() {
        super.update();
        visible = IllusionGame.instance.currentRoom.equals("deltoid_room");
        if (moving) {
            PointerInfo a = MouseInfo.getPointerInfo();
            Point point = new Point(a.getLocation());
            SwingUtilities.convertPointFromScreen(point, IllusionGame.instance);
            this.x = point.getX();
            this.y = point.getY();
            double deltaY = y - 265;
            double deltaX = x - 400;
            double newAngleInDegrees = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
            if (newAngleInDegrees != angleInDegrees) {
                angleInDegrees = newAngleInDegrees;
                if (skipStep) {
                    DeltoidBase.rotateTriangle(angleInDegrees);
                    recalculate();
                }
                skipStep = !skipStep;
            }
        }
    }

    @Override
    public void onKey(int keycode) {
        super.onKey(keycode);
        recalculate();
    }

    List<DPoint> circlePoints = new ArrayList<>();

    private void recalculate() {
        if (ctrl == null) {
            ctrl = (Controller) DB.db.objects.get("controller");
        }
        rays.clear();
        for (final DPoint cPoint : circlePoints) {
            final Ray r = new Ray();

            for (int i = 0; i < 360; i++) {
                DPoint inter = intersectWithLine(cPoint, DeltoidBase.extendedSide1);
                if (inter == null) {
                    continue;
                }
                final double x2 = cPoint.x;
                final double y2 = cPoint.y;
                final double x1 = inter.x;
                final double y1 = inter.y;
                r.f_part.set(x1, y1, x2, y2);
            }
            for (int i = 0; i < 360; i++) {
                DPoint inter = intersectWithLine(cPoint, DeltoidBase.extendedSide2);
                if (inter == null) {
                    continue;
                }
                final double x2 = cPoint.x;
                final double y2 = cPoint.y;
                final double x1 = inter.x;
                final double y1 = inter.y;
                r.s_part.set(x1, y1, x2, y2);
            }
            for (int i = 0; i < 360; i++) {
                DPoint inter = intersectWithLine(cPoint, DeltoidBase.extendedSide3);
                if (inter == null) {
                    continue;
                }
                final double x2 = cPoint.x;
                final double y2 = cPoint.y;
                final double x1 = inter.x;
                final double y1 = inter.y;
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

    /**
     * @author STALKER_2010
     */
    public class Ray {
        public Line f_part = new Line();
        public Line s_part = new Line();
        public Line t_part = new Line();
        public Line res = new Line();

        public void draw(final Graphics g) {
//            f_part.draw(g);
//            s_part.draw(g);
//            t_part.draw(g);
            res.draw(g);
        }

    }

    @JsonIgnore
    public static final int SIZE = 16;
    @JsonIgnore
    public static final int SIZE_HALF = 8;

    @Override
    public void render(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillOval(d2i(x) - SIZE_HALF, d2i(y) - SIZE_HALF, SIZE, SIZE);
        g.setColor(GradientHelper.get());
        for (final Ray r : rays) {
            r.draw(g);
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
            return null;
        }
        double x4 = r1.x - k * (l.y2 - l.y1);
        double y4 = r1.y + k * (l.x2 - l.x1);
        return new DPoint(x4, y4);
    }
}
