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

    /**
     * Stores x-coordinate of bomb's position in the game
     */
    private final float x;
    /**
     * Stores y-coordinate of bomb's position in the game
     */
    private final float y;

    /**
     * Constructs a new {@code Bomb}.
     *
     * @param x the x-coordinate of the bomb's initial position
     * @param y the y-coordinate of the bomb's initial position
     * @param objectsToBeRemovedNextCycle a list to track objects scheduled for removal
     */
    public Bomb(float x, float y, List<Drawable> objectsToBeRemovedNextCycle) {
        super(FUSE_TIME, objectsToBeRemovedNextCycle);
        this.x = x;
        this.y = y;
    }

    /**
     * Updates state of bomb by reducing its remaining time
     * & triggers its removal once fuse time elapses
     * @param frameTime the time elapsed since last update
     */
    @Override
    public void tick(float frameTime) {
        super.tick(frameTime);
    }

    /**
     * Retrieves current appearance of the bomb
     * @return
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.BOMB;
    }

    /**
     * Retrieves x-coordinate of the bomb's position
     * @return
     */
    @Override
    public float getX() {
        return this.x;
    }

    /**
     * Retrieves y-coordinate of the bomb's position
     * @return
     */
    @Override
    public float getY() {
        return this.y;
    }
}
