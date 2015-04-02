package io.github.stalker2010.illusion.object;

import java.awt.*;

/**
 * @author STALKER_2010
 */
public class Ray {
    public Line f_part = new Line();
    public Line s_part = new Line();

    public void draw(final Graphics g, float scale) {
        f_part.draw(g, scale);
        s_part.draw(g, scale);
    }

}
