package de.tum.cit.ase.bomberquest.texture;

import com.badlogic.gdx.physics.box2d.World;

public interface Destroyable {
    void destroyBody(World world);
}
