package de.tum.cit.ase.bomberquest.map.basic_tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Destroyable;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

public class DestructibleWall extends Wall implements Destroyable {

    private final List<Drawable> objectsToBeRemovedNextCycle;

    public DestructibleWall(World world, float x, float y, List<Drawable> objectsToBeRemovedNextCycle) {
        super(world, x, y, true);
        this.objectsToBeRemovedNextCycle = objectsToBeRemovedNextCycle;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return this.solid ? Textures.DESTRUCTIBLE_WALL : Textures.EMPTY;
    }

    /**
     * Destroys wall by setting isSolid to false and removing the body
     */
    public void destroy() {
        setSolid(false);
        this.objectsToBeRemovedNextCycle.add(this);
    }

    @Override
    public void destroyBody(World world) {
        if (this.body != null) {
            world.destroyBody(body);
        }
    }
}