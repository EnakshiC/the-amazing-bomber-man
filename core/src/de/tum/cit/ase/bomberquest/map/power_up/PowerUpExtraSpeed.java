package de.tum.cit.ase.bomberquest.map.power_up;

import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

/**
 * Represents a specific type of PowerUp that adds extra speed to the player.
 */
public class PowerUpExtraSpeed extends PowerUp {

    /**
     * Constructs a PowerUpExtraSpeed instance, a specific type of PowerUp that increases the speed of the player.
     *
     * @param world The physics world where the PowerUp exists, enabling physical interactions.
     * @param x The X-coordinate (in tiles) of the PowerUp's position.
     * @param y The Y-coordinate (in tiles) of the PowerUp's position.
     * @param objectsToBeRemovedNextCycle A list of objects scheduled for removal in the next game cycle.
     */
    public PowerUpExtraSpeed(World world, float x, float y, List<Drawable> objectsToBeRemovedNextCycle) {
        super(world, x, y, new SpeedAddEffect(), Textures.POWER_UP_EXTRA_SPEED, objectsToBeRemovedNextCycle);
    }

    /**
     * The effect of this power-up: increases the speed of the player.
     */
    private static class SpeedAddEffect implements PowerUpEffect {
        @Override
        public void apply(GameMap gameMap) {
            gameMap.getPlayer().increaseSpeed();
        }
    }
}
