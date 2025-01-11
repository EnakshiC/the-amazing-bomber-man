package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

import java.util.List;

// TODO: Implement Bomb
public class Bomb implements Drawable {

    /** The time it takes for the bomb to explode after it has been placed. */
    public static final float FUSE_TIME = 3.0f;


    private final float x;
    private final float y;

    /**
     * Tracks the time elapsed since the bomb has been placed, measured in seconds.
     * It is incremented in each game tick with the frame's elapsed time.
     * When the value reaches or exceeds the predefined fuse duration, the bomb explodes.
     */
    private float fuseTimer = 0.0f;
    private final Runnable onExplode;

    /** When adding itself to this list, the bomb will be removed next tick cycle. */
    private final List<Bomb> bombsToBeRemovedNextCycle;

    public Bomb(float x, float y, Runnable onExplode, List<Bomb> bombsToBeRemovedNextCycle) {
        this.x = x;
        this.y = y;

        this.onExplode = onExplode;
        this.bombsToBeRemovedNextCycle = bombsToBeRemovedNextCycle;
    }

    public void tick(float frameTime) {
        fuseTimer += frameTime;
        if (fuseTimer >= FUSE_TIME) {
            fuseTimer = 0.0f;

            if (onExplode != null) {
                onExplode.run();
            }

            if (bombsToBeRemovedNextCycle != null) {
                bombsToBeRemovedNextCycle.add(this);
            }

        }
    }
    
    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.BOMB;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }
}
