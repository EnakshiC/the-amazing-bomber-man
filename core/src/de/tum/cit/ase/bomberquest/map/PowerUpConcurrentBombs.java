package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

public class PowerUpConcurrentBombs extends PowerUp {

    private static final int MAX_POSSIBLE = 8;

    public PowerUpConcurrentBombs(World world, float x, float y, List<Drawable> killList) {
        super(world, x, y, new ConcurrentBombsEffect(), Textures.POWER_UP_CONCURRENT_BOMBS, killList);
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
                System.out.println("Max bombs allowed increased to: " + (currentMaxBombs + 1));
            } else {
                System.out.println("Max bombs allowed is already at the maximum: " + MAX_POSSIBLE);
            }
        }
    }
}