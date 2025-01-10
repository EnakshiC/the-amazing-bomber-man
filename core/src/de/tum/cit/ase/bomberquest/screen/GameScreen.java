package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.map.Path;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.map.GameMap;

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
    private  static  final float VIEW_FRAME_PERCENTAGE = 0.8f;

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
     * Constructor for GameScreen. Sets up the camera and font.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public GameScreen(BomberQuestGame game) {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();
        this.map = game.getMap();
        this.hud = new Hud(spriteBatch, game.getSkin().getFont("font"));

        // Create and configure the camera for the game view
        this.mapCamera = new OrthographicCamera();
        this.mapCamera.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);

        // Ensure the camera starts centered on the player
        updateCamera();
    }

    /**
     * The render method is called every frame to render the game.
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {
        // Check for escape key press to go back to the menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.goToMenu();
        }

        // Clear the previous frame from the screen
        ScreenUtils.clear(Color.BLACK);

        // Cap frame time to 250ms to prevent spiral of death
        float frameTime = Math.min(deltaTime, 0.250f);

        // Update the map state
        map.tick(frameTime);

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
     * {@link de.tum.cit.ase.bomberquest.screen.GameScreen#VIEW_FRAME_PERCENTAGE} set the percentage of the overall camera viewport.
     */
    private void updateCamera() {
        // Get the player's position in the map
        float playerX = map.getPlayer().getX() * TILE_SIZE_PX * SCALE;
        float playerY = map.getPlayer().getY() * TILE_SIZE_PX * SCALE;

        // Only adjust the camera if the players positions breaks out of the VIEW_FRAME_PERCENTAGE ratio of the frame
        // Since the players 0,0 is at its bottom left, we need adjust the camera accordingly on the right and top
        if (playerX < mapCamera.position.x - mapCamera.viewportWidth * VIEW_FRAME_PERCENTAGE * .5) {
            mapCamera.position.x = playerX + mapCamera.viewportWidth * VIEW_FRAME_PERCENTAGE * .5f;
        }
        else if(playerX > mapCamera.position.x + mapCamera.viewportWidth * VIEW_FRAME_PERCENTAGE * .5 - TILE_SIZE_PX) {
            mapCamera.position.x = playerX - mapCamera.viewportWidth * VIEW_FRAME_PERCENTAGE * .5f + TILE_SIZE_PX;
        }
        if (playerY < mapCamera.position.y - mapCamera.viewportHeight * VIEW_FRAME_PERCENTAGE * .5) {
            mapCamera.position.y = playerY + mapCamera.viewportHeight * VIEW_FRAME_PERCENTAGE * .5f;
        }
        else if(playerY > mapCamera.position.y + mapCamera.viewportHeight * VIEW_FRAME_PERCENTAGE * .5 - 2 * TILE_SIZE_PX) {
            mapCamera.position.y = playerY - mapCamera.viewportHeight * VIEW_FRAME_PERCENTAGE * .5f + 2 * TILE_SIZE_PX;
        }

        mapCamera.update(); // Apply camera updates
    }

    /**
     * Renders the game map using the configured camera.
     */
    private void renderMap() {
        // This configures the spriteBatch to use the camera's perspective when rendering
        spriteBatch.setProjectionMatrix(mapCamera.combined);

        // Start drawing
        spriteBatch.begin();

        // Render everything in the map here, in order from lowest to highest (later things appear on top)
        for (Drawable paths : map.getPath()) {
            draw(spriteBatch, paths);
        }
        draw(spriteBatch, map.getPlayer());

        // Finish drawing, i.e. send the drawn items to the graphics card
        spriteBatch.end();
    }

    /**
     * Draws this object on the screen.
     * The texture will be scaled by the game scale and the tile size.
     * This should only be called between spriteBatch.begin() and spriteBatch.end(), e.g. in the renderMap() method.
     * @param spriteBatch The SpriteBatch to draw with.
     */
    private static void draw(SpriteBatch spriteBatch, Drawable drawable) {
        TextureRegion texture = drawable.getCurrentAppearance();
        // Drawable coordinates are in tiles, so we need to scale them to pixels
        float x = drawable.getX() * TILE_SIZE_PX * SCALE;
        float y = drawable.getY() * TILE_SIZE_PX * SCALE;
        // Additionally scale everything by the game scale
        float width = texture.getRegionWidth() * SCALE;
        float height = texture.getRegionHeight() * SCALE;
        spriteBatch.draw(texture, x, y, width, height);
    }

    /**
     * Called when the window is resized.
     * This is where the camera is updated to match the new window size.
     * @param width The new window width.
     * @param height The new window height.
     */
    @Override
    public void resize(int width, int height) {
        mapCamera.setToOrtho(false, width / SCALE, height / SCALE);
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
