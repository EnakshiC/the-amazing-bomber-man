package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.audio.SoundEffect;
import de.tum.cit.ase.bomberquest.map.basic_tiles.IndestructibleWall;
import de.tum.cit.ase.bomberquest.map.bomb.Bomb;
import de.tum.cit.ase.bomberquest.map.bomb.BombExplosion;
import de.tum.cit.ase.bomberquest.map.bomb.BombExplosionTile;
import de.tum.cit.ase.bomberquest.map.enemies.Enemy;
import de.tum.cit.ase.bomberquest.map.enemies.EnemySmartSearcher;
import de.tum.cit.ase.bomberquest.map.power_up.PowerUp;
import de.tum.cit.ase.bomberquest.texture.Destroyable;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.GameContactListener;
import de.tum.cit.ase.bomberquest.utils.PropertiesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game map and manages all entities and interactions on the map,
 * such as the player, bombs, and static elements. It integrates with the Box2D
 * physics engine for realistic simulation of physics interactions.
 */
public class GameMap {

    // A static block is executed once when the class is referenced for the first time.
    static {
        // Initialize the Box2D physics engine.
        com.badlogic.gdx.physics.box2d.Box2D.init();
    }

    // Box2D physics simulation parameters (you can experiment with these if you want, but they work well as they are)
    /**
     * The time step for the physics simulation.
     * This is the amount of time that the physics simulation advances by in each frame.
     * It is set to 1/refreshRate, where refreshRate is the refresh rate of the monitor, e.g., 1/60 for 60 Hz.
     */
    private static final float TIME_STEP = 1f / Gdx.graphics.getDisplayMode().refreshRate;
    /**
     * The number of velocity iterations for the physics simulation.
     */
    private static final int VELOCITY_ITERATIONS = 6;
    /**
     * The number of position iterations for the physics simulation.
     */
    private static final int POSITION_ITERATIONS = 2;
    /**
     * The accumulated time since the last physics step.
     * We use this to keep the physics simulation at a constant rate even if the frame rate is variable.
     */
    private float physicsTime = 0;

    /**
     * The game, in case the map needs to access it.
     */
    private final BomberQuestGame game;

    /**
     * The Box2D world for physics simulation.
     */
    private final World world;

    // Game objects
    private final Player player;
    private final Exit exit;
    private final List<Bomb> bombsInPlay = new ArrayList<>();
    private final List<BombExplosion> explosionTiles = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<List<Drawable>> wallElements;
    private final List<PowerUp> powerUps = new ArrayList<>();

    /**
     * Objects in this list will be removed from World/Canvas next tick cycle
     */
    private final List<Drawable> objectsToRemoveNextCycle = new ArrayList<>();

    // Game progress values
    private final int enemiesCountAtBeginning;
    private int maxBombsAllowed = 1;
    private int bombRadius = 1;
    private float timeLeft = 300.0f;

    private boolean gameIsOver = false;
    private boolean gameWasWon = false;
    private float gameOverFadeOutTime = 0.0f;

    public GameMap(BomberQuestGame game) {
        this.game = game;

        this.world = new World(Vector2.Zero, true);

        // The contact listener handles all collisions in the game
        this.world.setContactListener(new GameContactListener(this));

        // Create a player with initial position (1, 3)
        this.player = new Player(this.world, PropertiesHelper.getPlayerEntranceX(), PropertiesHelper.getPlayerEntranceY());

        // Load all enemies and store initial count
        // this.enemies.addAll(PropertiesHelper.loadEnemiesFromProperties(world, this));
        enemies.add(new EnemySmartSearcher(world, 10, 5, this));
        this.enemiesCountAtBeginning = enemies.size();

        // Load exit
        this.exit = new Exit(world, PropertiesHelper.getExitX(), PropertiesHelper.getExitY(), this);

        // Load all power-ups
        powerUps.addAll(PropertiesHelper.loadPowerUpsFromProperties(world, objectsToRemoveNextCycle));

        // Load all wall elements
        this.wallElements = new ArrayList<>(PropertiesHelper.loadWallsFromProperties(world, objectsToRemoveNextCycle));
    }

    /**
     * Updates the game state. This is called once per frame.
     * Every dynamic object in the game should update its state here.
     *
     * @param frameTime the time that has passed since the last update
     */
    public void tick(float frameTime) {
        // No more updates if the game is over
        //
        if (gameIsOver) {
            gameOverFadeOutTime += frameTime;
            endGame(gameWasWon);
            return;
        }

        // Handle the timer of the current game
        timeLeft -= frameTime;
        if (timeLeft <= 0) {
            gameIsOver = true;
            gameWasWon = false;
            timeLeft = 0.0f;
        }

        // Pressing SPACE car drops a bomb
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) dropBomb();

        // Update the tick methods of all time sensitive classes
        for (Bomb b : bombsInPlay) b.tick(frameTime);
        for (BombExplosion e : explosionTiles) e.tick(frameTime);
        for (Enemy enemy : enemies) enemy.tick(frameTime);
        this.player.tick(frameTime);

        // Update the world's physics engine
        doPhysicsStep(frameTime);

        // Handle all objects that are marked to be removed next cycle
        for (Drawable object : objectsToRemoveNextCycle) {
            // Remove the bodies from the world before the object is removed
            if (object instanceof Destroyable) ((Destroyable) object).destroyBody(world);

            // Trigger bomb explosion and remove bombs that are due
            if (object instanceof Bomb) {
                explodeBomb((Bomb) object);
                bombsInPlay.remove((Bomb) object);
            }
            // Remove all explosions that are due this cycle
            if (object instanceof BombExplosion) {
                explosionTiles.remove((BombExplosion) object);
                world.destroyBody(((BombExplosion) object).getHitbox());
            }

            // Remove all collected power-ups
            if (object instanceof PowerUp) powerUps.remove((PowerUp) object);

            // Remove all enemies that were killed and died
            if (object instanceof Enemy) enemies.remove((Enemy) object);

        }
        objectsToRemoveNextCycle.clear();
    }

    /**
     * Performs as many physics steps as necessary to catch up to the given frame time.
     * This will update the Box2D world by the given time step.
     *
     * @param frameTime Time since last frame in seconds
     */
    private void doPhysicsStep(float frameTime) {
        this.physicsTime += frameTime;
        while (this.physicsTime >= TIME_STEP) {
            this.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            this.physicsTime -= TIME_STEP;
        }
    }

    /**
     * Places a bomb on the tile where the player is currently positioned.
     * Before placing the bomb, it checks if a bomb already exists
     * on the target tile to avoid overlapping placements.
     */
    private void dropBomb() {
        // Do not lay a bomb if all allowed bombs are currently placed
        if (bombsInPlay.size() >= maxBombsAllowed) return;

        SoundEffect.BOMB_DROP.play();

        // Since the players origin of coordinates is at their bottom left,
        // but the bomb should be placed on the perceived field at the bodies center / core, we add .5 to x and y
        // Beware that it is in our tile system not real screen coordinates
        int playerTileX = (int) (player.getX() + .5);
        int playerTileY = (int) (player.getY() + .5);

        boolean alredayBombOnTile = false;
        for (Bomb bomb : bombsInPlay) {


            if (bomb.getX() == playerTileX && bomb.getY() == playerTileY) {
                alredayBombOnTile = true;
                break;
            }
        }

        if (!alredayBombOnTile) bombsInPlay.add(new Bomb(playerTileX, playerTileY, objectsToRemoveNextCycle));

    }

    /**
     * Ends the game and terminates the game loop. Depending on the outcome
     * it will either transition to the victory screen or handle the player's death and
     * the subsequent transition to the defeat screen.
     *
     * @param victory a boolean indicating whether the game ended in victory (true) or defeat (false)
     */
    public void endGame(boolean victory) {
        // End the game and game loop
        gameIsOver = true;
        gameWasWon = victory;

        if (gameOverFadeOutTime == 0.0f && !victory) player.die();

        if (gameOverFadeOutTime >= 1.0f) {
            game.goToGameEndScreen(victory);
        }
    }

    /**
     * Triggers the explosion of a bomb at its specified location, determining the affected tiles
     * within a certain radius and direction. It calculates the tiles affected by the explosion
     * and stops the explosion's propagation when encountering indestructible obstacles.
     *
     * @param bomb The bomb to be exploded. Provides information about its placement on the map (x, y).
     */
    private void explodeBomb(Bomb bomb) {
        SoundEffect.BOMB_EXPLOSION.play();

        final float x = bomb.getX();
        final float y = bomb.getY();

        // Put directions of bomb in a list, like vectors in each up, down, left, right
        final List<String> direction = new ArrayList<>();
        direction.add("1,0");
        direction.add("0,1");
        direction.add("-1,0");
        direction.add("0,-1");

        // Add center tile
        // Since bomb was placed here, it can always exist.
        explosionTiles.add(new BombExplosion(world, x, y, BombExplosionTile.CENTER, objectsToRemoveNextCycle));

        // Iterate over each direction
        for (String dir : direction) {
            final int xShift = Integer.parseInt(dir.split(",")[0]);
            final int yShift = Integer.parseInt(dir.split(",")[1]);

            // iterate from 1 to radius
            for (int i = 1; i <= bombRadius; i++) {
                // Check if the tile shifted from the bombs center is an IndestructibleWall
                // We do not need to check for the outside of the map, since it is always surrounded by an IndestructibleWall
                final int newX = (int) (x + xShift * i);
                final int newY = (int) (y + yShift * i);

                // If we hit an IndestructibleWall we stop in this direction and go the next
                // Otherwise we add the next element of the bombs explosion
                if (this.wallElements.get(newX).get(newY) instanceof IndestructibleWall) {
                    break;
                } else {
                    explosionTiles.add(new BombExplosion(world, newX, newY, BombExplosionTile.getByDirectionAndEnd(dir, i == bombRadius), objectsToRemoveNextCycle));
                }
            }
        }
    }

    /**
     * Returns the player on the map.
     */
    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getEnemiesCountAtBeginning() {
        return enemiesCountAtBeginning;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public Exit getExit() {
        return exit;
    }

    public List<Drawable> getObjectsToRemoveNextCycle() {
        return objectsToRemoveNextCycle;
    }

    public List<Bomb> getBombsInPlay() {
        return bombsInPlay;
    }

    public List<BombExplosion> getExplosionTiles() {
        return explosionTiles;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public int getMaxBombsAllowed() {
        return maxBombsAllowed;
    }

    public void setMaxBombsAllowed(int maxBombsAllowed) {
        this.maxBombsAllowed = maxBombsAllowed;
    }

    /**
     * Returns the all static Elements on the map (e.g. walls, paths, etc.)
     */
    public List<Drawable> getWallElements() {
        return wallElements.stream()
                .flatMap(List::stream)
                .toList();
    }
}

