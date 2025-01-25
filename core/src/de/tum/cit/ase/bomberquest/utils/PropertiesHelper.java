package de.tum.cit.ase.bomberquest.utils;

import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.*;
import de.tum.cit.ase.bomberquest.map.basic_tiles.DestructibleWall;
import de.tum.cit.ase.bomberquest.map.basic_tiles.EmptyTile;
import de.tum.cit.ase.bomberquest.map.basic_tiles.IndestructibleWall;
import de.tum.cit.ase.bomberquest.map.basic_tiles.Path;
import de.tum.cit.ase.bomberquest.map.enemies.*;
import de.tum.cit.ase.bomberquest.map.power_up.PowerUp;
import de.tum.cit.ase.bomberquest.map.power_up.PowerUpBombRadius;
import de.tum.cit.ase.bomberquest.map.power_up.PowerUpConcurrentBombs;
import de.tum.cit.ase.bomberquest.texture.Drawable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The PropertiesHelper class helps with the handling of the .properties map files.
 * It stores the current map, returns enemies, players positions, entries, exits, etc.
 */
public class PropertiesHelper {
    private static final String FALLBACK_PATH = "maps/map-1.properties";

    private static String currentFilePath = FALLBACK_PATH;

    private static String exitCoordinates;

    private static String entryCoordinates;

    // Random to select random enemies
    private static final Random RANDOM = new Random();

    // Available maps in the game initially
    private static final Map<String, String> MAP_PATHS = Map.of(
            "Map 1", "maps/map-1.properties",
            "Map 2", "maps/map-2.properties"
    );

    public static Map<String, String> getMapPaths() {
        return MAP_PATHS;
    }

    /**
     * Loads properties from the current map file.
     *
     * @return a Properties instance containing the map data.
     */
    private static Properties getProperties() {

        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream(currentFilePath)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading .properties file: " + e.getMessage());
        }

        return properties;
    }

    /**
     * Validates the map file by checking the keys (coordinates) and values (object types).
     *
     * @param properties the properties to validate.
     * @return true if valid, false otherwise.
     */
    private static boolean isMapValid(Properties properties) {
        for (Object key : properties.keySet()) {
            String keyStr = key.toString();
            // Ensure keys are valid coordinates
            if (!keyStr.matches("\\d+,\\d+")) {
                System.err.println("Invalid key format: " + keyStr);
                return false;
            }

            String value = properties.getProperty(keyStr);
            try {
                int objectType = Integer.parseInt(value);
                // Ensure value is a valid object type
                if (objectType < 0 || objectType > 6) {
                    System.err.println("Invalid object type: " + value);
                    return false;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid value format: " + value);
                return false;
            }
        }

        return true;
    }

    /**
     * Stores a new map path if there is a valid file at the given path.
     *
     * @param path is an absolute path to a .properties map file.
     */
    public static void loadNewMap(String path) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading .properties file: " + e.getMessage());
            return;
        }

        if (isMapValid(properties)) {
            currentFilePath = path;
            // Reset the exit
            exitCoordinates = null;

            // Reset the entry
            entryCoordinates = null;

        } else {
            System.err.println("Invalid map file. Reverting to fallback.");
            currentFilePath = FALLBACK_PATH;
        }
    }

    /**
     * Returns the width of this map
     *
     * @return the maximum value that x can have in a map = width of map
     */
    public static int getMapWith() {
        return getProperties().keySet().stream()
                .map(key -> key.toString().split(",")[0])
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }

    /**
     * Returns the height of this map
     *
     * @return the maximum value that y can have in a map = height of map
     */
    public static int getMapHeight() {
        return getProperties().keySet().stream()
                .map(key -> key.toString().split(",")[1])
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);
    }

    /**
     * Returns a List<List<Drawable>> that contains all the non-dynamic elements (walls).
     *
     * @param world is the world used for walls to set hitboxes.
     */
    public static List<List<Drawable>> loadWallsFromProperties(World world, List<Drawable> objectsToBeRemovedNextCycle) {
        Properties properties = getProperties(); // Retrieve properties once.
        List<List<Drawable>> elements = new ArrayList<>();

        // Iterate over every row and every column and add the Drawable found under this key.
        for (int x = 0; x < getMapWith() + 1; x++) {
            List<Drawable> row = new ArrayList<>();
            for (int y = 0; y < getMapHeight() + 1; y++) {
                String key = x + "," + y;
                if (properties.containsKey(key)) {
                    int value = Integer.parseInt(properties.getProperty(key));

                    if (value == 0) {
                        row.add(new IndestructibleWall(world, x, y));
                    } else if (value == 1 || value == 4 || value == 5 || value == 6) {
                        // Add a DestructibleWall where it should be and over exit and power-ups
                        row.add(new DestructibleWall(world, x, y, objectsToBeRemovedNextCycle));
                    } else {
                        // Make sure that DestructibleWall is placed over random generated exit, too.
                        if (x == getExitX() && y == getExitY()) {
                            row.add(new DestructibleWall(world, x, y, objectsToBeRemovedNextCycle));
                        } else {
                            row.add(new EmptyTile(x, y));
                        }

                    }
                } else {
                    // Fallback if a certain set of coordinates is not present in the property file.
                    row.add(new EmptyTile(x, y));
                }
            }
            elements.add(row);
        }

        return elements;
    }

    /**
     * Loads background paths from the properties associated with the current map file
     * and constructs a grid of paths based on the map's dimensions.
     *
     * @return a 2D list containing {@link Path} objects that represent the background paths of the map.
     */
    public static List<List<Path>> loadBackgroundPathsFromProperties() {
        List<List<Path>> elements = new ArrayList<>();

        // Iterate over every row and every column and add the Drawable found under this key.
        for (int x = 0; x < getMapWith() + 1; x++) {
            List<Path> row = new ArrayList<>();
            for (int y = 0; y < getMapHeight() + 1; y++) {
                row.add(new Path(x, y));
            }
            elements.add(row);
        }

        return elements;
    }

    /**
     * Loads power-up objects from the map's properties file based on their coordinates and types.
     *
     * @param world the physics world to initialize their hitboxes.
     * @param objectsToBeRemovedNextCycle a list of drawable objects that are flagged for removal in the next game cycle.
     * @return a list of PowerUp objects created from the map properties.
     */
    public static List<PowerUp> loadPowerUpsFromProperties(World world, List<Drawable> objectsToBeRemovedNextCycle) {
        Properties properties = getProperties(); // Retrieve properties once.
        List<PowerUp> elements = new ArrayList<>();

        // Iterate over every row and every column and add the Drawable found under this key.
        for (int x = 0; x < getMapWith() + 1; x++) {
            for (int y = 0; y < getMapHeight() + 1; y++) {
                if (properties.containsKey(x + "," + y)) {
                    int value = Integer.parseInt(properties.getProperty(x + "," + y));

                    if (value == 5) {
                        elements.add(new PowerUpConcurrentBombs(world, x, y, objectsToBeRemovedNextCycle));
                    } else if (value == 6) {
                        elements.add(new PowerUpBombRadius(world, x, y, objectsToBeRemovedNextCycle));
                    }
                }
            }
        }

        return elements;
    }

    /**
     * Loads and initializes a list of Enemy objects based on the specified map properties.
     *
     * @param world the physics world to associate the enemies with, enabling proper interaction and behaviors.
     * @param gameMap the map object where the enemies will be placed and operated upon.
     * @return a list of Enemy objects loaded and initialized based on the map properties.
     */
    public static List<Enemy> loadEnemiesFromProperties(World world, GameMap gameMap) {
        Properties properties = getProperties(); // Retrieve properties once.
        List<Enemy> elements = new ArrayList<>();

        // Iterate over every row and every column and add the Drawable found under this key.
        for (int x = 0; x < getMapWith() + 1; x++) {
            for (int y = 0; y < getMapHeight() + 1; y++) {
                if (properties.containsKey(x + "," + y)) {
                    int value = Integer.parseInt(properties.getProperty(x + "," + y));

                    if (value == 3) {
                        List<Supplier<Enemy>> enemySuppliers = getEnemySuppliers(world, x, y, gameMap);

                        elements.add(enemySuppliers.get(RANDOM.nextInt(enemySuppliers.size())).get());
                    }
                }
            }
        }

        return elements;
    }

    /**
     * Provides a list of suppliers that generate enemy objects with specific movement behaviors.
     * This is used to spawn a random enemy type at this position on the map.
     *
     * @param world the physics world where the enemies will be placed.
     * @param x the initial x-coordinate for the enemies.
     * @param y the initial y-coordinate for the enemies.
     * @param gameMap the map object associated with the enemies' interactions and behaviors.
     * @return a list of suppliers, each capable of generating an enemy instance.
     */
    private static List<Supplier<Enemy>> getEnemySuppliers(World world, float x, float y, GameMap gameMap) {
        return List.of(
                () -> new EnemyWithBasicMovement(world, x, y, gameMap),
                () -> new EnemyWithDecisiveMovement(world, x, y, gameMap),
                () -> new EnemySmartSearcher(world, x, y, gameMap),
                ()->new EnemyGhost(world, x, y, gameMap)
        );
    }

    /**
     * Retrieves the x-coordinate of the player's entrance from the properties file.
     *
     * @return the x-coordinate of the player's entrance, or 0 if no entrance is found.
     */
    public static int getPlayerEntranceX() {
        ensureEntryCoordinatesInitialized();

        return Integer.parseInt(entryCoordinates.split(",")[0]); // Default to 0 if no entrance is found
    }

    /**
     * Retrieves the y-coordinate of the player's entrance from the properties file.
     *
     * @return the y-coordinate of the player's entrance, or 0 if no entrance is found.
     */
    public static int getPlayerEntranceY() {
        ensureEntryCoordinatesInitialized();

        return Integer.parseInt(entryCoordinates.split(",")[1]);
    }

    /**
     * Ensures the entry coordinates are initialized only once.
     * "synchronized" ensures that the method is only called once at the same time, so not to change the
     * entry between returning the x and y coordinates
     */
    private static synchronized void ensureEntryCoordinatesInitialized() {
        if (entryCoordinates == null) {
            setEntryCoordinates();
        }
    }

    /**
     * Retrieves all entries for potential entrances to the map and selects a random one and sets it
     * Fallback is '2,1'
     */
    private static void setEntryCoordinates() {
        entryCoordinates = getProperties().keySet().stream()
                .map(Object::toString)
                .filter(string -> getProperties().getProperty(string).equals("2"))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list ->
                        list.isEmpty() ? "2,1" : list.get(new Random().nextInt(list.size()))
                ));
    }

    /**
     * Retrieves the x-coordinate of the exit.
     * Ensures an exit exists before retrieving its coordinates.
     *
     * @return the x-coordinate of the exit.
     */
    public static int getExitX() {
        setExit();
        return Integer.parseInt(exitCoordinates.split(",")[0]);
    }

    /**
     * Retrieves the y-coordinate of the exit.
     * Ensures an exit exists before retrieving its coordinates.
     *
     * @return the y-coordinate of the exit.
     */
    public static int getExitY() {
        setExit();
        return Integer.parseInt(exitCoordinates.split(",")[1]);

    }

    /**
     * Sets and stores coordinates of an exit in this map
     * If there is an exit in the property file, this one is used.
     * Otherwise, an exit is placed at a random destructible wall of the map.
     */
    private static void setExit() {
        // Check if an exit is already stored for this map.
        if (exitCoordinates != null) return;

        // 1 --> if there is an exit set for this map
        List<String> exitKeys = new ArrayList<>();
        for (Object key : getProperties().keySet()) {
            if (getProperties().getProperty(key.toString()).equals("4")) {
                exitKeys.add(key.toString());
            }
        }
        // Select one of the found exit entries and set exitCoordinates
        if (!exitKeys.isEmpty()) {
            exitCoordinates = exitKeys.get((int) (Math.random() * exitKeys.size()));
            return;
        }

        // 2 --> if there is no exit set, place under random destructible wall
        // Find all destructible walls (value = "1").
        List<String> destructibleWallsKeys = new ArrayList<>();
        for (Object key : getProperties().keySet()) {
            if (getProperties().getProperty(key.toString()).equals("1")) {
                destructibleWallsKeys.add(key.toString());
            }
        }

        // If there are destructible walls, randomly place an exit under one of them.
        if (!destructibleWallsKeys.isEmpty()) {
            exitCoordinates = destructibleWallsKeys.get((int) (Math.random() * destructibleWallsKeys.size()));
        } else {
            // If no destructible walls are found, warn about the lack of exit placement.
            System.err.println("No suitable destructible walls found to place an exit.");

            // Fallback at bottom left corner
            exitCoordinates = "1,1";
        }
    }
}