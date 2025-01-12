package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.map.Bomb;
import de.tum.cit.ase.bomberquest.map.BombExplosion;
import de.tum.cit.ase.bomberquest.texture.Drawable;
import de.tum.cit.ase.bomberquest.map.GameMap;

public class LossScreen implements Screen
    {
        private final BomberQuestGame game;
        private final SpriteBatch spriteBatch;
        private final BitmapFont font;
        private final GlyphLayout layout; // Used for text measurement


        public LossScreen(BomberQuestGame game) {
        this.game = game;
        this.spriteBatch = game.getSpriteBatch();
        this.font = game.getSkin().getFont("font");
            this.layout = new GlyphLayout();
        }

        @Override
        public void show() {

    }

        @Override
        public void render(float deltaTime) {
            // To clear the screen with a red color to indicate loss
            //ScreenUtils.clear(Color.RED);
            ScreenUtils.clear(new Color(0.8f, 0.2f, 0.2f, 1));

            spriteBatch.begin();
            System.out.println("SpriteBatch.begin() called");

            // Center the text dynamically
            String gameOverMessage = "Game Over!";
            String instructionMessage = "Press Enter to Return to Main Menu";
            float centerX = Gdx.graphics.getWidth() / 2f;
            float centerY = Gdx.graphics.getHeight() / 2f;

            // Measure text width for proper centering
            layout.setText(font, gameOverMessage);
            float gameOverMessageWidth = layout.width;

            layout.setText(font, instructionMessage);
            float instructionMessageWidth = layout.width;

            font.draw(spriteBatch, gameOverMessage, centerX - gameOverMessageWidth / 2, centerY + 20);
            font.draw(spriteBatch, instructionMessage, centerX - instructionMessageWidth / 2, centerY - 20);

            spriteBatch.end();
            System.out.println("SpriteBatch.end() called");


            // Check if the player presses Enter to return to the menu
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                System.out.println("Returning to main menu from Loss Screen.");
                game.goToMenu();
            }
        }

        @Override
        public void resize(int i, int i1) {

    }

        @Override
        public void pause() {

    }

        @Override
        public void resume() {

    }

        @Override
        public void hide() {

    }

        @Override
        public void dispose() {

    }
}

