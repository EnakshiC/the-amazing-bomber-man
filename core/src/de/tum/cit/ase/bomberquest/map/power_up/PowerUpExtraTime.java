package de.tum.cit.ase.bomberquest.map.power_up;

import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

/**
 * Represents a specific type of PowerUp that adds an extra 15 seconds to the game time.
 * The effect is limited to a maximum radius.
 */
public class PowerUpExtraTime extends PowerUp {

    private static final float ADDED_TIME = 15.0f;

    /**
     * Constructs a PowerUpBombRadius instance, a specific type of PowerUp that increases the time left in the game
     * by 15 seconds.
     *
     * @param world The physics world where the PowerUp exists, enabling physical interactions.
     * @param x The X-coordinate (in tiles) of the PowerUp's position.
     * @param y The Y-coordinate (in tiles) of the PowerUp's position.
     * @param objectsToBeRemovedNextCycle A list of objects scheduled for removal in the next game cycle.
     */
    public PowerUpExtraTime(World world, float x, float y, List<Drawable> objectsToBeRemovedNextCycle) {
        super(world, x, y, new TimeAddEffect(), Textures.POWER_UP_EXTRA_TIME, objectsToBeRemovedNextCycle);
    }

    /**
     * The effect of this power-up: increases the maximum radius of a bomb explosion.
     */
    private static class TimeAddEffect implements PowerUpEffect {
        @Override
        public void apply(GameMap gameMap) {
            gameMap.addTime(ADDED_TIME);
        }
    }
}
