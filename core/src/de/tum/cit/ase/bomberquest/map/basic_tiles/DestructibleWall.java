package de.tum.cit.ase.bomberquest.map.basic_tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Destroyable;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

/**
 * Represents a destructible wall within the game, which extends the functionality
 * of a {@link Wall} and implements the {@link Destroyable} interface.
 * This type of wall can be destroyed, changing its state and appearance,
 * and marking it for removal.
 */

public class DestructibleWall extends Wall implements Destroyable {

    /**
     * A list of {@link Drawable} objects to be removed in the next game cycle.
     */
    private final List<Drawable> objectsToBeRemovedNextCycle;

    /**
     * Constructs a new {@code DestructibleWall}.
     *
     * @param world the Box2D {@link World} instance where the wall resides
     * @param x the x-coordinate of the wall
     * @param y the y-coordinate of the wall
     * @param objectsToBeRemovedNextCycle a list to track objects scheduled for removal
     */
    public DestructibleWall(World world, float x, float y, List<Drawable> objectsToBeRemovedNextCycle) {
        super(world, x, y, true);
        this.objectsToBeRemovedNextCycle = objectsToBeRemovedNextCycle;
    }

    /**
     * Retrieves the current appearance of the wall.
     *
     * @return a {@link TextureRegion} to provide the appearance of a destructible wall.
     * If the wall is solid, it returns the destructible wall texture;
     * otherwise, it returns an empty texture.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        return this.solid ? Textures.DESTRUCTIBLE_WALL : Textures.EMPTY;
    }

    /**
     * Destroys wall by setting isSolid to false and removing the body
     */
    public void destroy() {
        setSolid(false);
        this.objectsToBeRemovedNextCycle.add(this);
    }

    /**
     * Removes physical body of the wall from the Box2D @param world
     */
    @Override
    public void destroyBody(World world) {
        if (this.body != null) {
            world.destroyBody(body);
        }
    }
}