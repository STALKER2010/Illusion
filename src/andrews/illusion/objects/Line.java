package andrews.illusion.objects;

import static andrews.illusion.objects.Utils.*;

import java.awt.*;

/**
 * @author STALKER_2010
 */
public class Line {
    public double x1 = 0;
    public double y1 = 0;
    public double x2 = 0;
    public double y2 = 0;
    private int _x1 = 0;
    private int _y1 = 0;
    private int _x2 = 0;
    private int _y2 = 0;

    public void draw(final Graphics g) {
        g.drawLine(_x1, _y1, _x2, _y2);
    }

    public Line set(final int x1, final int y1, final int x2, final int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this._x1 = x1;
        this._y1 = y1;
        this._x2 = x2;
        this._y2 = y2;
        return this;
    }

    public Line set(final double x1, final double y1, final double x2, final double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        update();
        return this;
    }

    public Line update() {
        _x1 = d2i(x1);
        _y1 = d2i(y1);
        _x2 = d2i(x2);
        _y2 = d2i(y2);
        return this;
    }
}
