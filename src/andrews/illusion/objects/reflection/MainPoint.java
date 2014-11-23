package andrews.illusion.objects.reflection;

import andrews.illusion.IllusionGame;
import andrews.illusion.objects.Controller;
import andrews.illusion.objects.GradientHelper;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author STALKER_2010
 */
public class MainPoint extends GameObject {

    public MainPoint() {
        super();
    }

    public MainPoint(String name) {
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
        visible = IllusionGame.instance.currentRoom.equals("reflection_room");
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
        int incMode = (ctrl.mode == Controller.MODE_PARABOLIC) ? 2 : 5;
        if (ctrl.isLimited) incMode = incMode * 5;
        final int maxMode = (ctrl.isLimited) ? (incMode * 4) + 1 : 360;
        for (float i = 0; i < maxMode; i += incMode) {
            final Ray r = new Ray();

            final double angle = Math.toRadians(i);
            double endX = x + LINES_LENGTH * Math.sin(angle);
            double endY = y + LINES_LENGTH * Math.cos(angle);
            Pair<Double, Double> inter = null;
            if (ctrl.mode == Controller.MODE_PARABOLIC) {
                inter = intersectWithLine(endX, endY);
            } else {
                final List<Pair<Double, Double>> interList = intersectWithCircle(endX, endY, new Point(400, 300), 200);
                if (interList.size() > 0) {
                    inter = interList.get(0);
                }
            }
            if (inter != null) {
                final int x2 = double2int(x);
                final int y2 = double2int(y);
                final int x1 = double2int(inter.getKey());
                final int y1 = double2int(inter.getValue());
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

    public class Ray {
        public Line f_part = new Line();
        public Line s_part = new Line();

        public void draw(final Graphics g) {
            f_part.draw(g);
            s_part.draw(g);
        }

        public class Line {
            public int x1 = 0;
            public int y1 = 0;
            public int x2 = 0;
            public int y2 = 0;

            public void draw(final Graphics g) {
                g.drawLine(x1, y1, x2, y2);
            }

            public Line set(final int x1, final int y1, final int x2, final int y2) {
                this.x1 = x1;
                this.y1 = y1;
                this.x2 = x2;
                this.y2 = y2;
                return this;
            }
            public Line set(final double x1, final double y1, final double x2, final double y2) {
                this.x1 = double2int(x1);
                this.y1 = double2int(y1);
                this.x2 = double2int(x2);
                this.y2 = double2int(y2);
                return this;
            }
        }
    }

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

    public List<Pair<Double, Double>> intersectWithCircle(double endX,
                                                          double endY, Point center, double radius) {
        double baX = endX - x;
        double baY = endY - y;
        double caX = center.x - x;
        double caY = center.y - y;

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
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        Pair<Double, Double> p1 = new Pair<>(x - baX * abScalingFactor1, y
                - baY * abScalingFactor1);
        if (disc == 0) {
            return Collections.singletonList(p1);
        }
        Pair<Double, Double> p2 = new Pair<>(x - baX * abScalingFactor2, y
                - baY * abScalingFactor2);
        return Arrays.asList(p1, p2);
    }

    public static int double2int(final double d) {
        return Long.valueOf(Math.round(d)).intValue();
    }
}
