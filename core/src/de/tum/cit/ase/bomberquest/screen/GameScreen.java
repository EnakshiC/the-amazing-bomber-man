package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.map.*;
import de.tum.cit.ase.bomberquest.map.basic_tiles.Path;
import de.tum.cit.ase.bomberquest.map.bomb.Bomb;
import de.tum.cit.ase.bomberquest.map.bomb.BombExplosion;
import de.tum.cit.ase.bomberquest.map.enemies.Enemy;
import de.tum.cit.ase.bomberquest.map.power_up.PowerUp;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.utils.PropertiesHelper;

import java.util.List;

/**
 * The GameScreen class is responsible for rendering the gameplay screen.
 * It handles the game logic, including camera updates, and rendering of the game elements.
 */
public class GameScreen implements Screen {

    /**
     * The size of a grid cell in pixels.
     * This allows us to think of coordinates in terms of square grid tiles
     * (e.g. x=1, y=1 is the bottom left corner of the map)
     * rather than absolute pixel coordinates.
     */
    public static final int TILE_SIZE_PX = 16;

    /**
     * Sets the percentage of the overall camera viewport that the player should be in at any time.
     */
    private static final float VIEW_FRAME_PERCENTAGE = 0.8f;

    /**
     * The scale of the game.
     * This is used to make everything in the game look bigger or smaller.
     */
    public static final int SCALE = 2;

    private final BomberQuestGame game;
    private final SpriteBatch spriteBatch;
    private final GameMap map;
    private final Hud hud;
    private final OrthographicCamera mapCamera;

    /**
     * A list of cached background Path objects used for optimizing render performance.
     */
    private final List<Path> backgroundPaths;

    /**
     * A cache used for storing and rendering sprites efficiently.
     */
    private SpriteCache spriteCache;

    /**
     * Represents the identifier for the cached background assets in the sprite cache.
     */
    private int cacheId;

    /**
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(BomberQuestGame game) {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();
        this.map = game.getMap();

        this.hud = new Hud(spriteBatch, game.getSkin().getFont("font"), this.map);

        // Create and configure the camera for the game view
        this.mapCamera = new OrthographicCamera();
        this.mapCamera.setToOrtho(false, (float) Gdx.graphics.getWidth() / SCALE, (float) Gdx.graphics.getHeight() / SCALE);

        // Load background tiles once
        // TODO: Check if it still works when we change the map!
        this.backgroundPaths = PropertiesHelper.loadBackgroundPathsFromProperties()
                .stream().flatMap(List::stream).toList();

        // Initialize the cache for the background tiles
        initBackgroundCache();

        // Ensure the camera starts centered on the player
        updateCamera();
    }

    /**
     * Initializes the background sprite cache for efficient rendering.
     */
    private void initBackgroundCache() {
        // System.out.println("initBackgroundCache");

        spriteCache = new SpriteCache();
        spriteCache.beginCache();

        for (Path path : backgroundPaths) {
            float x = path.x() * TILE_SIZE_PX * SCALE;
            float y = path.y() * TILE_SIZE_PX * SCALE;
            TextureRegion region = path.getCurrentAppearance();
            float width = region.getRegionWidth() * SCALE;
            float height = region.getRegionHeight() * SCALE;
            spriteCache.add(region, x, y, width, height);
        }

        cacheId = spriteCache.endCache();
    }

    /**
     * The render method is called every frame to render the game.
     *
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {

        // Clear the screen with a black color
        ScreenUtils.clear(Color.BLACK);

        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
            return;
        }

        // Cap frame time to 250ms to prevent spiral of death
        float frameTime = Math.min(deltaTime, 0.250f);

        // Update the map state
        map.tick(frameTime);

        // Method to check for key press to simulate victory or loss
        //TODO: Insert real variables later
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            game.setScreen(new VictoryScreen(game));
            return;
        }

        // Check for key press to simulate loss
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            game.setScreen(new LossScreen(game));
            return;
        }

        // Check for escape key to return to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
            return;
        }

        // Update the camera
        updateCamera();

        // Render the map on the screen
        renderMap();

        // Render the HUD on the screen
        hud.render();
    }

    /**
     * Updates the camera to keep the hero character centered while ensuring it stays within the map bounds.
     * The camera only moves when the character approaches the edges of the camera's focus area.
     * {@link de.tum.cit.ase.bomberquest.screen.GameScreen#VIEW_FRAME_PERCENTAGE} sets the percentage of the overall camera viewport.
     */
    private void updateCamera() {
        // Get the player's position in the map
        float playerX = map.getPlayer().x() * TILE_SIZE_PX * SCALE;
        float playerY = map.getPlayer().y() * TILE_SIZE_PX * SCALE;

        // Only adjust the camera if the players positions breaks out of the VIEW_FRAME_PERCENTAGE ratio of the frame
        // Since the players 0,0 is at its bottom left, we need adjust the camera accordingly on the right and top
        if (playerX < mapCamera.position.x - mapCamera.viewportWidth * VIEW_FRAME_PERCENTAGE * .5) {
            mapCamera.position.x = playerX + mapCamera.viewportWidth * VIEW_FRAME_PERCENTAGE * .5f;
        } else if (playerX > mapCamera.position.x + mapCamera.viewportWidth * VIEW_FRAME_PERCENTAGE * .5 - TILE_SIZE_PX) {
            mapCamera.position.x = playerX - mapCamera.viewportWidth * VIEW_FRAME_PERCENTAGE * .5f + TILE_SIZE_PX;
        }
        if (playerY < mapCamera.position.y - mapCamera.viewportHeight * VIEW_FRAME_PERCENTAGE * .5) {
            mapCamera.position.y = playerY + mapCamera.viewportHeight * VIEW_FRAME_PERCENTAGE * .5f;
        } else if (playerY > mapCamera.position.y + mapCamera.viewportHeight * VIEW_FRAME_PERCENTAGE * .5 - 2 * TILE_SIZE_PX) {
            mapCamera.position.y = playerY - mapCamera.viewportHeight * VIEW_FRAME_PERCENTAGE * .5f + 2 * TILE_SIZE_PX;
        }

        mapCamera.update(); // Apply camera updates
    }

    /**
     * Renders the game map using the configured camera.
     */
    private void renderMap() {
        // Load background Path tiles from cache
        spriteCache.setProjectionMatrix(mapCamera.combined);
        spriteCache.begin();
        spriteCache.draw(cacheId);
        spriteCache.end();

        // This configures the spriteBatch to use the camera's perspective when rendering
        spriteBatch.setProjectionMatrix(mapCamera.combined);

        // Start drawing
        spriteBatch.begin();

        // Render middle layer: power-ups, exit, etc.
        draw(spriteBatch, map.getExit());

        for (PowerUp powerUp : map.getPowerUps()) {
            draw(spriteBatch, powerUp);
        }

        for (Bomb bomb : map.getBombsInPlay()) {
            draw(spriteBatch, bomb);
        }


        // Render walls above middle layer
        for (Drawable element : map.getWallElements()) {
            draw(spriteBatch, element);
        }

        for (BombExplosion explosion : map.getExplosionTiles()) {
            draw(spriteBatch, explosion);
        }

        for (Enemy enemy : map.getEnemies()) {
            draw(spriteBatch, enemy);
        }

        draw(spriteBatch, map.getPlayer());


        // Finish drawing, i.e. send the drawn items to the graphics card
        spriteBatch.end();
    }

    /**
     * Draws this object on the screen.
     * The texture will be scaled by the game scale and the tile size.
     * This should only be called between spriteBatch.begin() and spriteBatch.end(), e.g. in the renderMap() method.
     *
     * @param spriteBatch The SpriteBatch to draw with.
     */
    private static void draw(SpriteBatch spriteBatch, Drawable drawable) {
        TextureRegion texture = drawable.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = drawable.x() * TILE_SIZE_PX * SCALE;
        float y = drawable.y() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }

    /**
     * Called when the window is resized.
     * This is where the camera is updated to match the new window size.
     *
     * @param width  The new window width.
     * @param height The new window height.
     */
    @Override
    public void resize(int width, int height) {
        mapCamera.setToOrtho(false, (float) width / SCALE, (float) height / SCALE);
        hud.resize(width, height);

        // Update the camera to reposition correctly
        updateCamera();
    }

    // Unused methods from the Screen interface
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
