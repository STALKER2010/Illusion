package io.github.stalker2010.illusion.background;

import io.github.stalker2010.jengine.Background;
import io.github.stalker2010.jengine.modules.Resources;

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
