package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.tum.cit.ase.bomberquest.map.GameMap;
import de.tum.cit.ase.bomberquest.texture.Animations;
import de.tum.cit.ase.bomberquest.texture.Textures;
import org.w3c.dom.Text;

/**
 * A Heads-Up Display (HUD) that displays information on the screen.
 * It uses a separate camera so that it is always fixed on the screen.
 */
public class Hud {

    private final SpriteBatch spriteBatch;
    private final BitmapFont font;
    private final OrthographicCamera camera;
    private final GameMap gameMap;
    private final ShapeRenderer shapeRenderer;

    public static final int HEIGHT = 50;

    /**
     * Constructs the HUD object with the given SpriteBatch and font.
     *
     * @param spriteBatch The SpriteBatch used to render HUD elements.
     * @param font        The font used for displaying text on the HUD.
     */
    public Hud(SpriteBatch spriteBatch, BitmapFont font, GameMap gameMap) {
        this.spriteBatch = spriteBatch;
        this.font = font;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.gameMap = gameMap;
        this.shapeRenderer = new ShapeRenderer();
    }

    private String getMinutes(float time) {
        return String.valueOf((int) time / 60);
    }

    private String getSeconds(float time) {
        int seconds = (int) time % 60;
        return seconds < 10 ? "0" + seconds : String.valueOf(seconds);
    }

    /**
     * Renders the HUD on the screen.
     */
    public void render() {
        // Render background first
        renderBackground();

        // Render from the camera's perspective
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        // Define horizontal layout
        int x = 100; // Starting x-coordinate
        final int y = Gdx.graphics.getHeight() - HEIGHT / 2; // Fixed y-coordinate for the HUD
        final int iconSize = 32;
        final int iconPadding = 10;
        final int elementPadding = 100;

        // Concurrent bombs allowed
        spriteBatch.draw(Textures.POWER_UP_CONCURRENT_BOMBS, x, y - (float) iconSize / 2, iconSize, iconSize);
        x += iconSize + iconPadding;
        font.draw(spriteBatch, ": " + gameMap.getMaxBombsAllowed(), x, y + 10f);

        // Current bomb blast radius
        x += elementPadding;
        spriteBatch.draw(Textures.POWER_UP_BOMB_RADIUS, x, y - (float) iconSize / 2, iconSize, iconSize);
        x += iconSize + iconPadding;
        font.draw(spriteBatch, ": " + gameMap.getBombRadius(), x, y + 10f);

        // Enemy counter
        x += elementPadding;
        spriteBatch.draw(Animations.GHOST_ENEMY_STANDING.getKeyFrame(0.0f), x, y - (float) iconSize / 2, iconSize, iconSize);
        x += iconSize + iconPadding;
        int enemiesAtStart = gameMap.getEnemiesCountAtBeginning();
        int currentEnemies = gameMap.getEnemies().size();

        if (currentEnemies == 0) {
            font.draw(spriteBatch, "Exit open!", x, y + 10f);
        } else {
            font.draw(spriteBatch, ": " + (enemiesAtStart - currentEnemies) + "/" + enemiesAtStart, x, y + 10f);
        }

        // Enemy counter
        x += (int) (elementPadding * 1.7);
        spriteBatch.draw(Textures.POWER_UP_EXTRA_SPEED, x, y - (float) iconSize / 2, iconSize, iconSize);
        x += iconSize + iconPadding;
        font.draw(spriteBatch, " : " + (gameMap.getPlayer().getCurrentSpeed()), x, y + 10f);

        x += (int) (elementPadding * 1.2);
        font.draw(spriteBatch, "Time Left: " + getMinutes(gameMap.getTimeLeft()) + ":" + getSeconds(gameMap.getTimeLeft()), x, y + 10f);

        spriteBatch.end();
    }

    /**
     * Renders the semi-transparent background for the HUD.
     */
    private void renderBackground() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND); // Enable transparency
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.8f)); // 80% black
        shapeRenderer.rect(0, Gdx.graphics.getHeight() - HEIGHT, Gdx.graphics.getWidth(), HEIGHT);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND); // Disable transparency
    }

    /**
     * Resizes the HUD when the screen size changes.
     */
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    /**
     * Disposes resources used by the HUD.
     */
    public void dispose() {
        shapeRenderer.dispose();
    }
}