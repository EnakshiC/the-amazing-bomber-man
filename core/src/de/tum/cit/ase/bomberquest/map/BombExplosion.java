package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.SpriteSheet;

public class BombExplosion implements Drawable {
    /** Total time elapsed since the explosion started. We use this for animating it. */
    private float elapsedTime;

    final float x;
    final float y;

    final BombExplosionTile bombExplosionTile;


    public BombExplosion(float x, float y, BombExplosionTile bombExplosionTile) {
        this.x = x;
        this.y = y;
        this.bombExplosionTile = bombExplosionTile;

    }

    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return bombExplosionTile.getAnimation().getKeyFrame(elapsedTime);
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
