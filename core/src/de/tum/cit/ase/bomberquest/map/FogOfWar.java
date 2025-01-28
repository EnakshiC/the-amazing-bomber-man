package de.tum.cit.ase.bomberquest.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.tum.cit.ase.bomberquest.map.basic_tiles.DestructibleWall;
import de.tum.cit.ase.bomberquest.map.basic_tiles.IndestructibleWall;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.texture.Textures;
import de.tum.cit.ase.bomberquest.utils.PropertiesHelper;

import java.util.*;

/**
 * The FogOfWar class manages the visibility and discovery of tiles on a game map.
 * It handles the fog effect, ensuring unexplored areas remain hidden and dynamically
 * updates visible and discovered tiles as the player explores the map.
 */
public class FogOfWar {
    private final boolean[][] discovered;
    private final boolean[][] visible;

    private final GameMap gameMap;

    private final int mapWidth;
    private final int mapHeight;

    private final int fogRadius;
    private final boolean gameHasFog;

    private final List<FogTile> fogTiles = new LinkedList<>();


    private static int currentFogSettingIndex = 0;
    private static final List<String> FOG_SETTINGS = Arrays.asList("No Fog", "Easy Fog", "Medium Fog", "Hard Fog", "Extreme Fog");

    public static String getFogSettings() { return FOG_SETTINGS.get(currentFogSettingIndex); }

    /**
     * Constructs a new FogOfWar instance for the given game map.
     * Initializes the visibility and discovery states and creates FogTile instances for all tiles.
     *
     * @param gameMap   the game map to which this fog of war system is applied
     */
    public FogOfWar(GameMap gameMap) {
        this.gameMap = gameMap;
        this.mapWidth = PropertiesHelper.getMapWith();
        this.mapHeight = PropertiesHelper.getMapHeight();

        this.fogRadius = getFogRadius();
        this.gameHasFog = getGameHasFog();

        this.discovered = new boolean[mapWidth][mapHeight];
        this.visible = new boolean[mapWidth][mapHeight];

        // Fill both arrays with all 'false' values
        for (int x = 0; x < mapWidth; x++) {
            Arrays.fill(this.discovered[x], !gameHasFog);
            Arrays.fill(this.visible[x], !gameHasFog);
        }

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                fogTiles.add(new FogTile(x, y, this));
            }
        }
    }

    /**
     * Selects the next fog of war setting
     */
    public static void nextFogSetting() {
        currentFogSettingIndex++;
        if (currentFogSettingIndex >= FOG_SETTINGS.size()) {
            currentFogSettingIndex = 0;
        }
    }

    /**
     * Selects the previous fog of war setting
     */
    public static void previousFogSetting() {
        currentFogSettingIndex--;
        if (currentFogSettingIndex < 0) {
            currentFogSettingIndex = FOG_SETTINGS.size() - 1;
        }
    }

    /**
     * Returns the actual radius of visibility for the current settings
     *
     * @return the radius of visibility
     */
    private static int getFogRadius() {
        // No Fog
        if (currentFogSettingIndex == 0) return 999;

        // Easy Fog
        if (currentFogSettingIndex == 1) return 5;

        // Medium Fog
        if (currentFogSettingIndex == 2) return 3;

        // Hard Fog
        if (currentFogSettingIndex == 3) return 1;

        // Extreme Fog
        if (currentFogSettingIndex == 4) return 0;

        return 999;
    }

    /**
     * @return true if there is fog in the game with the current settings
     */
    private static boolean getGameHasFog() {
        return currentFogSettingIndex != 0;
    }

    /**
     * Checks if there is a clear line of sight (no walls) from a visible tile to the given tile.
     *
     * @param x The x-coordinate of the target tile.
     * @param y The y-coordinate of the target tile.
     *
     * @return true if there is a clear line of sight, false otherwise.
     */
    private boolean hasLineOfSight(int x, int y) {
        for (int dx = -fogRadius; dx <= fogRadius; dx++) {
            for (int dy = -fogRadius; dy <= fogRadius; dy++) {
                int nx = x + dx;
                int ny = y + dy;

                // Check if within bounds and visible
                if (nx >= 0 && nx < mapWidth && ny >= 0 && ny < mapHeight && visible[nx][ny]) {
                    // Check line of sight
                    if (isClearPath(nx, ny, x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if there is a clear path (no walls) between two tiles using Bresenham's line algorithm.
     *
     * @param x1 The x-coordinate of the start tile.
     * @param y1 The y-coordinate of the start tile.
     * @param x2 The x-coordinate of the end tile.
     * @param y2 The y-coordinate of the end tile.
     *
     * @return true if the path is clear, false otherwise.
     */
    private boolean isClearPath(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            // If a wall is found, the path is blocked
            if (isWall(x1, y1)) {
                return false;
            }

            // Reached the target tile
            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        return true;
    }

    /**
     * Tick method to update player state each frame.
     */
    public void tick(double frameTime) {
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                visible[x][y] = false;
            }
        }

        int px = Math.round(gameMap.getPlayer().getX());
        int py = Math.round(gameMap.getPlayer().getY());

        Queue<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[mapWidth][mapHeight];

        queue.add(new Node(px, py));
        visited[px][py] = true;
        visible[px][py] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int cx = current.x;
            int cy = current.y;

            for (Direction dir : Direction.values()) {
                int nx = cx + dir.dx;
                int ny = cy + dir.dy;

                // Bounds
                if (nx < 0 || nx >= mapWidth || ny < 0 || ny >= mapHeight) continue;

                if (visited[nx][ny]) continue;

                // Distance check
                int dist = Math.abs(nx - px) + Math.abs(ny - py);
                if (dist > fogRadius) continue;

                visited[nx][ny] = true;
                visible[nx][ny] = true;

                // If it's a wall, do not enqueue further
                if (!isWall(nx, ny)) {
                    queue.add(new Node(nx, ny));
                }
            }
        }

        // Update discovered
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (visible[x][y]) {
                    discovered[x][y] = true;
                }
            }
        }
    }

    /**
     * Simple helper to store queue coordinates
     */
        private record Node(int x, int y) {}

    /** Directions to move in the queue */
    private enum Direction {
        UP(0, 1),
        DOWN(0, -1),
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP_LEFT(-1, 1),
        UP_RIGHT(1, 1),
        DOWN_LEFT(-1, -1),
        DOWN_RIGHT(1, -1);

        public final int dx;
        public final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    /** Checks if a given tile is a wall (Indestructible or a solid Destructible)
     * @param x the x value of the tile
     * @param y the y vale of the tile
     *
     * @return true if the element at position x,y is a (currently) solid wall
     * */
    private boolean isWall(int x, int y) {
        for (Drawable element : gameMap.getWallElements()) {
            int ex = Math.round(element.getX());
            int ey = Math.round(element.getY());
            if (ex == x && ey == y) {
                // IndestructibleWall or a solid DestructibleWall
                if (element instanceof IndestructibleWall) return true;
                if (element instanceof DestructibleWall dw && dw.isSolid()) return true;
            }
        }
        return false;
    }

    /**
     * Returns the current transparency of a tile
     *
     * @param x the x value of the tile
     * @param y the y value of the tile
     *
     * @return alpha value visibility of this fog tile
     */
    public float calculateAlpha(int x, int y) {
        // Already discovered tiles are fully visible
        if (discovered[x][y]) return 0.0f;

        // Check if there is a line of sight to the tile
        if (!hasLineOfSight(x, y)) {
            return 1.0f; // Fully opaque if there is no line of sight
        }

        // Check the neighbours in all directions
        float alpha = 1.0f; // Standard: fully opaque
        int maxDistance = 2; // Max. distance for alpha calculations

        for (int dx = -maxDistance; dx <= maxDistance; dx++) {
            for (int dy = -maxDistance; dy <= maxDistance; dy++) {
                int nx = x + dx;
                int ny = y + dy;

                // Check for bounds
                if (nx >= 0 && nx < mapWidth && ny >= 0 && ny < mapHeight) {
                    if (discovered[nx][ny]) {
                        // Alpha gets lower the closer a discovered tile is
                        float distance = (float) Math.sqrt(dx * dx + dy * dy);
                        alpha = Math.min(alpha, distance / maxDistance);
                    }
                }
            }
        }

        return alpha;
    }

    /**
     * Helper class for the style of a fog tile
     * @param x
     * @param y
     * @param fogOfWar
     */
    public record FogTile(int x, int y, FogOfWar fogOfWar) implements Drawable {
        @Override
        public TextureRegion getCurrentAppearance() {
            return fogOfWar.discovered[x][y] ? Textures.EMPTY : Textures.FOG_CENTER;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }

        public float getAlpha() {
            return fogOfWar.calculateAlpha(x, y);
        }
    }

    public List<FogTile> getFogTiles() {
        return fogTiles;
    }

}
