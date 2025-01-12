package de.tum.cit.ase.bomberquest.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Enum to manage sound effects for game events.
 */
public enum SoundEffect {
    BOMB_DROP("Game_PlayerDropBomb_Sound.mp3"),
    BOMB_EXPLOSION("Game_BombExplodes_Sound.mp3"),
    POWER_UP("Game_CollectPowerUp_Sound.mp3"),
    PLAYER_WIN("Game_PlayerWon_Sound.mp3"),
    PLAYER_LOSE("Game_PlayerLost_Sound.mp3");

    private final Sound sound;

    /**
     * Enum constructor to load the sound effect file.
     *
     * @param fileName The name of the sound effect file.
     */
    SoundEffect(String fileName) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/" + fileName));
    }

    /**
     * Plays the sound effect.
     *
     * @param volume The volume to play the sound effect at (0.0f to 1.0f).
     */
    public void play(float volume) {
        this.sound.play(volume);
    }

    /**
     * Stops the sound effect.
     */
    public void stop() {
        this.sound.stop();
    }
}

