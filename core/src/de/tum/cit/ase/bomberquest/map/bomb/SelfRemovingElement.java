package de.tum.cit.ase.bomberquest.map.bomb;

import de.tum.cit.ase.bomberquest.texture.Drawable;

import java.util.List;


/**
 * Represents an abstract drawable element in the game with a limited lifespan.
 * Once the duration specified by the time-to-live value elapses, the element
 * self-removes by adding itself to a specified kill list.
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

    /**
     * Constructs a SelfRemovingElement with a specified time-to-live duration and a list of objects
     * to be removed in the next update cycle.
     *
     * @param timeToLive The duration in seconds after which the element will add itself to the removal list.
     * @param objectsToBeRemovedNextCycle A list of {@link Drawable} objects that are marked for removal
     *                                    in the subsequent cycle. The current object will be added to
     *                                    this list when the time-to-live elapses.
     */
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
