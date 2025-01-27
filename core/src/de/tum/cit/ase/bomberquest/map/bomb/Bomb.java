package de.tum.cit.ase.bomberquest.map.bomb;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

/**
 * Represents a bomb that can be placed in the game world.
 * The bomb is a self-removing element that explodes after a defined fuse time
 * and is subsequently scheduled for removal from the game.
 */
public class Bomb extends SelfRemovingElement {

    /** The time it takes for the bomb to explode after it has been placed. */
    public static final float FUSE_TIME = 3.0f;


    private final float x;
    private final float y;

    public Bomb(float x, float y, List<Drawable> objectsToBeRemovedNextCycle) {
        super(FUSE_TIME, objectsToBeRemovedNextCycle);
        this.x = x;
        this.y = y;
    }

    @Override
    public void tick(float frameTime) {
        super.tick(frameTime);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.BOMB;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }
}
