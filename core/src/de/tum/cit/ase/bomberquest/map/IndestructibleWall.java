package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents an indestructible wall in the game world.
 * This wall is solid and cannot be passed through or destroyed by any game mechanics such as bombs or other interactions.
 */
public class IndestructibleWall extends Wall {

    public IndestructibleWall(World world, float x, float y) {
        super(world, x, y, true);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.INDESTRUCTIBLE_WALL;
    }
}
