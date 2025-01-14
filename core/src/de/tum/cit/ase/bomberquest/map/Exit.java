package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents the exit point in a game map, which can open or close based on the state of the map.
 * The exit is considered open when there are no enemies left on the game map,
 * and closed when enemies are still present.
 */
public class Exit implements Drawable {

    private final int x;
    private final int y;
    private final GameMap gameMap;

    public Exit(int x, int y, GameMap gameMap) {
        this.x = x;
        this.y = y;
        this.gameMap=gameMap;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if(gameMap.getEnemies().isEmpty()) {
            return Textures.EXIT_OPEN;
        }
        else {
            return Textures.EXIT_CLOSE;
        }
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }
}