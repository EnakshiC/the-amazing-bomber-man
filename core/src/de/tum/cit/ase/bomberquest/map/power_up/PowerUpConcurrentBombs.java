package de.tum.cit.ase.bomberquest.map.power_up;

import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

/**
 * PowerUpConcurrentBombs is a specific type of PowerUp that increases the maximum
 * number of bombs a player can place concurrently when collected up to a maximum.
 */
public class PowerUpConcurrentBombs extends PowerUp {

    private static final int MAX_POSSIBLE = 8;

    /**
     * Constructs a PowerUpConcurrentBombs object, a specific type of PowerUp that increases
     * the maximum number of bombs a player can place concurrently when collected.
     *
     * @param world The physics world where the PowerUp exists, enabling physical interactions.
     * @param x The X-coordinate (in tiles) of the PowerUp's position.
     * @param y The Y-coordinate (in tiles) of the PowerUp's position.
     * @param objectsToBeRemovedNextCycle A list of objects scheduled for removal in the next game cycle.
     */
    public PowerUpConcurrentBombs(World world, float x, float y, List<Drawable> objectsToBeRemovedNextCycle) {
        super(world, x, y, new ConcurrentBombsEffect(), Textures.POWER_UP_CONCURRENT_BOMBS, objectsToBeRemovedNextCycle);
    }

    /**
     * The effect of this power-up: increases the maximum number of bombs a player can place concurrently.
     */
    private static class ConcurrentBombsEffect implements PowerUpEffect {
        @Override
        public void apply(GameMap gameMap) {
            int currentMaxBombs = gameMap.getMaxBombsAllowed();
            if (currentMaxBombs < MAX_POSSIBLE) {
                gameMap.setMaxBombsAllowed(currentMaxBombs + 1);
            }
        }
    }
}