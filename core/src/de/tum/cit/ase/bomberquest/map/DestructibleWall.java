package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Textures;

public class DestructibleWall extends Wall {

    public DestructibleWall(World world, float x, float y) {
        super(world, x, y, true);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        // TODO: Handle elements underneath! How do I stack them that they are not visible before, but now they are...

        return this.isSolid ? Textures.DESTRUCTIBLE_WALL : Textures.EMPTY;
    }

    /**
     * Destroys wall by setting isSolid to false and removing the body
     */
    public void destroy() {
        setSolid(false);
        // Remove the Hitbox from the Box2D-World
        // body.getWorld().destroyBody(body);
    }
}