package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class Exit implements Drawable {

    private final int x;
    private final int y;
    boolean Open = false;

    public Exit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public TextureRegion getCurrentAppearance() {

        return Textures.EXIT_CLOSE;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }
}