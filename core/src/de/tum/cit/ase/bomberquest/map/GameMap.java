package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.GameContactListener;
import de.tum.cit.ase.bomberquest.utils.PropertiesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game map.
 * Holds all the objects and entities in the game.
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

    private List<Bomb> bombsInPlay = new ArrayList<>();
    private final List<Bomb> bombsToExplode = new ArrayList<>();

    private final List<BombExplosion> explosionTiles = new ArrayList<>();

    private final List<List<Drawable>> backgroundElements;

    public GameMap(BomberQuestGame game) {
        this.game = game;

        this.world = new World(Vector2.Zero, true);

        // The contact listener handles all collisions in the game
        this.world.setContactListener(new GameContactListener());

        // Create a player with initial position (1, 3)
        this.player = new Player(this.world, PropertiesHelper.getPlayerEntranceX(), PropertiesHelper.getPlayerEntranceY());

        this.exit = new Exit(PropertiesHelper.getExitX(), PropertiesHelper.getExitY());

        // TODO: The path file should come from somewhere else --> user should be able to choose the file

        this.backgroundElements = new ArrayList<>(PropertiesHelper.loadDrawablesFromProperties(world));
    }

    /**
     * Updates the game state. This is called once per frame.
     * Every dynamic object in the game should update its state here.
     *
     * @param frameTime the time that has passed since the last update
     */
    public void tick(float frameTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) layBomb();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) bombsInPlay = new ArrayList<>();

        for (Bomb b : bombsInPlay) b.tick(frameTime);

        for (BombExplosion e : explosionTiles) e.tick(frameTime);

        this.player.tick(frameTime);
        doPhysicsStep(frameTime);

        // Remove bombs that are due this cycle
        for (Bomb b : bombsToExplode) {
            explodeBomb(b);
            bombsInPlay.remove(b);
        }

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
    private void layBomb() {

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

        if (!alredayBombOnTile) bombsInPlay.add(new Bomb(playerTileX, playerTileY, () -> {}, bombsToExplode));

    }

    private void explodeBomb(Bomb bomb) {
        explosionTiles.add(new BombExplosion(bomb.getX(), bomb.getY(), BombExplosionTile.CENTER));

        explosionTiles.add(new BombExplosion(bomb.getX()+1, bomb.getY(), BombExplosionTile.RIGHT_MIDDLE));
        explosionTiles.add(new BombExplosion(bomb.getX()+2, bomb.getY(), BombExplosionTile.RIGHT_END));
        explosionTiles.add(new BombExplosion(bomb.getX()-1, bomb.getY(), BombExplosionTile.LEFT_MIDDLE));
        explosionTiles.add(new BombExplosion(bomb.getX()-2, bomb.getY(), BombExplosionTile.LEFT_END));


        explosionTiles.add(new BombExplosion(bomb.getX(), bomb.getY()+1, BombExplosionTile.TOP_MIDDLE));
        explosionTiles.add(new BombExplosion(bomb.getX(), bomb.getY()+2, BombExplosionTile.TOP_END));
        explosionTiles.add(new BombExplosion(bomb.getX(), bomb.getY()-1, BombExplosionTile.BOTTOM_MIDDLE));
        explosionTiles.add(new BombExplosion(bomb.getX(), bomb.getY()-2, BombExplosionTile.BOTTOM_END));
    }

    /**
     * Returns the player on the map.
     */
    public Player getPlayer() {
        return player;
    }

    public Exit getExit() {
        return exit;
    }

    public List<Bomb> getBombsInPlay() {
        return bombsInPlay;
    }

    public List<BombExplosion> getExplosionTiles() {
        return explosionTiles;
    }

    /**
     * Returns the all static Elements on the map (e.g. walls, paths, etc.)
     */
    public List<Drawable> getStaticElements() {
        return backgroundElements.stream()
                .flatMap(List::stream)
                .toList();
    }
}

