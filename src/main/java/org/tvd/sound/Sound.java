package org.tvd.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {

    private Clip clip;
    private final URL[] soundUrl = new URL[30];

    public Sound() {

        soundUrl[0] = getClass().getResource("/sound/background.wav");
        soundUrl[1] = getClass().getResource("/sound/fanfare.wav");
        soundUrl[2] = getClass().getResource("/sound/gameover.wav");
        soundUrl[3] = getClass().getResource("/sound/hitmonster.wav");
        soundUrl[4] = getClass().getResource("/sound/levelup.wav");
        soundUrl[5] = getClass().getResource("/sound/powerup.wav");
        soundUrl[6] = getClass().getResource("/sound/receivedamage.wav");
        soundUrl[7] = getClass().getResource("/sound/coin.wav");
        soundUrl[8] = getClass().getResource("/sound/button.wav");
        soundUrl[9] = getClass().getResource("/sound/pick.wav");
    }

    public void setFile(int index) {

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[index]);

            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e ) {
            System.err.println("Sound exception: " + e.getMessage());
        }
    }

    public void start() {

        if (clip != null) clip.start();
    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {

        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
    }

    public void play(int index) {

        setFile(index);
        start();
        loop();
    }

    public void playSE(int index) {

        Sound sound = new Sound();
        sound.setFile(index);
        sound.start();
    }
}
