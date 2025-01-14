package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

public class PowerUpBombRadius extends PowerUp {

    private final static int MAX_POSSIBLE = 8;

    public PowerUpBombRadius(World world, float x, float y, List<Drawable> objectsToBeRemovedNextCycle) {
        super(world, x, y, new BombRadiusEffect(), Textures.POWER_UP_BOMB_RADIUS, objectsToBeRemovedNextCycle);
    }

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
