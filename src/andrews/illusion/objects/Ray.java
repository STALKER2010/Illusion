package andrews.illusion.objects;

import java.awt.*;

/**
* @author STALKER_2010
*/
public class Ray {
    public Line f_part = new Line();
    public Line s_part = new Line();

    public void draw(final Graphics g) {
        f_part.draw(g);
        s_part.draw(g);
    }

}
