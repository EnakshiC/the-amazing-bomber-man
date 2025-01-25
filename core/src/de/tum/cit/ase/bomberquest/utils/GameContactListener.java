package de.tum.cit.ase.bomberquest.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import de.tum.cit.ase.bomberquest.map.*;
import de.tum.cit.ase.bomberquest.map.basic_tiles.DestructibleWall;
import de.tum.cit.ase.bomberquest.map.basic_tiles.Wall;
import de.tum.cit.ase.bomberquest.map.bomb.BombExplosion;
import de.tum.cit.ase.bomberquest.map.enemies.Enemy;
import de.tum.cit.ase.bomberquest.map.power_up.PowerUp;

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

        // If BombExplosion collides with Destructible Wall, destroy wall
        if (a instanceof DestructibleWall && b instanceof BombExplosion) {
            ((DestructibleWall) a).destroy();

        } else if (b instanceof DestructibleWall && a instanceof BombExplosion) {
            ((DestructibleWall) b).destroy();
        }

        if (a instanceof Player && b instanceof BombExplosion) {
            gameMap.endGame(false);
        } else if (b instanceof Player && a instanceof BombExplosion) {
            gameMap.endGame(false);
        }

        if (a instanceof Player && b instanceof Enemy) {
            gameMap.endGame(false);
        } else if (b instanceof Player && a instanceof Enemy) {
            gameMap.endGame(false);
        }

        // If Player collides with PowerUp, collect it
        if (a instanceof Player && b instanceof PowerUp) {
            ((PowerUp) b).collect(gameMap);
        } else if (b instanceof Player && a instanceof PowerUp) {
            ((PowerUp) a).collect(gameMap);
        }

        // If Enemy collides with BombExplosion, kill enemy
        if (a instanceof Enemy && b instanceof BombExplosion) {
            ((Enemy) a).kill();
        } else if (b instanceof Enemy && a instanceof BombExplosion) {
            ((Enemy) b).kill();
        }

        // If Enemy collides with Wall (which it should not), trigger 'emergency' turn around.
        if (a instanceof Enemy && b instanceof Wall) {
            ((Enemy) a).turnAround();
        } else if (b instanceof Enemy && a instanceof Wall) {
            ((Enemy) b).turnAround();
        }

        // If Player collides with Exit and Exit is open, game is won
        if (a instanceof Exit && b instanceof Player) {
            if (((Exit) a).isOpen()) {
                gameMap.endGame(true);
            }
        } else if (b instanceof Exit && a instanceof Player) {
            if (((Exit) b).isOpen()) {
                gameMap.endGame(true);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
