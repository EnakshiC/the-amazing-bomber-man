package de.tum.cit.ase.bomberquest.map;

/**
 * Represents a power-up effect that can be applied to a game map.
 * Implementations of this interface define specific behaviors
 * that modify the state of the game map, such as changing player abilities,
 * altering game mechanics, or enhancing map attributes.
 */
public interface PowerUpEffect {
    void apply(GameMap gameMap);
}