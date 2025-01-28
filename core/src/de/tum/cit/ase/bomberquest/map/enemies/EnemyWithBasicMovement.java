package de.tum.cit.ase.bomberquest.map.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Animations;

/**
 * A basic enemy that walks straight as long as it is possible.
 * When hitting a wall, it randomly chooses one of the available other directions.
 * Look: White walking human like enemy.
 */
public class EnemyWithBasicMovement extends Enemy {

    /**
     * Constructs an instance of a basic enemy with simple movement behavior.
     *
     * @param world the physical world where the enemy exists
     * @param x the initial x-coordinate of the enemy in the game world
     * @param y the initial y-coordinate of the enemy in the game world
     * @param gameMap the game map the enemy is associated with
     */
    public EnemyWithBasicMovement(World world, float x, float y, GameMap gameMap) {
        super(world, x, y, gameMap);
    }


    /**
     * Retrieves the animation frame for the enemy when it is standing still.
     *
     * @param elapsedTime The elapsed time since the animation started, used for frame calculation.
     * @return The {@link TextureRegion} representing the standing animation frame of the enemy.
     */
    @Override
    protected TextureRegion getStandingFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_STANDING.getKeyFrame(elapsedTime, true);
    }

    /**
     * Retrieves the animation frame for the enemy when moving upwards.
     *
     * @param elapsedTime The elapsed time since the animation started, used for frame calculation.
     * @return The {@link TextureRegion} representing the upward walking animation frame of the enemy.
     */
    @Override
    protected TextureRegion getWalkUpFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_WALK_UP.getKeyFrame(elapsedTime, true);
    }

    /**
     * Retrieves the animation frame for the enemy when moving downwards.
     *
     * @param elapsedTime The elapsed time since the animation started, used for frame calculation.
     * @return The {@link TextureRegion} representing the downward walking animation frame of the enemy.
     */
    @Override
    protected TextureRegion getWalkDownFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_WALK_DOWN.getKeyFrame(elapsedTime, true);
    }

    /**
     * Retrieves the animation frame for the enemy when moving leftwards.
     *
     * @param elapsedTime The elapsed time since the animation started, used for frame calculation.
     * @return The {@link TextureRegion} representing the leftward walking animation frame of the enemy.
     */
    @Override
    protected TextureRegion getWalkLeftFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_WALK_LEFT.getKeyFrame(elapsedTime, true);
    }

    /**
     * Retrieves the animation frame for the enemy when moving rightwards.
     *
     * @param elapsedTime The elapsed time since the animation started, used for frame calculation.
     * @return The {@link TextureRegion} representing the rightward walking animation frame of the enemy.
     */
    @Override
    protected TextureRegion getWalkRightFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_WALK_RIGHT.getKeyFrame(elapsedTime, true);
    }
}
