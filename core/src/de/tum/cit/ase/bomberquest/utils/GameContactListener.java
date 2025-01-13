package de.tum.cit.ase.bomberquest.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import de.tum.cit.ase.bomberquest.map.*;

/**
 * A custom implementation of the ContactListener interface that manages collisions
 * between game entities in the games specific context.
 */
public class GameContactListener implements ContactListener {
    private final GameMap gameMap;

    public GameContactListener(GameMap gameMap) {
        super();

        this.gameMap = gameMap;
    }

    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        // System.out.println("Contact detected between: " + a + " and " + b);

        // If BombExplosion collides with Destructible Wall, destroy wall
        if (a instanceof DestructibleWall && b instanceof BombExplosion) {
            ((DestructibleWall) a).destroy();

        } else if (b instanceof DestructibleWall && a instanceof BombExplosion) {
            ((DestructibleWall) b).destroy();
        }

        // Player is hit by bomb
        if (a instanceof Player && b instanceof BombExplosion) {
            System.out.println("Player DIED!!!");
        } else if (b instanceof Player && a instanceof BombExplosion) {
            System.out.println("Player DIED!!!");
        }

        // Player collides with enemy
        if (a instanceof Player && b instanceof Enemy) {
            System.out.println("Player DIED!!!");
        } else if (b instanceof Player && a instanceof Enemy) {
            System.out.println("Player DIED!!!");
        }

        // If Player collides with PowerUp, collect it
        if (a instanceof Player && b instanceof PowerUp) {
            ((PowerUp) b).collect(gameMap);
        } else if (b instanceof Player && a instanceof PowerUp) {
            ((PowerUp) a).collect(gameMap);
        }

        // If Enemy collides with BombExplosion
        if (a instanceof Enemy && b instanceof BombExplosion) {
            ((Enemy) a).die();
        } else if (b instanceof Enemy && a instanceof BombExplosion) {
            ((Enemy) b).die();
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
