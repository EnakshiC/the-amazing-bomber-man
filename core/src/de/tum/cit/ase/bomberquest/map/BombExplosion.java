package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.SpriteSheet;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

import java.util.List;

public class BombExplosion extends SelfRemovingElement {

    /** The time it takes for the explosion to evaporate. */
    public static final float EXPLOSION_DURATION = 2.0f;

    final float x;
    final float y;

    final BombExplosionTile bombExplosionTile;

    final Body body;

    public BombExplosion(World world, float x, float y, BombExplosionTile bombExplosionTile, List<Drawable> killList) {
        super(EXPLOSION_DURATION, killList);

        this.x = x;
        this.y = y;
        this.bombExplosionTile = bombExplosionTile;

        body = HitboxHelper.createPolygonHitbox(world, x, y, this, true);
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

    public Body getBody() {
        return body;
    }
}
