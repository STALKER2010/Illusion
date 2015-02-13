package andrews.illusion.objects.ellipse;

import andrews.illusion.IllusionGame;
import andrews.illusion.objects.Controller;
import andrews.illusion.objects.DPoint;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static andrews.illusion.objects.Utils.d2i;

/**
 * @author STALKER_2010
 */
public class EllipseMousePoint extends GameObject {

    public EllipseMousePoint() {
        super();
    }

    public EllipseMousePoint(String name) {
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
        visible = IllusionGame.instance.currentRoom.equals("ellipse_room");
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

    private static final DPoint CIRCLE_CENTER = new DPoint(400d, 300d);

    private void recalculate() {
        if (ctrl == null) {
            ctrl = (Controller) DB.db.objects.get("controller");
        }
        rays.clear();
        int incMode = 5;
        if (ctrl.isLimited) incMode = incMode * 5;
        final int maxMode = (ctrl.isLimited) ? (incMode * 4) + 1 : 360;
        for (float i = 0; i < maxMode; i += incMode) {
            final Ray r = new Ray();

            final double angle = Math.toRadians(i);
            double endX = x + LINES_LENGTH * Math.sin(angle);
            double endY = y + LINES_LENGTH * Math.cos(angle);
            Pair<Double, Double> inter;
            final List<Pair<Double, Double>> interList = intersectWithCircle(
                    new DPoint(x, y),
                    new DPoint(endX, endY),
                    CIRCLE_CENTER, 200);
            if (!interList.isEmpty()) {
                inter = interList.get(0);
                final double x2 = x;
                final double y2 = y;
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
        g.fillOval(d2i(x) - (SIZE / 2), d2i(y) - (SIZE / 2), SIZE, SIZE);
        g.setColor(GradientHelper.get());
        for (final Ray r : rays) {
            r.draw(g);
        }
        g.setColor(Color.lightGray);
    }

    public static List<Pair<Double, Double>> intersectWithCircle(DPoint start, DPoint end, DPoint center, double radius) {
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
