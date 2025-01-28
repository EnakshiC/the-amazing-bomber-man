package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all texture constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These can be referenced anywhere they are needed.
 */
public class Textures {
    
    public static final TextureRegion EMPTY = SpriteSheet.BASIC_TILES.at(11, 3);

    public static final TextureRegion RED = SpriteSheet.BASIC_TILES.at(2, 2);

    public static final TextureRegion EXIT_CLOSE = SpriteSheet.ORIGINAL_BOMBERMAN.at(4, 12);

    public static final TextureRegion EXIT_OPEN = SpriteSheet.BASIC_TILES.at(3, 7);

    public static final TextureRegion PATH = SpriteSheet.BASIC_TILES.at(2, 5);

    public static final TextureRegion DESTRUCTIBLE_WALL = SpriteSheet.BASIC_TILES.at(3, 1);

    public static final TextureRegion INDESTRUCTIBLE_WALL = SpriteSheet.BASIC_TILES.at(2, 7);

    public static final TextureRegion POWER_UP_BOMB_RADIUS = SpriteSheet.ORIGINAL_BOMBERMAN.at(15, 7);

    public static final TextureRegion POWER_UP_CONCURRENT_BOMBS = SpriteSheet.ORIGINAL_BOMBERMAN.at(15, 5);

    public static final TextureRegion POWER_UP_EXTRA_TIME = SpriteSheet.ORIGINAL_BOMBERMAN.at(15, 2);

    public static final TextureRegion POWER_UP_EXTRA_SPEED = SpriteSheet.ORIGINAL_BOMBERMAN.at(15, 4);

    public static final TextureRegion FOG_CENTER = SpriteSheet.FOG.at(1, 1);
}
