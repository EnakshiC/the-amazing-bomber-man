package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
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
    /** The number of velocity iterations for the physics simulation. */
    private static final int VELOCITY_ITERATIONS = 6;
    /** The number of position iterations for the physics simulation. */
    private static final int POSITION_ITERATIONS = 2;
    /**
     * The accumulated time since the last physics step.
     * We use this to keep the physics simulation at a constant rate even if the frame rate is variable.
     */
    private float physicsTime = 0;
    
    /** The game, in case the map needs to access it. */
    private final BomberQuestGame game;
    /** The Box2D world for physics simulation. */
    private final World world;
    
    // Game objects
    private final Player player;
    
    private final Chest chest;

    private final List<List<Drawable>> backgroundElements = new ArrayList<>();
    
    public GameMap(BomberQuestGame game) {
        this.game = game;
        this.world = new World(Vector2.Zero, true);
        // Create a player with initial position (1, 3)
        this.player = new Player(this.world, 1, 5);
        // Create a chest in the middle of the map
        this.chest = new Chest(world, 6, 6);

        // TODO: The path file should come from somewhere else --> user should be able to choose the file
        this.loadDrawablesFromProperties("/Users/maximilianschiff/IdeaProjects/itp2425itp2425projectwork-onemanshow/maps/map-1.properties");
    }

    /**
     * Loads properties from a files at the given path and adds the drawables to this.
     * Checks for the correct form.
     * @param path is the absolute path to the file.
     */
    private void loadDrawablesFromProperties(String path) {
        Properties properties = new Properties();
        try {
            // Load the property file from the given path
            FileInputStream input = new FileInputStream(path);
            properties.load(input);

            // TODO: get real size of loaded map

            // Iterate over every row and every colum and add the Drawable found under this key
            for (int x = 0; x < getMaxX(properties) + 1; x++) {
                List<Drawable> row = new ArrayList<>();
                for (int y = 0; y < getMaxY(properties) + 1; y++) {
                    if (properties.containsKey(x + "," + y)) {
                        int value = Integer.parseInt(properties.getProperty(x + "," + y));

                        // TODO: Add the real property!
                        if (value == 0) {
                             row.add(new Path(x, y));
                        } else {
                            row.add(new Flowers(x, y));
                        }
                    } else {
                        // Fallback if a certain set of coordinates is not present in the property file
                        row.add(new Flowers(x, y));
                    }
                }
                this.backgroundElements.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error loading .properties-file: " + e.getMessage());
        }
    }

    /**
     * Returns the max x value of this map
     * @param properties is the map file
     * @return the maximum value that x can have
     */
    public int getMaxX(Properties properties) {
        return properties.keySet().stream()
                .map(key -> key.toString().split(",")[0])
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }

    /**
     * Returns the max y value of this map
     * @param properties is the map file
     * @return the maximum value that y can have
     */
    public int getMaxY(Properties properties) {
        return properties.keySet().stream()
                .map(key -> key.toString().split(",")[1])
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }

    /**
     * Updates the game state. This is called once per frame.
     * Every dynamic object in the game should update its state here.
     * @param frameTime the time that has passed since the last update
     */
    public void tick(float frameTime) {
        this.player.tick(frameTime);
        doPhysicsStep(frameTime);
    }
    
    /**
     * Performs as many physics steps as necessary to catch up to the given frame time.
     * This will update the Box2D world by the given time step.
     * @param frameTime Time since last frame in seconds
     */
    private void doPhysicsStep(float frameTime) {
        this.physicsTime += frameTime;
        while (this.physicsTime >= TIME_STEP) {
            this.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            this.physicsTime -= TIME_STEP;
        }
    }
    
    /** Returns the player on the map. */
    public Player getPlayer() {
        return player;
    }
    
    /** Returns the chest on the map. */
    public Chest getChest() {
        return chest;
    }
    
    /** Returns the flowers on the map. */
    public List<Drawable> getPath() {
        return backgroundElements.stream()
                .flatMap(List::stream)
                .toList();
    }
}
