package de.tum.cit.ase.bomberquest.map.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.audio.SoundEffect;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.map.basic_tiles.DestructibleWall;
import de.tum.cit.ase.bomberquest.map.basic_tiles.IndestructibleWall;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Destroyable;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an abstract enemy that can move within a game world.
 * The movement is determined based on available directions when facing a wall. Otherwise, it walks straight ahead.
 * The enemy has a hitbox for collision detection.
 */
public abstract class Enemy implements Drawable, Destroyable {
    private float elapsedTime;
    private final Body hitbox;
    protected Direction currentDirection = Direction.NONE;
    private final GameMap gameMap;

    protected final float speed = 1f;

    /** If set to true, the dying process of the enemy is started */
    private boolean isDying = false;

    /** The time elapsed since the enemy 'started' dying */
    private float elapsedDyingTime = 0.0f;

    private float elapsedTurnAroundTime = 0.0f;

    protected abstract TextureRegion getStandingFrame(float elapsedTime);
    protected abstract TextureRegion getWalkUpFrame(float elapsedTime);
    protected abstract TextureRegion getWalkDownFrame(float elapsedTime);
    protected abstract TextureRegion getWalkLeftFrame(float elapsedTime);
    protected abstract TextureRegion getWalkRightFrame(float elapsedTime);

    private final List<Drawable> objectsToBeRemovedNextCycle;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE;

        public static int dx(Direction dir) {
            return switch (dir) {
                case LEFT -> -1;
                case RIGHT -> 1;
                default -> 0;
            };
        }

        public static int dy(Direction dir) {
            return switch (dir) {
                case UP -> 1;
                case DOWN -> -1;
                default -> 0;
            };
        }
    }

    /**
     * Constructs an Enemy object in the game world at a specified position with
     * access to the game map. The enemy is initialized with a circular hitbox
     * and a reference to objects marked for removal in the game.
     *
     * @param world   The physics world in which the enemy resides, used to manage collisions and dynamics.
     * @param x       The x-coordinate of the enemy's initial position in the game world.
     * @param y       The y-coordinate of the enemy's initial position in the game world.
     * @param gameMap The game map where the enemy exists, providing context for positioning and interactions.
     */
    public Enemy(World world, float x, float y, GameMap gameMap) {
        this.hitbox = HitboxHelper.createCircleHitbox(world, x, y, this, true);
        this.gameMap = gameMap;
        this.objectsToBeRemovedNextCycle = gameMap.getObjectsToRemoveNextCycle();
    }

    /**
     * Updates the state of an enemy based on the elapsed frame time.
     * Handles following behaviors:
     * - If an enemy is dying -> increments the dying timer and schedules removal after 0.5 seconds.
     * - Updates the enemy's movement direction if it's not in a turn-around state.
     * - Adjusts the velocity of the enemy's hitbox based on its current direction and speed.
     *
     * @param frameTime The time elapsed since the last frame update, used for calculations.
     */
    public void tick(float frameTime) {
        // If an enemy is dying update dying time and remove after 0.5 seconds
        if (isDying) {
            elapsedDyingTime += frameTime;
            if (elapsedDyingTime >= 0.5f) {
                isDying = false;
                objectsToBeRemovedNextCycle.add(this);
            }
            return;
        }

        this.elapsedTime += frameTime;

        // If an enemy is currently in a turn-around, do not determine a new direction, but update elapsedTurnAroundTime
        if (elapsedTurnAroundTime <= 0.0f) {
            this.currentDirection = determineDirection();
        } else {
            elapsedTurnAroundTime -= frameTime;
        }

        updateVelocity();
    }

    /**
     * Determines the next direction an enemy should move based on its current position.
     * If an enemy is free to keep moving in the same direction, they do.
     * If the next tile in the direction is a wall, they choose randomly a new direction from the available options.
     *
     * @return The direction the enemy should move.
     */
    protected Direction determineDirection() {
        List<Direction> dirs = availableDirections();

        if (dirs.contains(currentDirection)) {
            return currentDirection;
        }

        if (dirs.isEmpty()) {
            return Direction.NONE;
        }

        if (isCloseToTileOrigin()) {
            return dirs.get((int) (Math.random() * dirs.size()));
        } else {
            return currentDirection;
        }
    }

    /**
     * Determines if the enemies current position is close to the origin of the tile it is located on.
     * The check is based on the Euclidean distance from the object's position to the
     * center of the tile origin, considering a threshold distance of less than 0.1 units.
     *
     * @return true if the current position is within a small threshold distance
     *         from the tile origin, false otherwise.
     */
    protected boolean isCloseToTileOrigin() {
        float px = getX();
        float py = getY();

        float tileX = (float) Math.floor(px);
        float tileY = (float) Math.floor(py);

        float dx = px - tileX;
        float dy = py - tileY;
        float distSq = dx * dx + dy * dy;

        return distSq < 0.01f;
    }

    /**
     * Reverses the current direction of the entity for a certain amount of time.
     */
    public void turnAround() {
        if (currentDirection == Direction.UP) {
            currentDirection = Direction.DOWN;
        } else if (currentDirection == Direction.DOWN) {
            currentDirection = Direction.UP;
        } else if (currentDirection == Direction.LEFT) {
            currentDirection = Direction.RIGHT;
        } else if (currentDirection == Direction.RIGHT) {
            currentDirection = Direction.LEFT;
        } else {
            currentDirection = Direction.NONE;
        }

        elapsedTurnAroundTime = .5f;

        // System.err.println("Enemy had to trigger turnaround due to Wall collision!");
    }

    /**
     * Determines the available movement directions for the current position of the enemy.
     * The method checks if there are obstacles (indestructible walls or solid destructible walls)
     * in the immediate adjacent tiles, and removes the corresponding direction from the list
     * of possible directions.
     *
     * @return A list of available directions (UP, DOWN, LEFT, RIGHT) that
     *         are not blocked by obstacles.
     */
    protected List<Direction> availableDirections() {
        int x = Math.round(getX());
        int y = Math.round(getY());
        List<Direction> directions = new ArrayList<>(List.of(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN));

        for (Drawable drawable : gameMap.getWallElements()) {
            if (!(drawable instanceof IndestructibleWall) && !((drawable instanceof DestructibleWall) && ((DestructibleWall) drawable).isSolid())) continue;

            int drawableX = Math.round(drawable.getX());
            int drawableY = Math.round(drawable.getY());

            if (drawableX == x - 1 && drawableY == y) directions.remove(Direction.LEFT);
            if (drawableX == x + 1 && drawableY == y) directions.remove(Direction.RIGHT);
            if (drawableX == x && drawableY == y - 1) directions.remove(Direction.DOWN);
            if (drawableX == x && drawableY == y + 1) directions.remove(Direction.UP);
        }

        return directions;
    }

    /**
     * Updates the linear velocity of the enemy's hitbox based on its current direction and speed.
     * The calculated velocities are then applied to the enemy's hitbox to update its motion
     * in the physics simulation.
     */
    private void updateVelocity() {
        float xVelocity = 0.0f;
        float yVelocity = 0.0f;

        switch (this.currentDirection) {
            case LEFT -> xVelocity = -speed;
            case RIGHT -> xVelocity = speed;
            case UP -> yVelocity = speed;
            case DOWN -> yVelocity = -speed;
            case NONE -> {
                xVelocity = 0.0f;
                yVelocity = 0.0f;
            }
        }

        this.hitbox.setLinearVelocity(xVelocity, yVelocity);
    }

    /** Triggers the dying state of the enemy. */
    //TODO - Adapt new sounds for different enemies?
    public void kill() {
        isDying = true;
        SoundEffect.BASIC_ENEMY_DYING.play();
    }

    @Override
    public float getX() {
        return hitbox.getPosition().x;
    }


    @Override
    public float getY() {
        return hitbox.getPosition().y;
    }

    @Override
    public TextureRegion getCurrentAppearance() {
        if (isDying) {
            return Animations.ENEMY_DYING.getKeyFrame(elapsedDyingTime, true);
        }

        if (Objects.equals(this.hitbox.getLinearVelocity(), new Vector2(0.0f, 0.0f))) {
            return getStandingFrame(elapsedTime);
        }

        return switch (this.currentDirection) {
            case UP -> getWalkUpFrame(elapsedTime);
            case DOWN -> getWalkDownFrame(elapsedTime);
            case LEFT -> getWalkLeftFrame(elapsedTime);
            case RIGHT -> getWalkRightFrame(elapsedTime);
            default -> getStandingFrame(elapsedTime);
        };
    }

    @Override
    public void destroyBody(World world) {
        if (this.hitbox != null) {
            world.destroyBody(hitbox);
        }
    }
}


