package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Destroyable;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an abstract enemy that can move within a game world.
 * The movement is determined based on available directions when facing a wall. Otherwise it walks straight ahead.
 * The enemy has a hitbox for collision detection.
 */
public abstract class Enemy implements Drawable, Destroyable {
    private float elapsedTime;
    private final Body hitbox;
    protected Direction currentDirection = Direction.NONE;
    private final GameMap gameMap;

    protected final float speed = 1f;

    private boolean isDying = false;
    private float elapsedDyingTime = 0.0f;

    protected abstract TextureRegion getStandingFrame(float elapsedTime);

    protected abstract TextureRegion getWalkUpFrame(float elapsedTime);
    protected abstract TextureRegion getWalkDownFrame(float elapsedTime);
    protected abstract TextureRegion getWalkLeftFrame(float elapsedTime);
    protected abstract TextureRegion getWalkRightFrame(float elapsedTime);

    private final List<Drawable> killList;

    public enum Direction { UP, DOWN, LEFT, RIGHT, NONE }

    public Enemy(World world, float x, float y, GameMap gameMap) {
        this.hitbox = HitboxHelper.createCircleHitbox(world, x, y, this, true);
        this.gameMap = gameMap;
        this.killList = gameMap.getElementsToRemoveNextCycle();
    }

    public void tick(float frameTime) {
        if (isDying) {
            elapsedDyingTime += frameTime;
            if (elapsedDyingTime >= 0.5f) {
                isDying = false;
                killList.add(this);
            }
            return;
        }

        this.elapsedTime += frameTime;
        this.currentDirection = determineDirection();
        updateVelocity();
        // System.out.println("X: " + getX() + " Y: " + getY());
    }

    /**
     * Determines the next direction the enemy should move based on its current position.
     * If the enemy is free to keep moving in the same direction, they do.
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
            if (!(drawable instanceof IndestructibleWall) && !((drawable instanceof DestructibleWall) && ((DestructibleWall) drawable).isSolid)) continue;

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

    public void die() {
        System.out.println("Enemy died: " + this);

        isDying = true;
        //killList.add(this);
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


