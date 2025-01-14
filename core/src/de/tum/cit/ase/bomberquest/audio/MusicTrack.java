package de.tum.cit.ase.bomberquest.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import java.util.Random;

/**
 * This enum is used to manage the music tracks in the game.
 * It supports multiple tracks for gameplay and a single track for the menu.
 * Music playback is controlled to make sure only one track is playing at a time.
 */
public enum MusicTrack {
    MENU("GameMenuSound1.ogg"),
    GAMEPLAY_1("GameBGSound1.ogg"),
    GAMEPLAY_2("GameBGSound2.ogg"),
    GAMEPLAY_3("GameBGSound3.ogg");

    /** The music file owned by this variant. */
    private final Music music;

    /**
     * Enum constructor to load the music file and set its properties.
     * @param fileName to store the name of the music file.
     * @param volume   to store the volume level of the music.
     */
    
    MusicTrack(String fileName, float volume) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("audio/" + fileName));
        this.music.setLooping(true);
        this.music.setVolume(volume);
    }

    /**
     * Enum constructor to load the music file and set its volume to .1f.
     * @param fileName to store the name of the music file.
     */
    MusicTrack(String fileName) {
        this(fileName, 0.1f);
    }

    /**
     * Play this music track, stopping any other currently playing music.
     */
    public void play() {
        stopAll(); // Stop all other tracks
        this.music.play();
    }

    /**
     * Stops this specific music track.
     */
    public void stop() {
        if (this.music.isPlaying()) {
            this.music.stop();
        }
    }

    /**
     * Stops all music tracks.
     */
    public static void stopAll() {
        for (MusicTrack track : values()) {
            track.stop();
        }
    }

    /**
     * Play a random gameplay music track.
     */
    public static void playRandomGameplayTrack() {
        MusicTrack[] gameplayTracks = {GAMEPLAY_1, GAMEPLAY_2, GAMEPLAY_3};
        Random random = new Random();
        gameplayTracks[random.nextInt(gameplayTracks.length)].play();
    }
}
