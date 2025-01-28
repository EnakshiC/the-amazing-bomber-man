package de.tum.cit.ase.bomberquest.map.power_up;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.audio.SoundEffect;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Destroyable;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

import java.util.List;

/**
 * Represents a power-up in the game, which can be collected by players to
 * activate its effect. A power-up is a drawable entity with a physical
 * presence in the game world and a specific effect applied upon collection.
 */
public abstract class PowerUp implements Drawable, Destroyable {

    private final float x;
    private final float y;

    private final TextureRegion appearance;

    private final List<Drawable> objectsToBeRemovedNextCycle;

    /** The effect of the PowerUp containing the apply method. */
    private final PowerUpEffect effect;

    private final Body hitbox;

    /**
     * Creates a new PowerUp instance that applies a designated effect when picked up by the player.
     *
     * @param world The physics world where the PowerUp exists, enabling physical interactions.
     * @param x The X-coordinate (in tiles) of the PowerUp's position.
     * @param y The Y-coordinate (in tiles) of the PowerUp's position.
     * @param effect The specific effect that will be applied when the PowerUp is collected.
     * @param appearance The texture or visual representation of the PowerUp.
     * @param objectsToBeRemovedNextCycle A list of objects scheduled for removal in the next game cycle.
     */
    public PowerUp(World world, float x, float y, PowerUpEffect effect, TextureRegion appearance, List<Drawable> objectsToBeRemovedNextCycle) {
        this.x = x;
        this.y = y;
        this.appearance = appearance;
        this.objectsToBeRemovedNextCycle = objectsToBeRemovedNextCycle;
        this.effect = effect;

        // Has the same hitbox size as player which is only 70% of tile
        // This way it is well hidden under a DestructibleWall (98% width hitbox)
        hitbox = HitboxHelper.createCircleHitbox(world, x, y, this, true);
    }

    /**
     * Collects the PowerUp, triggering its effect and sound, scheduling it for removal in the next game cycle.
     *
     * @param gameMap The current game map where the PowerUp's effect will be applied upon collection.
     */
    public void collect(GameMap gameMap) {
        SoundEffect.POWER_UP.play();

        effect.apply(gameMap);
        objectsToBeRemovedNextCycle.add(this);
    }

    /**
     * Retrieves the current visual appearance of the PowerUp.
     *
     * @return The {@link TextureRegion} representing the PowerUp's appearance.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        return appearance;
    }

    /**
     * Retrieves the x-coordinate of the PowerUp's position.
     *
     * @return The x-coordinate in the game world.
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate of the PowerUp's position.
     *
     * @return The y-coordinate in the game world.
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     * Destroys the PowerUp's physical hitbox in the game world.
     *
     * @param world The physics world where the hitbox is registered.
     */
    @Override
    public void destroyBody(World world) {
        if (hitbox != null) {
            world.destroyBody(hitbox);
        }
    }
}
