package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.SpriteSheet;

import java.util.Objects;

/**
 * Represents the player character in the game.
 * The player has a hitbox, so it can collide with other objects in the game.
 */
public class Player implements Drawable {
    
    /** Total time elapsed since the game started. We use this for calculating the player movement and animating it. */
    private float elapsedTime;
    
    /** The Box2D hitbox of the player, used for position and collision detection. */
    private final Body hitbox;

    /** Current movement direction of the player */
    private Direction currentDirection = Direction.NONE;

    /** Enum for the possible movement directions of the player. */
    private enum Direction
    {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    /**
     * Constructor to create a player instance at a specified position.
     * @param world The Box2D world to add the player's hitbox to.
     * @param x The starting x-coordinate of the player.
     * @param y The starting y-coordinate of the player.
     */
    public Player(World world, float x, float y) {
        this.hitbox = createHitbox(world, x, y);
    }
    
    /**
     * Creates a Box2D body for the player.
     * This is what the physics engine uses to move the player around and detect collisions with other bodies.
     * @param world The Box2D world to add the body to.
     * @param startX The initial X position.
     * @param startY The initial Y position.
     * @return The created body.
     */
    private Body createHitbox(World world, float startX, float startY) {
        // BodyDef is like a blueprint for the movement properties of the body.
        BodyDef bodyDef = new BodyDef();
        // Dynamic bodies are affected by forces and collisions.
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set the initial position of the body.
        bodyDef.position.set(startX, startY);
        // Create the body in the world using the body definition.
        Body body = world.createBody(bodyDef);
        // Now we need to give the body a shape so the physics engine knows how to collide with it.
        // We'll use a circle shape for the player.
        CircleShape circle = new CircleShape();
        // Give the circle a radius of 0.3 tiles (the player is 0.6 tiles wide).
        circle.setRadius(0.3f);
        // Attach the shape to the body as a fixture.
        // Bodies can have multiple fixtures, but we only need one for the player.
        body.createFixture(circle, 1.0f);
        // We're done with the shape, so we should dispose of it to free up memory.
        circle.dispose();
        // Set the player as the user data of the body so we can look up the player from the body later.
        body.setUserData(this);
        return body;
    }
    
    /**
     * Move the player around in a circle by updating the linear velocity of its hitbox every frame.
     * This doesn't actually move the player, but it tells the physics engine how the player should move next frame.
     * @param frameTime the time since the last frame.
     */

    /**
     * Tick method to update player state each frame.
     * @param frameTime the time since the last frame.
     */
    public void tick(float frameTime) {
        //Incrementing elapsed time for animations
        this.elapsedTime += frameTime;
        // Make the player move in a circle with radius 2 tiles
        // You can change this to make the player move differently, e.g. in response to user input.
        // See Gdx.input.isKeyPressed() for keyboard input

        // Determine the new direction based on user input
        // TODO: Adapt player movement logic to reflect one direction at a time based on user input
        Direction newDirection = determineDirection();

        // Update the player's direction if a new direction is detected
        this.currentDirection = newDirection;

        // Update the velocity of the player based on current direction
        // TODO: Ensure movement reflects the chosen direction and stops otherwise
        updateVelocity();
    }

   /**
     * Determines the direction based on key presses.
     * Priority: LEFT -> RIGHT -> UP -> DOWN.
     * @return The determined direction or NONE if no key is pressed.
     */

    private Direction determineDirection()
    {
        // TODO: Check key input to determine movement direction
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            return Direction.LEFT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            return Direction.RIGHT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            return Direction.UP;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            return Direction.DOWN;
        }
        return Direction.NONE;
    }

    /**
     * Updates the velocity of the player based on the current direction.
     * Sets the velocity to zero if no direction is active.
     */
    private void updateVelocity()
    {
        float xVelocity=0.0f;
        float yVelocity=0.0f;

        //TODO: Set velocity based on current direction
        switch(this.currentDirection)
        {
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
     * @return The texture region for the current appearance of the player.
     */

    //TODO: Update Player Class: Modify getCurrentAppearance() to return the appropriate animation frame based on the player's movement direction (up, down, left, right)
    @Override
    public TextureRegion getCurrentAppearance() {
        //TODO: Return standing animation if velocity is 0.0
        if (Objects.equals(this.hitbox.getLinearVelocity(), new Vector2(0.0f, 0.0f))) {
            return Animations.CHARACTER_STANDING.getKeyFrame(elapsedTime, true);
        }

        //TODO: Return the appropriate animation based on the current direction
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
     * @return The x-coordinate of the player's hitbox.
     */
    @Override
    public float getX() {
        // The x-coordinate of the player is the x-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().x;
    }

    /**
     * Returns the current y-coordinate of the player.
     * @return The y-coordinate of the player's hitbox.
     */
    @Override
    public float getY() {
        // The y-coordinate of the player is the y-coordinate of the hitbox (this can change every frame).
        return hitbox.getPosition().y;
    }
}
