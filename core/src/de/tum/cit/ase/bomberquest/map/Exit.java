package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

/**
 * Represents the exit point in a game map, which can open or close based on the state of the map.
 * The exit is considered open when there are no enemies left on the game map,
 * and closed when enemies are still present.
 */
public class Exit implements Drawable {

    private final int x;
    private final int y;
    private final GameMap gameMap;

    /**
     * Constructs an {@code Exit} object at a specified position on the game map.
     *
     * @param world   The physics world where the exit's hitbox is registered.
     * @param x       The x-coordinate of the exit's position in the game map.
     * @param y       The y-coordinate of the exit's position in the game map.
     * @param gameMap The game map associated with this exit, used to check its state.
     */
    public Exit(World world, int x, int y, GameMap gameMap) {
        this.x = x;
        this.y = y;
        this.gameMap=gameMap;

        HitboxHelper.createCircleHitbox(world, x, y, this, true);
    }

    /** The exit is open if there are no more enemies alive in the map. */
    public boolean isOpen() {
        return gameMap.getEnemies().isEmpty();
    }

    /**
     * Retrieves the current appearance of the exit based on its state.
     *
     * @return The {@link TextureRegion} for the open exit if {@code isOpen()} is {@code true},
     *         otherwise the {@link TextureRegion} for the closed exit.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        if(isOpen()) {
            return Textures.EXIT_OPEN;
        }
        else {
            return Textures.EXIT_CLOSE;
        }
    }

    /**
    * Retrieves the x-coordinate of the exit's position.
    */
    @Override
    public float getX() {
        return x;
    }

    /**
    * Retrieves the y-coordinate of the exit's position.
    */
    @Override
    public float getY() {
        return y;
    }


    /**
    * Returns a string representation of the exit's position.
    */
    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }
}