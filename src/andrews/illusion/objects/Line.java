package andrews.illusion.objects;

import static andrews.illusion.objects.Utils.*;

import java.awt.*;

/**
 * @author STALKER_2010
 */
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
