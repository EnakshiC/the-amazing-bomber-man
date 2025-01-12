package de.tum.cit.ase.bomberquest.utils;

import com.badlogic.gdx.physics.box2d.World;
import de.tum.cit.ase.bomberquest.map.DestructibleWall;
import de.tum.cit.ase.bomberquest.map.IndestructibleWall;
import de.tum.cit.ase.bomberquest.map.Path;
import de.tum.cit.ase.bomberquest.texture.Drawable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The PropertiesHelper class helps with the handling of the .properties map files.
 * It stores the current map, returns enemies, players positions, entries, exits, etc.
 */
public class PropertiesHelper {
    // TODO: Make Fallback relative to project, not absolute paths
    //private static final String FALLBACK_PATH = "/Users/maximilianschiff/IdeaProjects/itp2425itp2425projectwork-onemanshow/maps/map-1.properties";
    private static final String FALLBACK_PATH = "C:/Users/enaks/IdeaProjects/itp2425itp2425projectwork-onemanshow/maps/map-1.properties";

    private static String currentFilePath = FALLBACK_PATH;

    private static String exitCoordinates;


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
     * @return 0 if successful or -1 if not -> using FALLBACK_PATH.
     */
    public static int loadNewMap(String path) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading .properties file: " + e.getMessage());
            return -1;
        }

        if (isMapValid(properties)) {
            currentFilePath = path;
            // Reset the exit
            exitCoordinates = null;

            return 0;
        } else {
            System.err.println("Invalid map file. Reverting to fallback.");
            currentFilePath = FALLBACK_PATH;
            return -1;
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
     * Returns a List<List<Drawable>> that contains all the non-dynamic elements (walls, paths, power-ups, etc.).
     *
     * @param world is the world used for walls to set hitboxes.
     */
    public static List<List<Drawable>> loadDrawablesFromProperties(World world) {
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
                    } else if (value == 1) {
                        row.add(new DestructibleWall(world, x, y));
                    } else {
                        row.add(new Path(x, y));
                    }
                } else {
                    // Fallback if a certain set of coordinates is not present in the property file.
                    row.add(new Path(x, y));
                }
            }
            elements.add(row);
        }

        return elements;
    }


    /**
     * Retrieves the x-coordinate of the player's entrance from the properties file.
     * @return the x-coordinate of the player's entrance, or 0 if no entrance is found.
     */
    public static int getPlayerEntranceX() {
        return getProperties().keySet().stream()
                .filter(key -> getProperties().getProperty(key.toString()).equals("2"))
                .map(key -> key.toString().split(",")[0])
                .mapToInt(Integer::parseInt)
                .findFirst()
                .orElse(0); // Default to 0 if no entrance is found
    }

    /**
     * Retrieves the y-coordinate of the player's entrance from the properties file.
     * @return the y-coordinate of the player's entrance, or 0 if no entrance is found.
     */
    public static int getPlayerEntranceY() {
        return getProperties().keySet().stream()
                .filter(key -> getProperties().getProperty(key.toString()).equals("2"))
                .map(key -> key.toString().split(",")[1])
                .mapToInt(Integer::parseInt)
                .findFirst()
                .orElse(0); // Default to 0 if no entrance is found
    }

    /**
     * Retrieves the x-coordinate of the exit.
     * Ensures an exit exists before retrieving its coordinates.
     *
     * @return the x-coordinate of the exit.
     */
    public static int getExitX() {
        setExit();

        // System.out.println("X coordinates: " + Integer.parseInt(exitCoordinates.split(",")[0]));

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

        // System.out.println("Y coordinates: " + Integer.parseInt(exitCoordinates.split(",")[1]));

        return Integer.parseInt(exitCoordinates.split(",")[1]);

    }


    /**
     * Sets and stores coordinates of an exit in this map
     * If there is an exit in the property file, this one is used.
     * Otherwise, an exit is placed at a random destructible wall of the map.
     */
    private static void setExit() {
        // Check if an exit already exists in the map.
        if (exitCoordinates != null) return; // If an exit exists, do nothing.

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

            // System.out.println("Exit placed at: " + exitCoordinates);
        } else {
            // If no destructible walls are found, warn about the lack of exit placement.
            System.err.println("No suitable destructible walls found to place an exit.");

            // Fallback
            exitCoordinates = "1,1";

        }
    }
}