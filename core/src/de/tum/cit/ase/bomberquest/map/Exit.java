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

    public Exit(World world, int x, int y, GameMap gameMap) {
        this.x = x;
        this.y = y;
        this.gameMap=gameMap;

        HitboxHelper.createPolygonHitbox(world, x, y, this, false, true);
    }

    /** The exit is open if there are no more enemies alive in the map. */
    public boolean isOpen() {
        return gameMap.getEnemies().isEmpty();
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if(isOpen()) {
            return Textures.EXIT_OPEN;
        }
        else {
            return Textures.EXIT_CLOSE;
        }
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }
}