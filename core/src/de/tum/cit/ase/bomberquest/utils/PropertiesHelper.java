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
    private static final String FALLBACK_PATH = "/Users/maximilianschiff/IdeaProjects/itp2425itp2425projectwork-onemanshow/maps/map-1.properties";
    // private static final String FALLBACK_PATH = "C:/Users/enaks/IdeaProjects/itp2425itp2425projectwork-onemanshow/maps/map-1.properties";

    private static String currentFilePath = FALLBACK_PATH;

    /**
     * Returns Properties instance at the curren file path
     */
    private static Properties getProperties() {
        Properties properties = new Properties();

        try {
            FileInputStream input = new FileInputStream(currentFilePath);
            properties.load(input);

            input.close();
        } catch (IOException e) {
            System.err.println("Error loading .properties-file: " + e.getMessage());
        }

        return properties;
    }

    /**
     * Stores a new map path if there is a valid file at the given path.
     *
     * @param path is an absolute path to a .properties map file.
     * @return 0 if successful or -1 if not -> using FALLBACK_PATH.
     */
    public static int loadNewMap(String path) {
        currentFilePath = path;

        //TODO: Check if the map is correct with a better way. Do we have to check if maps are "good/correct"?
        if (!getProperties().isEmpty()) {
            currentFilePath = FALLBACK_PATH;
            return -1;
        }

        return 0;
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
     * Returns a List<List<Drawable>> that contains all the non-dynamic elements (walls, paths, power-ups, etc.)
     *
     * @param world is the world used for walls to set hitboxes.
     */
    public static List<List<Drawable>> loadDrawablesFromProperties(World world) {
        List<List<Drawable>> elements = new ArrayList<>();

        // Iterate over every row and every colum and add the Drawable found under this key
        for (int x = 0; x < getMapWith() + 1; x++) {
            List<Drawable> row = new ArrayList<>();
            for (int y = 0; y < getMapHeight() + 1; y++) {
                if (getProperties().containsKey(x + "," + y)) {
                    int value = Integer.parseInt(getProperties().getProperty(x + "," + y));

                    // TODO: Add the real property AND outsource to extra method that just matches the int to the Drawable
                    if (value == 0) {
                        row.add(new IndestructibleWall(world, x, y));
                    } else if (value == 1) {
                        row.add(new DestructibleWall(world, x, y));
                    } else {
                        row.add(new Path(x, y));
                    }
                } else {
                    // Fallback if a certain set of coordinates is not present in the property file
                    row.add(new Path(x, y));
                }
            }
            elements.add(row);
        }

        return elements;
    }

    // TODO: Return Real Player Entrance X and Y
    public static int getPlayerEntranceX() {
        return 1;
    }
    public static int getPlayerEntranceY() {
        return 10;
    }

    // TODO: Return Exit -> either look for any exits that are in the map or create random exit --> See instructions
}
