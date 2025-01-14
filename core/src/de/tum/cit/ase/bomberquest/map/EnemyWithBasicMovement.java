package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
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

    @Override
    protected TextureRegion getStandingFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_STANDING.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkUpFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_WALK_UP.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkDownFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_WALK_DOWN.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkLeftFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_WALK_LEFT.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkRightFrame(float elapsedTime) {
        return Animations.BASIC_ENEMY_WALK_RIGHT.getKeyFrame(elapsedTime, true);
    }
}
