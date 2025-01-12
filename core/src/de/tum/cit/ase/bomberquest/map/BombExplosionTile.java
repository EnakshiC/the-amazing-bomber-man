package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Animations;

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

    BombExplosionTile(Animation<TextureRegion> animation, String direction, boolean isEnd) {
        this.animation = animation;
        this.direction = direction;
        this.isEnd = isEnd;
    }

    public static BombExplosionTile getByDirectionAndEnd(String direction, boolean isEnd) {
        for (BombExplosionTile tile : BombExplosionTile.values()) {
            if (tile.getDirection().equals(direction) && tile.isEnd() == isEnd) {
                return tile;
            }
        }

        System.err.println("No matching BombExplosionTile found for direction: " + direction + " and end: " + isEnd + ".");

        return BombExplosionTile.CENTER;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public String getDirection() {
        return direction;
    }

    public boolean isEnd() {
        return isEnd;
    }

}
