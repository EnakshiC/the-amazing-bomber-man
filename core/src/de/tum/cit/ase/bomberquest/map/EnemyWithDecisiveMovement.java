package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;

import java.util.List;

/**
 * A basic enemy that walks straight and decides at any crossing for one of the directions.
 * Look: Slimy green monster.
 */
public class EnemyWithDecisiveMovement extends Enemy {
    /** The time the enemy should stick with the current decision before making a new one
     * Ensures that the enemy is not turning multiple times on the same tile.
     */
    private static final float DECISION_DELAY = .1f;
    private float decisionTimer = 0.0f;

    public EnemyWithDecisiveMovement(World world, float x, float y, GameMap gameMap) {
        super(world, x, y, gameMap);
    }

    /**
     * Determines the next direction the enemy should move based on its current position.
     * If the enemy is on a tile with multiple directions ahead it randomly chooses one.
     *
     * @return The direction the enemy should move.
     */
    @Override
    protected Direction determineDirection() {
        List<Direction> dirs = super.availableDirections();

        // If there is nowhere to go, stand still
        if (dirs.isEmpty()) {
            return Direction.NONE;
        }

        // Remove the way back option
        dirs.remove(oppositeDirection(super.currentDirection));

        // If a decision was just made, do not do another one
        if (decisionTimer > 0.0f) {
            return super.currentDirection;
        }

        if (isCloseToTileOrigin()) {
            // Set decision timer, to delay next decision
            decisionTimer = DECISION_DELAY;

            // If it is now empty, we only could move backwards, so do it
            if (dirs.isEmpty()) {
                return oppositeDirection(super.currentDirection);
            }

            return dirs.get((int) (Math.random() * dirs.size()));
        } else {
            return currentDirection;
        }
    }

    private Direction oppositeDirection(Direction dir) {
        return switch (dir) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;
            default -> Direction.NONE;
        };
    }

    @Override
    public void tick(float frameTime) {
        super.tick(frameTime);

        if (decisionTimer <= 0.0f) return;

        decisionTimer -= frameTime;
    }

    @Override
    protected TextureRegion getStandingFrame(float elapsedTime) {
        return Animations.SLIMY_ENEMY_STANDING.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkUpFrame(float elapsedTime) {
        return Animations.SLIMY_ENEMY_WALK_UP.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkDownFrame(float elapsedTime) {
        return Animations.SLIMY_ENEMY_WALK_DOWN.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkLeftFrame(float elapsedTime) {
        return Animations.SLIMY_ENEMY_WALK_LEFT.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkRightFrame(float elapsedTime) {
        return Animations.SLIMY_ENEMY_WALK_RIGHT.getKeyFrame(elapsedTime, true);
    }
}
