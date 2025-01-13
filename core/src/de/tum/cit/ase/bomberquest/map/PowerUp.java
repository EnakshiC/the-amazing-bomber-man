package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.audio.SoundEffect;
import de.tum.cit.ase.bomberquest.texture.Destroyable;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

import java.util.List;

public abstract class PowerUp implements Drawable, Destroyable {

    private final float x;
    private final float y;

    private final TextureRegion appearance;

    private final List<Drawable> killList;

    private final PowerUpEffect effect;

    private final Body hitbox;

    public PowerUp(World world, float x, float y, PowerUpEffect effect, TextureRegion appearance, List<Drawable> killList) {
        this.x = x;
        this.y = y;
        this.appearance = appearance;
        this.killList = killList;
        this.effect = effect;

        // Has the same hitbox size as player which is only 70% of tile
        // This way it is well hidden under a DestructibleWall (98% width hitbox)
        hitbox = HitboxHelper.createCircleHitbox(world, x, y, this, true);
    }

    public void collect(GameMap gameMap) {
        // System.out.println("Collect! " + this);

        SoundEffect.POWER_UP.play();

        effect.apply(gameMap);
        killList.add(this);
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        return appearance;
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
    public void destroyBody(World world) {
        if (hitbox != null) {
            world.destroyBody(hitbox);
        }
    }
}
