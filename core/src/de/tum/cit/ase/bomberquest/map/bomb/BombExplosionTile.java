package de.tum.cit.ase.bomberquest.map.bomb;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Animations;

/**
 * Represents all the tiles of a bomb explosion in the grid-based game. Each tile corresponds
 * to a specific position (direction) relative to the explosion's center or a specific
 * type within the explosion pattern. The tiles can be categorized as either middle or
 * end parts of the explosion, moving in different directions.
*/
public enum BombExplosionTile {
    CENTER(Animations.EXPLOSION_CENTER, "0,0", false),
    LEFT_END(Animations.EXPLOSION_LEFT_END, "-1,0", true),
    LEFT_MIDDLE(Animations.EXPLOSION_LEFT_MIDDLE, "-1,0", false),
    RIGHT_END(Animations.EXPLOSION_RIGHT_END, "1,0", true),
    RIGHT_MIDDLE(Animations.EXPLOSION_RIGHT_MIDDLE, "1,0", false),
    TOP_END(Animations.EXPLOSION_TOP_END, "0,1", true),
    TOP_MIDDLE(Animations.EXPLOSION_TOP_MIDDLE, "0,1", false),
    BOTTOM_END(Animations.EXPLOSION_BOTTOM_END, "0,-1", true),
    BOTTOM_MIDDLE(Animations.EXPLOSION_BOTTOM_MIDDLE, "0,-1", false);

    private final Animation<TextureRegion> animation;

    /** Indicating the direction from the center in a vector form, e.g. 1,0 or 0,-1 */
    private final String direction;

    /** Indicating weather this is the end of the explosion line or not */
    private final boolean isEnd;

    /**
     * Constructs a BombExplosionTile representing a specific tile of a bomb explosion in the
     * game, detailing its animation, direction, and whether it marks the end of an
     * explosion line.
     *
     * @param animation The animation for the explosion tile.
     * @param direction The direction vector indicating the position relative to the
     *                  center of the explosion.
     * @param isEnd     Indicates whether this tile is the end of an explosion branch.
     */
    BombExplosionTile(Animation<TextureRegion> animation, String direction, boolean isEnd) {
        this.animation = animation;
        this.direction = direction;
        this.isEnd = isEnd;
    }

    /**
     * Retrieves a {@code BombExplosionTile} based on the specified direction and whether it is the
     * end of an explosion line. If no matching tile is found, it defaults to returning {@code CENTER}.
     *
     * @param direction The direction vector as a string (e.g., "1,0" or "0,-1"), indicating the
     *                  position relative to the center of the explosion.
     * @param isEnd     A boolean value indicating whether the desired tile should be the end
     *                  of an explosion line.
     * @return The {@code BombExplosionTile} that matches the specified direction and end status.
     *         If no match is found, returns {@code CENTER}.
     */
    public static BombExplosionTile getByDirectionAndEnd(String direction, boolean isEnd) {
        for (BombExplosionTile tile : BombExplosionTile.values()) {
            if (tile.getDirection().equals(direction) && tile.isEnd() == isEnd) {
                return tile;
            }
        }

        System.err.println("No matching BombExplosionTile found for direction: " + direction + " and end: " + isEnd + ".");

        return BombExplosionTile.CENTER;
    }

    /**
     * Retrieves animations for associated explosion tiles
     * @return Animation for this tile
     */
    public Animation<TextureRegion> getAnimation() {
        return animation;
    }


    /**
     * Retrieves vector direction of explosion tiles relative to centre tile
     * @return
     */
    public String getDirection() {
        return direction;
    }


    /**
     * Checks if this tile represents the end of an explosion branch.
     * @return
     */
    public boolean isEnd() {
        return isEnd;
    }
}
