package andrews.illusion.background;

import andrews.jengine.Background;
import andrews.jengine.modules.Resources;

/**
 * @author STALKER_2010
 */
public class GameBackground extends Background {
    public GameBackground() {
        super();
    }

    public GameBackground(String name) {
        super(name, Resources.sprite("stars.png"));
        visible = true;
    }
}
