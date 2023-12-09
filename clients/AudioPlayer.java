package clients;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class AudioPlayer {
    File file = new File("music/hipjazz.wav"); // path to WAV file
    AudioInputStream audioStream;
    Clip clip;
    public void audioPlayer() {
        try {
            audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // start music
        } catch (UnsupportedAudioFileException e) { // catch errors
            throw new RuntimeException(e);
        } catch (IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }
    public void pausePlayer(){
        clip.stop();
    }

}
