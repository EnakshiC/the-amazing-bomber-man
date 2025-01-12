package de.tum.cit.ase.bomberquest.utils;

import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.map.BombExplosion;
import de.tum.cit.ase.bomberquest.map.Player;
import de.tum.cit.ase.bomberquest.map.PowerUp;
import de.tum.cit.ase.bomberquest.map.Wall;
import de.tum.cit.ase.bomberquest.texture.Drawable;

/**
 * Utility class for managing hitboxes and collision filtering in a Box2D physics environment.
 * Provides methods to create different types of hitboxes and configure collision filtering
 * using category and mask bits based on Drawable types.
 */
public class HitboxHelper {
    /**
     * Determines the category bits used for collision filtering based on the type of the given Drawable object.
     * The category bits categorize objects into specific types to define their collision behavior.
     *
     * @param drawable The Drawable object for which the category bits are determined.
     * @return The category bits representing the type of the Drawable. Returns 0x0000 if no matching category exists.
     */
    public static short getCategoryBits(Drawable drawable) {
        // TODO: Add Enemies
        if (drawable instanceof Player) {
            return 0x0001;
        } else if (drawable instanceof Wall) {
            return 0x0004;
//        } else if (drawable instanceof Enemy) {
//            return 0x0002;
        }
        else if (drawable instanceof BombExplosion) {
            return 0x0008;
        } else if (drawable instanceof PowerUp) {
            return 0x0016;
        } else {
            // System.err.println("No category bit matches for " + drawable);
            return 0x0000;
        }
    }

    /**
     * Determines the mask bits used for collision filtering based on the type of the given Drawable object.
     * The mask bits define which category of objects the Drawable can collide with.
     *
     * @param drawable The Drawable object for which the mask bits are determined.
     * @return The mask bits representing the collision behavior of the Drawable.
     *         Returns 0x0099 if no matching mask configuration exists.
     */
    public static short getMaskBits(Drawable drawable) {
        if (drawable instanceof Player) {
            return 0x0002 | 0x0008 | 0x0004 | 0x0016;
        } else if (drawable instanceof Wall) {
            return 0x0001 | 0x0008;
        } else if (drawable instanceof BombExplosion) {
            return 0x0001 | 0x0004;
        } else if (drawable instanceof PowerUp) {
            return 0x0001;
        } else {
            // System.err.println("No category bit matches for " + drawable);
            return 0x0099;
        }
    }


    /**
     * Creates a polygon-shaped hitbox as a Box2D body with specified properties and configurations.
     * This method defines the physical behavior and collision settings for the body in the Box2D physics world.
     *
     * @param world The Box2D world where the body will be added.
     * @param x The X coordinate for the initial position of the body.
     * @param y The Y coordinate for the initial position of the body.
     * @param drawable The drawable object associated with the body, used to configure collision filtering.
     * @param isDynamicBody Determines if the body is dynamic (true) or static (false).
     * @return The created Box2D body configured as a polygon hitbox.
     */
    public static Body createPolygonHitbox(World world, float x, float y, Drawable drawable, boolean isDynamicBody) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isDynamicBody ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        // The hitbox of Walls and BombExplosion are just below .5f
        // This way BombExplosions that "fly" just by a wall do not trigger the DestructibleWalls next to it to vanish, too.
        box.setAsBox(0.49f, 0.49f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.isSensor = false;

        // Giving the current body a category bit and mask bits
        // It only collides with bodies that have a category bit listed in the mask bits
        fixtureDef.filter.categoryBits = getCategoryBits(drawable);
        fixtureDef.filter.maskBits = getMaskBits(drawable);

        body.createFixture(fixtureDef);
        box.dispose();

        body.setUserData(drawable);
        return body;
    }

    public static Body createPolygonHitbox(World world, float x, float y, Drawable drawable) {
        return createPolygonHitbox(world, x, y, drawable, false);
    }


    /**
     * Creates a circle-shaped hitbox as a Box2D body and configures it with the specified properties.
     * This method sets up the physical behavior and collision settings for the body in the Box2D physics world.
     * The circle hitbox is associated with a Drawable object, which is used to configure collision filtering.
     *
     * @param world The Box2D world where the body will be added.
     * @param startX The X coordinate for the initial position of the body.
     * @param startY The Y coordinate for the initial position of the body.
     * @param drawable The drawable object associated with the body, used to configure collision filtering.
     * @return The created Box2D body configured as a circle hitbox.
     */
    public static Body createCircleHitbox(World world, float startX, float startY, Drawable drawable) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(startX, startY);

        Body body = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(0.3f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.isSensor = false;

        fixtureDef.filter.categoryBits = getCategoryBits(drawable);
        fixtureDef.filter.maskBits = getMaskBits(drawable);

        body.createFixture(fixtureDef);

        circle.dispose();
        // Set the player as the user data of the body so we can look up the player from the body later.
        body.setUserData(drawable);
        return body;
    }
}
