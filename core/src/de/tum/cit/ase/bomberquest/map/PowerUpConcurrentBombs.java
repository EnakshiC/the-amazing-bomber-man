package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

public class PowerUpConcurrentBombs extends PowerUp {

    private static final int MAX_POSSIBLE = 8;

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