package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Drawable;

// TODO: Implement Bomb
public class Bomb implements Drawable {

    /** The time it takes for the bomb to explode after it has been placed. */
    public static final float FUSE_TIME = 3.0f;

    @Override
    public TextureRegion getCurrentAppearance() {
        return null;
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }
}
