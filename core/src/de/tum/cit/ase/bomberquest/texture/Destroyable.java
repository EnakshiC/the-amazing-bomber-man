package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.physics.box2d.World;

/**
 * An interface representing entities that have a physical body which can
 * be destroyed in a game world. Implementing classes are responsible for
 * defining how their respective bodies should be removed from the world.
 */
public interface Destroyable {
    void destroyBody(World world);
}
