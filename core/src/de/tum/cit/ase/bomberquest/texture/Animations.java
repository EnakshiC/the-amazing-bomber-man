package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.map.bomb.BombExplosion;

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

    /**
     * The animation for the character dying.
     * Uses the frame at row 1, column 15 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_DYING = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(1, 15)
    );

    //Animations for Character Placing Bombs
    /**
     * The animation for the character placing bomb while walking down.
     * Uses the frame at row 1, column 6 of the sprite sheet.
     */

    public static final Animation<TextureRegion> CHARACTER_PLACING_BOMB_DOWN = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(1, 6)
    );

    /**
     * The animation for the character placing bomb while walking up.
     * Uses the frame at row 3, column 6 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_PLACING_BOMB_UP = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(3, 6)
    );

    /**
     * The animation for the character placing bomb while walking left.
     * Uses the frame at row 4, column 6 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_PLACING_BOMB_LEFT = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(4, 6)
    );

    /**
     * The animation for the character placing bomb while walking right.
     * Uses the frame at row 2, column 6 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_PLACING_BOMB_RIGHT = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(2, 6)
    );

    // Animations for Character Covering Ears
    /**
     * The animation for the character covering ears while placing bomb and walking down.
     * Uses the frames at row 1, column 7-8 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_COVER_EARS_DOWN = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(1, 7), SpriteSheet.CHARACTER.at(1, 8)
    );

    /**
     * The animation for the character covering ears while placing bomb and walking up.
     * Uses the frames at row 3, column 7-8 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_COVER_EARS_UP = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(3, 7), SpriteSheet.CHARACTER.at(3, 8)
    );

    /**
     * The animation for the character covering ears while placing bomb and walking left.
     * Uses the frames at row 4, column 7-8 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_COVER_EARS_LEFT = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(4, 7), SpriteSheet.CHARACTER.at(4, 8)
    );

    /**
     * The animation for the character covering ears while placing bomb and walking right.
     * Uses the frames at row 2, column 7-8 of the sprite sheet.
     */
    public static final Animation<TextureRegion> CHARACTER_COVER_EARS_RIGHT = new Animation<>(0.2f,
            SpriteSheet.CHARACTER.at(2, 7), SpriteSheet.CHARACTER.at(2, 8)
    );



    //Animations for Basic Enemies
    /**
     * The animation for the basic enemy walking down.
     * Uses the frame at row 1, columns 10-12 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BASIC_ENEMY_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(1, 10),
            SpriteSheet.MOBS.at(1, 11),
            SpriteSheet.MOBS.at(1, 12),
            SpriteSheet.MOBS.at(1, 11)
    );

    /**
     * The animation for the basic enemy walking up.
     * Uses the frame at row 4, columns 10-12 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BASIC_ENEMY_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(4, 10),
            SpriteSheet.MOBS.at(4, 11),
            SpriteSheet.MOBS.at(4, 12),
            SpriteSheet.MOBS.at(4, 11)
    );

    /**
     * The animation for the basic enemy walking left.
     * Uses the frame at row 2, columns 10-12 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BASIC_ENEMY_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(2, 10),
            SpriteSheet.MOBS.at(2, 11),
            SpriteSheet.MOBS.at(2, 12),
            SpriteSheet.MOBS.at(2, 11)
    );

    /**
     * The animation for the basic enemy walking right.
     * Uses the frame at row 3, columns 10-12 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BASIC_ENEMY_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(3, 10),
            SpriteSheet.MOBS.at(3, 11),
            SpriteSheet.MOBS.at(3, 12),
            SpriteSheet.MOBS.at(3, 11)
    );

    /**
     * The animation for the basic enemy standing.
     * Uses the frame at row 1, column 10 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BASIC_ENEMY_STANDING = new Animation<>(0.2f,
            SpriteSheet.MOBS.at(1, 10)
    );

    //Animations for Slimy Enemies
    /**
     * The animation for the slimy enemy walking down.
     * Uses the frame at row 5, columns 1-3 of the sprite sheet.
     */
    public static final Animation<TextureRegion> SLIMY_ENEMY_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(5, 1),
            SpriteSheet.MOBS.at(5, 2),
            SpriteSheet.MOBS.at(5, 3),
            SpriteSheet.MOBS.at(5, 1)
    );

    /**
     * The animation for the slimy enemy walking up.
     * Uses the frame at row 8, columns 1-3 of the sprite sheet.
     */
    public static final Animation<TextureRegion> SLIMY_ENEMY_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(8, 1),
            SpriteSheet.MOBS.at(8, 2),
            SpriteSheet.MOBS.at(8, 3),
            SpriteSheet.MOBS.at(8, 2)
    );

    /**
     * The animation for the slimy enemy walking left.
     * Uses the frame at row 6, columns 1-3 of the sprite sheet.
     */
    public static final Animation<TextureRegion> SLIMY_ENEMY_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(6, 1),
            SpriteSheet.MOBS.at(6, 2),
            SpriteSheet.MOBS.at(6, 3),
            SpriteSheet.MOBS.at(6, 2)
    );

    /**
     * The animation for the slimy enemy walking right.
     * Uses the frame at row 7, columns 1-3 of the sprite sheet.
     */
    public static final Animation<TextureRegion> SLIMY_ENEMY_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(7, 1),
            SpriteSheet.MOBS.at(7, 2),
            SpriteSheet.MOBS.at(7, 3),
            SpriteSheet.MOBS.at(7, 2)
    );

    /**
     * The animation for the slimy enemy standing.
     * Uses the frame at row 4, column 1 of the sprite sheet.
     */
    public static final Animation<TextureRegion> SLIMY_ENEMY_STANDING = new Animation<>(0.2f,
            SpriteSheet.MOBS.at(4, 1)
    );

    //Animations for Bats
    /**
     * The animation for the bats walking down.
     * Uses the frame at row 5, columns 4-6 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BAT_ENEMY_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(5, 4),
            SpriteSheet.MOBS.at(5, 5),
            SpriteSheet.MOBS.at(5, 6),
            SpriteSheet.MOBS.at(5, 5)
    );

    /**
     * The animation for the bats walking up.
     * Uses the frame at row 8, columns 4-6 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BAT_ENEMY_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(8, 4),
            SpriteSheet.MOBS.at(8, 5),
            SpriteSheet.MOBS.at(8, 6),
            SpriteSheet.MOBS.at(8, 5)
    );

    /**
     * The animation for the bats walking left.
     * Uses the frame at row 6, columns 4-6 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BAT_ENEMY_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(6, 4),
            SpriteSheet.MOBS.at(6, 5),
            SpriteSheet.MOBS.at(6, 6),
            SpriteSheet.MOBS.at(6, 5)
    );

    /**
     * The animation for the bats walking right.
     * Uses the frame at row 7, columns 4-6 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BAT_ENEMY_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(7, 4),
            SpriteSheet.MOBS.at(7, 5),
            SpriteSheet.MOBS.at(7, 6),
            SpriteSheet.MOBS.at(7, 5)
    );

    /**
     * The animation for the bats standing.
     * Uses the frame at row 5, column 4 of the sprite sheet.
     */
    public static final Animation<TextureRegion> BAT_ENEMY_STANDING = new Animation<>(0.2f,
            SpriteSheet.MOBS.at(5, 4)
    );


    //Animations for Ghosts
    /**
     * The animation for the ghosts walking down.
     * Uses the frame at row 5, columns 7-9 of the sprite sheet.
     */
    public static final Animation<TextureRegion> GHOST_ENEMY_WALK_DOWN = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(5, 7),
            SpriteSheet.MOBS.at(5, 8),
            SpriteSheet.MOBS.at(5, 9),
            SpriteSheet.MOBS.at(5, 8)
    );

    /**
     * The animation for the ghosts walking up.
     * Uses the frame at row 8, columns 7-9 of the sprite sheet.
     */
    public static final Animation<TextureRegion> GHOST_ENEMY_WALK_UP = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(8, 7),
            SpriteSheet.MOBS.at(8, 8),
            SpriteSheet.MOBS.at(8, 9),
            SpriteSheet.MOBS.at(8, 8)
            );

    /**
     * The animation for the ghosts walking left.
     * Uses the frame at row 6, columns 7-9 of the sprite sheet.
     */
    public static final Animation<TextureRegion> GHOST_ENEMY_WALK_LEFT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(6, 7),
            SpriteSheet.MOBS.at(6, 8),
            SpriteSheet.MOBS.at(6, 9),
            SpriteSheet.MOBS.at(6, 8)
    );

    /**
     * The animation for the ghosts walking right.
     * Uses the frame at row 7, columns 7-9 of the sprite sheet.
     */
    public static final Animation<TextureRegion> GHOST_ENEMY_WALK_RIGHT = new Animation<>(0.1f,
            SpriteSheet.MOBS.at(7, 7),
            SpriteSheet.MOBS.at(7, 8),
            SpriteSheet.MOBS.at(7, 9),
            SpriteSheet.MOBS.at(7, 8)
    );

    /**
     * The animation for the ghosts standing.
     * Uses the frame at row 4, column 7 of the sprite sheet.
     */
    public static final Animation<TextureRegion>GHOST_ENEMY_STANDING=new Animation<>(0.2f,
            SpriteSheet.MOBS.at(4, 7)
            );

    //Animations for Enemies Dying (Smoke and Explosions)
    /**
     * The animation for the smoke appearing after enemy dying.
     * Uses the frame at row 1, columns 1-7 of the sprite sheet.
     */
    public static final Animation<TextureRegion> ENEMY_DYING = new Animation<>(0.1f,
            SpriteSheet.SMOKE.at(1, 1),
            SpriteSheet.SMOKE.at(1, 2),
            SpriteSheet.SMOKE.at(1, 3),
            SpriteSheet.SMOKE.at(1, 4),
            SpriteSheet.SMOKE.at(1, 5),
            SpriteSheet.SMOKE.at(1, 6),
            SpriteSheet.SMOKE.at(1, 7)
    );

    /**
     * The animation for the bomb explosion centre.
     * Uses the frame at row 7,12 columns 3,8 of the sprite sheet.
     */
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

    /**
     * The animation for the bomb explosion left middle.
     * Uses the frame at row 7,12 columns 2,7 of the sprite sheet.
     */
    public static final Animation<TextureRegion> EXPLOSION_LEFT_MIDDLE = new Animation<>(BombExplosion.EXPLOSION_DURATION / 10f,
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

    /**
     * The animation for the bomb explosion left.
     * Uses the frame at row 7,12 columns 1,6 of the sprite sheet.
     */
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

    /**
     * The animation for the bomb explosion right middle.
     * Uses the frame at row 7,12 columns 4,9 of the sprite sheet.
     */
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

    /**
     * The animation for the bomb explosion right end.
     * Uses the frame at row 7,12 columns 5,10 of the sprite sheet.
     */
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

    /**
     * The animation for the bomb explosion top middle.
     * Uses the frame at row 6,11 columns 3,8 of the sprite sheet.
     */
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

    /**
     * The animation for the bomb explosion top end.
     * Uses the frame at row 5,10 columns 3,8 of the sprite sheet.
     */
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

    /**
     * The animation for the bomb explosion bottom middle.
     * Uses the frame at row 8,13 columns 3,8 of the sprite sheet.
     */
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

    /**
     * The animation for the bomb explosion bottom end.
     * Uses the frame at row 9,14 columns 3,8 of the sprite sheet.
     */
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
