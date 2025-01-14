package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.map.BombExplosion;

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

    public static final Animation<TextureRegion> BASIC_ENEMY_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(1, 10),
            SpriteSheet.MOBS.at(1, 11),
            SpriteSheet.MOBS.at(1, 12),
            SpriteSheet.MOBS.at(1, 11)
    );

    public static final Animation<TextureRegion> BASIC_ENEMY_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(4, 10),
            SpriteSheet.MOBS.at(4, 11),
            SpriteSheet.MOBS.at(4, 12),
            SpriteSheet.MOBS.at(4, 11)
    );

    public static final Animation<TextureRegion> BASIC_ENEMY_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(2, 10),
            SpriteSheet.MOBS.at(2, 11),
            SpriteSheet.MOBS.at(2, 12),
            SpriteSheet.MOBS.at(2, 11)
    );

    public static final Animation<TextureRegion> BASIC_ENEMY_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(3, 10),
            SpriteSheet.MOBS.at(3, 11),
            SpriteSheet.MOBS.at(3, 12),
            SpriteSheet.MOBS.at(3, 11)
    );

    public static final Animation<TextureRegion> BASIC_ENEMY_STANDING = new Animation<>(0.2f,
            SpriteSheet.MOBS.at(1, 10)
    );

    public static final Animation<TextureRegion> SLIMY_ENEMY_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(5, 1),
            SpriteSheet.MOBS.at(5, 2),
            SpriteSheet.MOBS.at(5, 3),
            SpriteSheet.MOBS.at(5, 1)
    );

    public static final Animation<TextureRegion> SLIMY_ENEMY_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(8, 1),
            SpriteSheet.MOBS.at(8, 2),
            SpriteSheet.MOBS.at(8, 3),
            SpriteSheet.MOBS.at(8, 2)
    );

    public static final Animation<TextureRegion> SLIMY_ENEMY_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(6, 1),
            SpriteSheet.MOBS.at(6, 2),
            SpriteSheet.MOBS.at(6, 3),
            SpriteSheet.MOBS.at(6, 2)
    );

    public static final Animation<TextureRegion> SLIMY_ENEMY_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(7, 1),
            SpriteSheet.MOBS.at(7, 2),
            SpriteSheet.MOBS.at(7, 3),
            SpriteSheet.MOBS.at(7, 2)
    );

    public static final Animation<TextureRegion> SLIMY_ENEMY_STANDING = new Animation<>(0.2f,
            SpriteSheet.MOBS.at(4, 1)
    );

    public static final Animation<TextureRegion> ENEMY_DYING = new Animation<>(0.1f,
            SpriteSheet.SMOKE.at(1, 1),
            SpriteSheet.SMOKE.at(1, 2),
            SpriteSheet.SMOKE.at(1, 3),
            SpriteSheet.SMOKE.at(1, 4),
            SpriteSheet.SMOKE.at(1, 5),
            SpriteSheet.SMOKE.at(1, 6),
            SpriteSheet.SMOKE.at(1, 7)
    );

    public static final Animation<TextureRegion> EXPLOSION_CENTER = new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 3)
    );

    public static final Animation<TextureRegion> EXPLOSION_LEFT_MIDDLE= new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 2),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 7),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 2),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 7),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 7),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 7),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 7),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 2),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 7),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 2)
    );

    public static final Animation<TextureRegion> EXPLOSION_LEFT_END = new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 1),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 6),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 1),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 6),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 6),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 6),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 6),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 1),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 6),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 1)
    );

    public static final Animation<TextureRegion> EXPLOSION_RIGHT_MIDDLE = new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 4),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 9),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 4),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 9),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 9),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 9),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 9),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 4),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 9),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 4)
    );

    public static final Animation<TextureRegion> EXPLOSION_RIGHT_END = new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 5),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 10),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 5),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 10),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 10),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 10),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 10),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(12, 5),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 10),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(7, 5)
    );

    public static final Animation<TextureRegion> EXPLOSION_TOP_MIDDLE = new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(6, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(6, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(11, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(11, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(11, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(11, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(11, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(11, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(6, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(6, 3)

    );

    public static final Animation<TextureRegion> EXPLOSION_TOP_END = new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(5, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(5, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(10, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(10, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(10, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(10, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(10, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(10, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(5, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(5, 3)
    );

    public static final Animation<TextureRegion> EXPLOSION_BOTTOM_MIDDLE = new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(8, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(8, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(13, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(13, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(13, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(13, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(13, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(13, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(8, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(8, 3)
    );

    public static final Animation<TextureRegion> EXPLOSION_BOTTOM_END = new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
            SpriteSheet.ORIGINAL_BOMBERMAN.at(9, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(9, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(14, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(14, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(14, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(14, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(14, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(14, 3),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(9, 8),
            SpriteSheet.ORIGINAL_BOMBERMAN.at(9, 3)
    );
}
