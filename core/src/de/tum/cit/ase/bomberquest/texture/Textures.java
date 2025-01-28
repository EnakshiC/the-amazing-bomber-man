package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Contains all texture constants used in the game.
 * It is good practice to keep all textures and animations in constants to avoid loading them multiple times.
 * These can be referenced anywhere they are needed.
 */
public class Textures {

    /** Represents an empty tile with flower background with no visual content. */
    public static final TextureRegion EMPTY = SpriteSheet.BASIC_TILES.at(11, 3);

    public static final TextureRegion RED = SpriteSheet.BASIC_TILES.at(2, 2);

    /** Represents the closed exit texture. */
    public static final TextureRegion EXIT_CLOSE = SpriteSheet.ORIGINAL_BOMBERMAN.at(4, 12);

    /** Represents the open exit texture. */
    public static final TextureRegion EXIT_OPEN = SpriteSheet.BASIC_TILES.at(3, 7);

    /** Represents the texture for a standard walkable path. */
    public static final TextureRegion PATH = SpriteSheet.BASIC_TILES.at(2, 5);

    /** Represents the texture for a destructible wall. */
    public static final TextureRegion DESTRUCTIBLE_WALL = SpriteSheet.BASIC_TILES.at(3, 1);

    /** Represents the texture for an indestructible wall. */
    public static final TextureRegion INDESTRUCTIBLE_WALL = SpriteSheet.BASIC_TILES.at(2, 7);

    /** Represents the texture for the "Bomb Radius" power-up. */
    public static final TextureRegion POWER_UP_BOMB_RADIUS = SpriteSheet.ORIGINAL_BOMBERMAN.at(15, 7);

    /** Represents the texture for the "Concurrent Bombs" power-up. */
    public static final TextureRegion POWER_UP_CONCURRENT_BOMBS = SpriteSheet.ORIGINAL_BOMBERMAN.at(15, 5);

    /** Represents the texture for the "Extra Time" power-up. */
    public static final TextureRegion POWER_UP_EXTRA_TIME = SpriteSheet.ORIGINAL_BOMBERMAN.at(15, 2);

    /** Represents the texture for the "Extra Speed" power-up. */
    public static final TextureRegion POWER_UP_EXTRA_SPEED = SpriteSheet.ORIGINAL_BOMBERMAN.at(15, 4);

    public static final TextureRegion FOG_CENTER = SpriteSheet.FOG.at(1, 1);
}
