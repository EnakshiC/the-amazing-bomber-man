package de.tum.cit.ase.bomberquest.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import de.tum.cit.ase.bomberquest.BomberQuestGame;

public class GameEndedScreen implements Screen {

/**
 * This class is meant to display an end screen with a message based on the game outcome
 * (Victory or Loss) and provides player with option to return to game menu
 */
    private final BomberQuestGame game;
    private final SpriteBatch spriteBatch;
    private final BitmapFont font;
    private final GlyphLayout layout;
    private final boolean victory;

    /**
     * Constructs GameEndScreen with an instance of the main game & outcome
     *
     * @param game main game instance
     * @param victory - indicator for victory screen (true) or loss screen (false)
     */
    public GameEndedScreen(BomberQuestGame game, boolean victory)
    {
        this.game=game;
        this.spriteBatch = game.getSpriteBatch();
        this.font = game.getSkin().getFont("font");
        this.layout = new GlyphLayout();
        this.victory=victory;
    }

    @Override
    public void show() {

    }
    /**
     * Renders the game-ended screen, displaying the outcome message and instructions.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void render(float deltaTime)
    {
        //Setting screen color based on game outcome
        Color backgroundColor = victory ? new Color(0.2f, 0.8f, 0.2f, 1): new Color(0.8f, 0.2f, 0.2f, 1);
        ScreenUtils.clear(backgroundColor);

        spriteBatch.begin();

        //Setting messages based on the game outcome
        String mainMessage = victory ? "Victory!" : "Game Over!";
        String instructionMessage = "Press Enter to Return to Main Menu";

        float centerX = Gdx.graphics.getWidth()/2f;
        float centerY = Gdx.graphics.getHeight()/2f;

        //Measuring text width for proper centering
        layout.setText(font, mainMessage);
        float mainMessageWidth = layout.width;

        layout.setText(font, instructionMessage);
        float instructionMessageWidth = layout.width;

        //Drawing the messages
        font.draw(spriteBatch, mainMessage, centerX - mainMessageWidth / 2, centerY + 20);
        font.draw(spriteBatch, instructionMessage, centerX - instructionMessageWidth / 2, centerY - 20);

        spriteBatch.end();

        //Checking for player's ENTER input
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            game.goToMenu();
        }
    }

    @Override
    public void resize(int width, int height){

    }
    @Override
    public void pause()
    {

    }

    @Override
    public void resume(){

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose(){

    }


}