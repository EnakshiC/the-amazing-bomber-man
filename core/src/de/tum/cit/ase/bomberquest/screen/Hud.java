package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A Heads-Up Display (HUD) that displays information on the screen.
 * It uses a separate camera so that it is always fixed on the screen.
 */
public class Hud {
    
    /** The SpriteBatch used to draw the HUD. This is the same as the one used in the GameScreen. */
    private final SpriteBatch spriteBatch;
    /** The font used to draw text on the screen. */
    private final BitmapFont font;
    /** The camera used to render the HUD. */
    private final OrthographicCamera camera;

    // New properties for HUD display
    /** Current bomb count placed by the player. */
    private int currentBombCount;

    /** Maximum bombs the player can place concurrently. */
    private int maxBombCount;

    /** Current blast radius of the player's bombs. */
    private int currentBlastRadius;

    /** Number of enemies left in the game. */
    private int remainingEnemies;

    /** Countdown timer for the game (in seconds). */
    private float countdownTimer;


    /**
     * Constructs the HUD object with the given SpriteBatch and font.
     *
     * @param spriteBatch The SpriteBatch used to render HUD elements.
     * @param font The font used for displaying text on the HUD.
     */

    public Hud(SpriteBatch spriteBatch, BitmapFont font) {
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Updates the HUD with the latest gameplay state.
     *
     * @param BombsInPlay Current bomb count.
     * @param maxBombs Maximum bombs the player can place concurrently.
     * @param blastRadius Current bomb blast radius.
     * @param enemiesLeft Number of remaining enemies.
     * @param timer Current countdown timer in seconds.

    public void updateHUD(GameMap map, float timer) {
        updateHUD(
                map.getBombsInPlay().size(),               // Current bomb count
                map.getPlayer().getMaxBombCount(),         // Maximum bombs
                map.getPlayer().getBlastRadius(),          // Current bomb blast radius
                map.getRemainingEnemies(),                 // Number of remaining enemies
                timer                                      // Countdown timer
        );

    } */

    /**
     * Renders the HUD on the screen.
     * This uses a different OrthographicCamera so that the HUD is always fixed on the screen.
     */
    public void render() {
        // Render from the camera's perspective
        spriteBatch.setProjectionMatrix(camera.combined);
        // Start drawing
        spriteBatch.begin();
        // Add consistent vertical spacing between HUD elements
        int padding = 20; // Vertical spacing between lines
        int startY = Gdx.graphics.getHeight() - 10; // Start from the top with some padding

        // Draw the HUD elements
        font.draw(spriteBatch, "Press Esc to Pause!", 10, startY);
        font.draw(spriteBatch, "Bombs: " + currentBombCount + " / " + maxBombCount, 10, startY - padding);
        font.draw(spriteBatch, "Blast Radius: " + currentBlastRadius, 10, startY - 2 * padding);
        font.draw(spriteBatch, "Enemies Left: " + remainingEnemies, 10, startY - 3 * padding);
        font.draw(spriteBatch, "Timer: " + String.format("%.1f", countdownTimer), 10, startY - 4 * padding);

        // Finish drawing
        spriteBatch.end();
    }
    
    /**
     * Resizes the HUD when the screen size changes.
     * This is called when the window is resized.
     * @param width The new width of the screen.
     * @param height The new height of the screen.
     */
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }
    
}
