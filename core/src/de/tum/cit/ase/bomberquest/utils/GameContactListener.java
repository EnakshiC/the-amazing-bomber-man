package de.tum.cit.ase.bomberquest.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import de.tum.cit.ase.bomberquest.map.DestructibleWall;
import de.tum.cit.ase.bomberquest.map.Player;

/**
 * A custom implementation of the ContactListener interface that manages collisions
 * between game entities in the games specific context.
 */
public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if (a instanceof DestructibleWall && b instanceof Player) {
            ((DestructibleWall) a).destroy();
        } else if (b instanceof DestructibleWall && a instanceof Player) {
            ((DestructibleWall) b).destroy();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
