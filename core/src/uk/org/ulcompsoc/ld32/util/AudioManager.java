package uk.org.ulcompsoc.ld32.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.DelayQueue;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class AudioManager extends Thread {

    private static final float DEFAULT_VOLUME = 0.25f;

    //Hashmap allows us to play audio given the audio name, more meaningful
    private HashMap<String,Music> loadedAudio = new HashMap<String,Music>();

    private Queue<Music> playing = new LinkedList<Music>();

    public AudioManager(HashMap<String, String> files) {

        for(String key : files.keySet()) {

            Music m = Gdx.audio.newMusic(Gdx.files.internal(files.get(key)));

            m.setVolume(DEFAULT_VOLUME);
            m.setLooping(false);

            loadedAudio.put(key, m);
        }
    }

    public void queue(String key) {
        if(playing.isEmpty()) {
            loadedAudio.get(key).play();
            playing.add((loadedAudio.get(key)));

        } else {
            playing.add((loadedAudio.get(key)));
        }

    }


    @Override
    public void run() {
        try {
            while(true) {
                if(playing.isEmpty() || playing.peek().isPlaying()) {
                    Thread.sleep(50);
                } else {
                    //It's done
                    playing.remove().pause();

                    //play the next
                    if (!playing.isEmpty()) {
                        (playing.peek()).play();
                    }
                }

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }






}
