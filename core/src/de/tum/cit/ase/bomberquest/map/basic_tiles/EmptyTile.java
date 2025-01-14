package de.tum.cit.ase.bomberquest.map.basic_tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents an empty tile in the game world.
 * The EmptyTile is a non-collidable, transparent object that is mainly used to fill
 * gaps between walls in the List<List<Drawable>> of wall in the map.
 */
public record EmptyTile(float x, float y) implements Drawable {

    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.EMPTY;
    }
}