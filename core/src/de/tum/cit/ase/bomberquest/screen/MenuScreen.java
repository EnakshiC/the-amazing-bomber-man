package de.tum.cit.ase.bomberquest.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.tum.cit.ase.bomberquest.BomberQuestGame;
import de.tum.cit.ase.bomberquest.utils.PropertiesHelper;
import games.spooky.gdx.nativefilechooser.NativeFileChooserCallback;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import games.spooky.gdx.nativefilechooser.NativeFileChooserIntent;

import java.util.ArrayList;
import java.util.List;

/**
 * The MenuScreen class is responsible for displaying the main menu of the game.
 * It extends the LibGDX Screen class and sets up the UI components for the menu.
 */
public class MenuScreen implements Screen {

    private final Stage stage;
    private final BomberQuestGame game;

    /**
     * The key of the currently selected map
     * Is "<Custom Map>" if a file was selected
     */
    private String currentKey;

    // All buttons of the MenuScreen
    // ... as local variables since we need to edit them in render() according to the current game running or not
    private final TextButton goToGameButton;
    private final TextButton endGameButton;
    private final TextButton previousMapButton;
    private final TextButton nextMapButton;
    private final TextButton loadMapButton;
    private final TextButton previousEnemySettingButton;
    private final TextButton nextEnemySettingButton;


    /**
     * Constructor for MenuScreen. Sets up the camera, viewport, stage, and UI elements.
     *
     * @param game The main game class, used to access global resources and methods.
     */
    public MenuScreen(BomberQuestGame game) {
        currentKey = "Map 1";// PropertiesHelper.getMapPaths().keySet().iterator().next();
        var camera = new OrthographicCamera();
        camera.zoom = 1.5f; // Set camera zoom for a closer view

        Viewport viewport = new ScreenViewport(camera); // Create a viewport with the camera
        stage = new Stage(viewport, game.getSpriteBatch()); // Create a stage for UI elements

        Table table = new Table(); // Create a table for layout
        table.setFillParent(true); // Make the table fill the stage
        stage.addActor(table); // Add the table to the stage

        // Add a label as a title
        table.add(new Label("The Amazing Bomber Man", game.getSkin(), "title")).padBottom(80).row();

        // Create and add a button to go to the game screen
        // It continues the game if it is running or starts it, if it is a new game
        goToGameButton = new TextButton("Start Game", game.getSkin());
        table.add(goToGameButton).width(450).padBottom(20).row();
        goToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.goToGame(); // Change to the game screen when button is pressed
            }
        });

        endGameButton = new TextButton("End Game", game.getSkin());
        table.add(endGameButton).width(450).padBottom(20).row();
        endGameButton.setDisabled(!game.hasStarted());
        endGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.resetGame(); // Change to the game screen when button is pressed
            }
        });

        // ITERATE THROUGH EXISTING MAPS
        // Add a label and two buttons for iterating through the map
        Label mapLabel = new Label(currentKey, game.getSkin());
        previousMapButton = new TextButton("<", game.getSkin(), "default");
        nextMapButton = new TextButton(">", game.getSkin(), "default");

        Table rowTable = new Table();
        rowTable.add(previousMapButton).size(60).padRight(20);
        rowTable.add(mapLabel).padRight(20).width(290);
        rowTable.add(nextMapButton).size(60);
        table.add(rowTable).padBottom(20).row();

        previousMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                List<String> keys = new ArrayList<>(PropertiesHelper.getMapPaths().keySet());
                int currentIndex = keys.contains(currentKey) ? keys.indexOf(currentKey) : 1;
                currentKey = keys.get((currentIndex - 1 + keys.size()) % keys.size());
                mapLabel.setText(currentKey);
                PropertiesHelper.loadNewMap(PropertiesHelper.getMapPaths().get(currentKey));
                game.resetGame();
            }
        });

        nextMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                List<String> keys = new ArrayList<>(PropertiesHelper.getMapPaths().keySet());
                int currentIndex = keys.contains(currentKey) ? keys.indexOf(currentKey) : -1;
                currentKey = keys.get((currentIndex + 1) % keys.size());
                mapLabel.setText(currentKey);
                PropertiesHelper.loadNewMap(PropertiesHelper.getMapPaths().get(currentKey));
                game.resetGame();
            }
        });

        // SELECT ENEMY SETTINGS
        // Add a label and two buttons for iterating through the different enemy settings
        Label enemySettingLabel = new Label(PropertiesHelper.getCurrentEnemySetting(), game.getSkin());
        previousEnemySettingButton = new TextButton("<", game.getSkin(), "default");
        nextEnemySettingButton = new TextButton(">", game.getSkin(), "default");

        Table row2Table = new Table();
        row2Table.add(previousEnemySettingButton).size(60).padRight(20);
        row2Table.add(enemySettingLabel).padRight(20).width(290);
        row2Table.add(nextEnemySettingButton).size(60);
        table.add(row2Table).padBottom(20).row();

        previousEnemySettingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PropertiesHelper.previousEnemySetting();
                enemySettingLabel.setText(PropertiesHelper.getCurrentEnemySetting());
                game.resetGame();
            }
        });

        nextEnemySettingButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PropertiesHelper.nextEnemySetting();
                enemySettingLabel.setText(PropertiesHelper.getCurrentEnemySetting());
                game.resetGame();
            }
        });

        // Create and add a button to open the file loader
        loadMapButton = new TextButton("Load map from file...", game.getSkin());
        table.add(loadMapButton).width(450).row();
        loadMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                NativeFileChooserConfiguration chooserConfig = new NativeFileChooserConfiguration();
                chooserConfig.title = "Choose Map File";
                chooserConfig.directory = new FileHandle(System.getProperty("user.home"));
                chooserConfig.intent = NativeFileChooserIntent.OPEN;

                NativeFileChooserCallback callback = new NativeFileChooserCallback() {
                    @Override
                    public void onFileChosen(FileHandle fileHandle) {
                        System.out.println("Chosen File: " + fileHandle.path());

                        // Check if it is a .properties File
                        if (fileHandle.extension().equals("properties")) {
                            PropertiesHelper.loadNewMap(fileHandle.path());
                            game.resetGame();
                            currentKey = "< CUSTOM MAP >";
                            mapLabel.setText(currentKey);
                            game.goToGame();
                        }
                    }

                    @Override
                    public void onCancellation() {
                        // System.out.println("Cancel Choose File");
                    }

                    @Override
                    public void onError(Exception e) {
                        System.err.println("Error: " + e);
                    }
                };
                chooserConfig.title = "Chose Map File";
                game.getFileChooser().chooseFile(chooserConfig, callback);
            }
        });

        this.game = game;
    }

    /**
     * The render method is called every frame to render the menu screen.
     * It clears the screen and draws the stage.
     *
     * @param deltaTime The time in seconds since the last render.
     */
    @Override
    public void render(float deltaTime) {
        float frameTime = Math.min(deltaTime, 0.250f); // Cap frame time to 250ms to prevent spiral of death        ScreenUtils.clear(Color.BLACK);
        ScreenUtils.clear(Color.BLACK);
        stage.act(frameTime); // Update the stage
        stage.draw();// Draw the stage

        //Start the game on ENTER
        // Pressing SPACE car drops a bomb
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) game.goToGame();

        // Update the buttons based on if the game is running or not
        goToGameButton.setText(game.hasStarted() ? "Continue Game" : "Start Game");
        endGameButton.setDisabled(!game.hasStarted());
        previousMapButton.setDisabled(game.hasStarted());
        nextMapButton.setDisabled(game.hasStarted());
        loadMapButton.setDisabled(game.hasStarted());
        nextEnemySettingButton.setDisabled(game.hasStarted());
        previousEnemySettingButton.setDisabled(game.hasStarted());

    }

    /**
     * Resize the stage when the screen is resized.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update the stage viewport on resize
    }

    @Override
    public void dispose() {
        // Dispose of the stage when screen is disposed
        stage.dispose();
    }

    @Override
    public void show() {
        // Set the input processor so the stage can receive input events
        Gdx.input.setInputProcessor(stage);
    }

    // The following methods are part of the Screen interface but are not used in this screen.
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
