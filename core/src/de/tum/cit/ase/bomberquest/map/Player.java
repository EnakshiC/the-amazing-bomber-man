package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

import java.util.Objects;

/**
 * Represents the player character in the game.
 * The player has a hitbox, so it can collide with other objects in the game.
 */
public class Player implements Drawable {

    /**
     * Total time elapsed since the game started. We use this for calculating the player movement and animating it.
     */
    private float elapsedTime;

    /**
     * The Box2D hitbox of the player, used for position and collision detection.
     */
    private final Body hitbox;

    /**
     * Current movement direction of the player
     */
    private Direction currentDirection = Direction.NONE;

    /**
     * Enum for the possible movement directions of the player.
     */
    private enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    /**
     * Constructor to create a player instance at a specified position.
     *
     * @param world The Box2D world to add the player's hitbox to.
     * @param x     The starting x-coordinate of the player.
     * @param y     The starting y-coordinate of the player.
     */
    public Player(World world, float x, float y) {
        this.hitbox = HitboxHelper.createCircleHitbox(world, x, y, this, false);
    }

    /**
     * Tick method to update player state each frame.
     *
     * @param frameTime the time since the last frame.
     */
    public void tick(float frameTime) {
        //Incrementing elapsed time for animations
        this.elapsedTime += frameTime;

        // Update the player's direction if a new direction is detected
        this.currentDirection = determineDirection();

        // Update the velocity of the player based on current direction
        updateVelocity();
    }

    /**
     * Determines the direction based on key presses.
     * Priority: LEFT -> RIGHT -> UP -> DOWN.
     *
     * @return The determined direction or NONE if no key is pressed.
     */
    private Direction determineDirection() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) return Direction.LEFT;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) return Direction.RIGHT;
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) return Direction.UP;
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) return Direction.DOWN;

        return Direction.NONE;
    }

    /**
     * Updates the velocity of the player based on the current direction.
     * Sets the velocity to zero if no direction is active.
     */
    private void updateVelocity() {
        float xVelocity = 0.0f;
        float yVelocity = 0.0f;

        switch (this.currentDirection) {
            case LEFT -> xVelocity = -2.0f;
            case RIGHT -> xVelocity = 2.0f;
            case UP -> yVelocity = 2.0f;
            case DOWN -> yVelocity = -2.0f;
            case NONE -> {
                xVelocity = 0.0f;
                yVelocity = 0.0f;
            }
        }
        this.hitbox.setLinearVelocity(xVelocity, yVelocity);
    }

    /**
     * Returns the appropriate texture region (animation frame) based on the player's movement state.
     *
     * @return The texture region for the current appearance of the player.
     */
    @Override
    public TextureRegion getCurrentAppearance() {
        if (Objects.equals(this.hitbox.getLinearVelocity(), new Vector2(0.0f, 0.0f))) {
            return Animations.CHARACTER_STANDING.getKeyFrame(elapsedTime, true);
        }

        // TODO: Have different appearance when laying a bomb...

        return switch (this.currentDirection) {
            case UP -> Animations.CHARACTER_WALK_UP.getKeyFrame(elapsedTime, true);
            case DOWN -> Animations.CHARACTER_WALK_DOWN.getKeyFrame(elapsedTime, true);
            case LEFT -> Animations.CHARACTER_WALK_LEFT.getKeyFrame(elapsedTime, true);
            case RIGHT -> Animations.CHARACTER_WALK_RIGHT.getKeyFrame(elapsedTime, true);
            default -> Animations.CHARACTER_STANDING.getKeyFrame(elapsedTime, true);
        };
    }

    /**
     * Returns the current x-coordinate of the player.
     *
     * @return The x-coordinate of the player's hitbox.
     */
    @Override
    public float x() {
        // The x-coordinate of the player is the x-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().x;
    }

    /**
     * Returns the current y-coordinate of the player.
     *
     * @return The y-coordinate of the player's hitbox.
     */
    @Override
    public float y() {
        // The y-coordinate of the player is the y-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().y;
    }
}
