package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

/**
 * Represents a path texture drawable in the game world.
 * A path is a non-interactive element that provides visual details
 * and serves as a decorative feature in the game grid.
 */
public record Path(float x, float y) implements Drawable {
    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.PATH;
    }
}
