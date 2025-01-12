package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class EmptyTile implements Drawable {

    private final float x;
    private final float y;

    public EmptyTile(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.EMPTY;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}