package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Animations;

public enum BombExplosionTile {
    CENTER(Animations.EXPLOSION_CENTER),
    LEFT_END(Animations.EXPLOSION_LEFT_END),
    LEFT_MIDDLE(Animations.EXPLOSION_LEFT_MIDDLE),
    RIGHT_END(Animations.EXPLOSION_RIGHT_END),
    RIGHT_MIDDLE(Animations.EXPLOSION_RIGHT_MIDDLE),
    TOP_END(Animations.EXPLOSION_TOP_END),
    TOP_MIDDLE(Animations.EXPLOSION_TOP_MIDDLE),
    BOTTOM_END(Animations.EXPLOSION_BOTTOM_END),
    BOTTOM_MIDDLE(Animations.EXPLOSION_BOTTOM_MIDDLE);

    private final Animation<TextureRegion> animation;

    BombExplosionTile(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

}
