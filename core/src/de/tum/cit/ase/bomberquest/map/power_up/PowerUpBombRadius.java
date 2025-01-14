package de.tum.cit.ase.bomberquest.map.power_up;

import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

/**
 * Represents a specific type of PowerUp that increases the radius of bombs when collected.
 * The effect is limited to a maximum radius.
 */
public class PowerUpBombRadius extends PowerUp {

    private final static int MAX_POSSIBLE = 8;

    /**
     * Constructs a PowerUpBombRadius instance, a specific type of PowerUp that increases the bomb radius
     * when collected by a player. The effect is capped at a predefined maximum radius.
     *
     * @param world The physics world where the PowerUp exists, enabling physical interactions.
     * @param x The X-coordinate (in tiles) of the PowerUp's position.
     * @param y The Y-coordinate (in tiles) of the PowerUp's position.
     * @param objectsToBeRemovedNextCycle A list of objects scheduled for removal in the next game cycle.
     */
    public PowerUpBombRadius(World world, float x, float y, List<Drawable> objectsToBeRemovedNextCycle) {
        super(world, x, y, new BombRadiusEffect(), Textures.POWER_UP_BOMB_RADIUS, objectsToBeRemovedNextCycle);
    }

    /**
     * The effect of this power-up: increases the maximum radius of a bomb explosion.
     */
    private static class BombRadiusEffect implements PowerUpEffect {
        @Override
        public void apply(GameMap gameMap) {
            int currentRadius = gameMap.getBombRadius();
            if (currentRadius < MAX_POSSIBLE) {
                gameMap.setBombRadius(currentRadius + 1);
            }
        }
    }
}
