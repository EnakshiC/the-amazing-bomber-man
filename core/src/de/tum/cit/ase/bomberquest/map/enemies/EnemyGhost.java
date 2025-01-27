package de.tum.cit.ase.bomberquest.map.enemies;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Animations;

import java.util.Random;

/**
 * EnemyGjost mirrors the EnemyWithDecisiveMovement,
 * but is invisible for a couple of seconds before becoming visible again.
 */
public class EnemyGhost extends EnemyWithDecisiveMovement{
    private float alpha = 1.0f;
    private float visibilityTimer; // Timer to track visibility changes
    private GhostState currentState = GhostState.VISIBLE;

    private static final float VISIBLE_DURATION = 4.0f; //4 Secs - full visibility
    private static final float INVISIBLE_DURATION = 4.0f; //4 Secs - no visibility
    private static final float FADE_DURATION = 1.0f; //1 Sec - fading transition

    private static final Random random = new Random();

    private enum GhostState{
        VISIBLE,
        FADING_OUT,
        INVISIBLE,
        FADING_IN
    }

    /**
     * Constructs a Ghost Enemy object in the game world at a specified position with access to the game map.
     *
     * @param world   The physics world in which the enemy resides, used to manage collisions and dynamics.
     *      * @param x       The x-coordinate of the enemy's initial position in the game world.
     *      * @param y       The y-coordinate of the enemy's initial position in the game world.
     *      * @param gameMap The game map where the enemy exists, providing context for positioning and interactions.
     */

    public EnemyGhost(World world, float x, float y, GameMap gameMap){
        super(world, x, y, gameMap);

        //Initialise timer with random offset
        //To ensure not all EnemyGhosts vanishing at once
        this.visibilityTimer=random.nextFloat()*(VISIBLE_DURATION + INVISIBLE_DURATION + 2*FADE_DURATION);
    }

    @Override
    public void tick(float frameTime)
    {
            super.tick(frameTime);

            //Update visibility timer
            visibilityTimer += frameTime;

            //Update aplpha dependent on current state & visibility timer
            switch(currentState){
                case VISIBLE -> {
                    if (visibilityTimer >= VISIBLE_DURATION) {
                        visibilityTimer = 0.0f;
                        currentState = GhostState.FADING_OUT;
                    }
                    alpha = 1.0f; // Fully visible
                }

                case FADING_OUT -> {
                    alpha = 1.0f - (visibilityTimer / FADE_DURATION); // Linear fade out
                    if (visibilityTimer >= FADE_DURATION) {
                        visibilityTimer = 0.0f;
                        currentState = GhostState.INVISIBLE;
                        alpha = 0.0f; // Ensure fully invisible
                }
            }
                case INVISIBLE -> {
                    if (visibilityTimer >= INVISIBLE_DURATION) {
                        visibilityTimer = 0.0f;
                        currentState = GhostState.FADING_IN;
                    }
                    alpha = 0.0f; // Fully invisible
            }
                case FADING_IN -> {
                    alpha = visibilityTimer / FADE_DURATION; // Linear fade in
                    if (visibilityTimer >= FADE_DURATION) {
                        visibilityTimer = 0.0f;
                        currentState = GhostState.VISIBLE;
                        alpha = 1.0f; // Ensure fully visible
                }
            }
        }
    }

    @Override
    protected TextureRegion getStandingFrame(float elapsedTime)
    {return Animations.GHOST_ENEMY_STANDING.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkUpFrame(float elapsedTime) {
        return Animations.GHOST_ENEMY_WALK_UP.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkDownFrame(float elapsedTime) {
        return Animations.GHOST_ENEMY_WALK_DOWN.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkLeftFrame(float elapsedTime) {
        return Animations.GHOST_ENEMY_WALK_LEFT.getKeyFrame(elapsedTime, true);
    }

    @Override
    protected TextureRegion getWalkRightFrame(float elapsedTime) {
        return Animations.GHOST_ENEMY_WALK_RIGHT.getKeyFrame(elapsedTime, true);
    }

    public float getAlpha()
    {
        return alpha;
    }

    public void makeVisible() {
        alpha = 1.0f;
    }
}
