package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Destroyable;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

import java.util.List;

public class BombExplosion extends SelfRemovingElement implements Destroyable {

    /** The time it takes for the explosion to evaporate. */
    public static final float EXPLOSION_DURATION = 1.0f;

    final float x;
    final float y;

    final BombExplosionTile bombExplosionTile;

    final Body hitbox;

    public BombExplosion(World world, float x, float y, BombExplosionTile bombExplosionTile, List<Drawable> killList) {
        super(EXPLOSION_DURATION, killList);

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
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public Body getHitbox() {
        return hitbox;
    }

    @Override
    public void destroyBody(World world) {
        if (hitbox != null) {
            // world.destroyBody(hitbox);
        }
    }
}
