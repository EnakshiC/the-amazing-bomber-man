package de.tum.cit.ase.bomberquest.map.bomb;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.SelfRemovingElement;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

import java.util.List;

/**
 * Represents an explosion that occurs when a bomb detonates in the game.
 * The explosion lasts for a limited duration, after which it self-removes
 * from the game world.
 */
public class BombExplosion extends SelfRemovingElement {

    /** The time it takes for the explosion to evaporate. */
    public static final float EXPLOSION_DURATION = 1.0f;

    final float x;
    final float y;

    final BombExplosionTile bombExplosionTile;

    final Body hitbox;

    /**
     * Constructs a new BombExplosion object representing an explosion triggered by a bomb.
     * The explosion has a limited duration after which it is automatically removed from the game.
     *
     * @param world The game world in which the explosion occurs. It is used for registering the hitbox.
     * @param x The X coordinate of the explosion's origin within the game world grid.
     * @param y The Y coordinate of the explosion's origin within the game world grid.
     * @param bombExplosionTile The specific visual and structural tile to use for this explosion.
     * @param objectsToBeRemovedNextCycle A list of objects that will be removed from the game at the end of their lifecycle.
     */
    public BombExplosion(World world, float x, float y, BombExplosionTile bombExplosionTile, List<Drawable> objectsToBeRemovedNextCycle) {
        super(EXPLOSION_DURATION, objectsToBeRemovedNextCycle);

        this.x = x;
        this.y = y;
        this.bombExplosionTile = bombExplosionTile;

        hitbox = HitboxHelper.createPolygonHitbox(world, x, y, this, true, true);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return bombExplosionTile.getAnimation().getKeyFrame(super.getTimeSinceCreation());
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    public Body getHitbox() {
        return hitbox;
    }
}
