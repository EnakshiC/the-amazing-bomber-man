package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;

// TODO: Implement Bomb
public class Bomb implements Drawable {

    /** The time it takes for the bomb to explode after it has been placed. */
    public static final float FUSE_TIME = 3.0f;


    private final float x;
    private final float y;

    public Bomb(World world, float x, float y) {
        this.x = x;
        this.y = y;
        createHitbox(world);
    }

    private void createHitbox(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(this.x, this.y);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
    }
    
    @Override
    public TextureRegion getCurrentAppearance() {
        return Textures.BOMB;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    public void onContact(Object other) {
        if (other instanceof Player) {
            System.out.println("Hit!");
        }
    }
}
