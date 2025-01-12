package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.HitboxHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Enemy implements Drawable {
    private float elapsedTime;
    private final Body hitbox;
    private Direction currentDirection = Direction.NONE;
    private final GameMap gameMap;

    protected abstract TextureRegion getStandingFrame(float elapsedTime);

    protected abstract TextureRegion getWalkUpFrame(float elapsedTime);
    protected abstract TextureRegion getWalkDownFrame(float elapsedTime);
    protected abstract TextureRegion getWalkLeftFrame(float elapsedTime);
    protected abstract TextureRegion getWalkRightFrame(float elapsedTime);

    private enum Direction { UP, DOWN, LEFT, RIGHT, NONE }

    public Enemy(World world, float x, float y, GameMap gameMap) {
        this.hitbox = HitboxHelper.createCircleHitbox(world, x, y, this);
        this.gameMap = gameMap;
    }

    public void tick(float frameTime) {
        this.elapsedTime += frameTime;
        this.currentDirection = determineDirection();
        updateVelocity();
        // System.out.println("X: " + getX() + " Y: " + getY());
    }

    private Direction determineDirection() {
        List<Direction> dirs = availableDirections();

        if (dirs.contains(currentDirection)) {
            return currentDirection;
        }

        if (dirs.isEmpty()) {
            return Direction.NONE;
        }

        if (isCloseToTileCenter()) {
            return dirs.get((int) (Math.random() * dirs.size()));
        } else {
            return currentDirection;
        }
    }

    private boolean isCloseToTileCenter() {
        float px = getX();
        float py = getY();

        float tileX = (float) Math.floor(px + 0.5f);
        float tileY = (float) Math.floor(py + 0.5f);

        float dx = px - tileX;
        float dy = py - tileY;
        float distSq = dx * dx + dy * dy;

        return distSq < 0.01f;
    }

    private List<Direction> availableDirections() {
        int x = Math.round(getX());
        int y = Math.round(getY());
        List<Direction> directions = new ArrayList<>(List.of(Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN));

        for (Drawable drawable : gameMap.getWallElements()) {
            if (!(drawable instanceof IndestructibleWall) && !(drawable instanceof DestructibleWall)) continue;

            int drawableX = Math.round(drawable.getX());
            int drawableY = Math.round(drawable.getY());

            if (drawableX == x - 1 && drawableY == y) directions.remove(Direction.LEFT);
            if (drawableX == x + 1 && drawableY == y) directions.remove(Direction.RIGHT);
            if (drawableX == x && drawableY == y - 1) directions.remove(Direction.DOWN);
            if (drawableX == x && drawableY == y + 1) directions.remove(Direction.UP);
        }

        return directions;
    }

    private void updateVelocity() {
        float speed = 1f;

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
}


