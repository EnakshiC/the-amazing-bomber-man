package de.tum.cit.ase.bomberquest.map.basic_tiles;

import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

/**
 * The abstract class of a Wall.
 * Is the basis of the IndestructibleWall and DestructibleWall
 */
public abstract class Wall implements Drawable {
    protected final float x;
    protected final float y;
    protected final Body body;
    protected boolean solid;

    /**
     * Create a wall at the given position.
     * @param world The Box2D world to add the chest's hitbox to.
     * @param x The X position.
     * @param y The Y position.
     * @param solid Weather the wall is solid (cannot be passed through) or not.
     */
    public Wall(World world, float x, float y, boolean solid) {
        this.x = x;
        this.y = y;
        this.solid = solid;
        this.body = HitboxHelper.createPolygonHitbox(world, x, y, this);
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }


    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}