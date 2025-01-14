package de.tum.cit.ase.bomberquest.map;

import de.tum.cit.ase.bomberquest.texture.Drawable;

import java.util.List;


/**
 * Represents an abstract drawable element in the game with a limited lifespan.
 * Once the duration specified by the time-to-live value elapses, the element
 * self-removes by adding itself to a specified kill list.
 *
 * This class is intended to be extended by other classes that require drawable
 * elements with a finite lifespan and automated lifecycle management.
 */
public abstract class SelfRemovingElement implements Drawable {
    private final float timeToLive;
    private float timeSinceCreation;

    /**
     * A list that holds drawable objects scheduled for removal from the game.
     * Once a drawable element's time-to-live exceeds its limit or other removal
     * conditions are met, it can be added to this list.
     */
    private final List<Drawable> objectsToBeRemovedNextCycle;

    public SelfRemovingElement(float timeToLive, List<Drawable> objectsToBeRemovedNextCycle) {
        this.timeToLive = timeToLive;
        this.timeSinceCreation = 0.0f;
        this.objectsToBeRemovedNextCycle = objectsToBeRemovedNextCycle;
    }

    public void tick(float frameTime) {
        timeSinceCreation += frameTime;
        if (timeSinceCreation >= timeToLive) {
            timeSinceCreation = 0.0f;

            if (objectsToBeRemovedNextCycle != null) {
                objectsToBeRemovedNextCycle.add(this);
            }
        }
    }

    public float getTimeSinceCreation() {
        return timeSinceCreation;
    }
}
