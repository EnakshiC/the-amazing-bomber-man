package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.texture.Animations;

public class BasicEnemy extends Enemy {

    public BasicEnemy(World world, float x, float y, GameMap gameMap) {
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
