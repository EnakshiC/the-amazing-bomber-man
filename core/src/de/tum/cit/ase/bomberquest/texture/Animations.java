package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all animation constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These can be referenced anywhere they are needed.
 */
public class Animations {

    /**
     * The animation for the character walking down.
     * Uses frames from row 1, columns 1-4 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(1, 1),
            SpriteSheet.CHARACTER.at(1, 2),
            SpriteSheet.CHARACTER.at(1, 3),
            SpriteSheet.CHARACTER.at(1, 4)
    );

    //TODO: Extend the Animations Class: Add new animations for walking up, left, and right, using the corresponding regions from the sprite sheet.
    /**
     * The animation for the character walking up.
     * Uses frames from row 3, columns 1-4 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(3, 1),
            SpriteSheet.CHARACTER.at(3, 2),
            SpriteSheet.CHARACTER.at(3, 3),
            SpriteSheet.CHARACTER.at(3, 4)
    );

    /**
     * The animation for the character walking left.
     * Uses frames from row 4, columns 1-4 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(4, 1),
            SpriteSheet.CHARACTER.at(4, 2),
            SpriteSheet.CHARACTER.at(4, 3),
            SpriteSheet.CHARACTER.at(4, 4)
    );

    /**
     * The animation for the character walking right.
     * Uses frames from row 2, columns 1-4 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.CHARACTER.at(2, 1),
            SpriteSheet.CHARACTER.at(2, 2),
            SpriteSheet.CHARACTER.at(2, 3),
            SpriteSheet.CHARACTER.at(2, 4)
    );

    /**
     * The animation for the character standing still.
     * Uses the frame at row 1, column 1 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_STANDING = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(1, 1)
    );

    /**
     * The animation for the bomb explosion's center.
     */
    public static final Animation<TextureRegion> EXPLOSION_CENTER = new Animation<>(0.05f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(4, 1),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(4, 1),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 3)

    );
}
