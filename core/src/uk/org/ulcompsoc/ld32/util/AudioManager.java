package uk.org.ulcompsoc.ld32.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class AudioManager implements Disposable {
	private static final float DEFAULT_VOLUME = 0.25f;
	// Will only work with the queue if the AudioManager is active.
	private static final boolean ACTIVE = true;

	// Hashmap allows us to play audio given the audio name, more meaningful
	private HashMap<String, Music> loadedAudio = new HashMap<String, Music>();
	private Queue<Music> playing = new LinkedList<Music>();

	public AudioManager(HashMap<String, String> files) {

		for (String key : files.keySet()) {

			Music m = Gdx.audio.newMusic(Gdx.files.internal(files.get(key)));

			m.setVolume(DEFAULT_VOLUME);
			m.setLooping(false);

			loadedAudio.put(key, m);
		}
	}

	/**
	 * Adds a sound to the queue, and plays it when it gets its turn
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	public void queue(String key) {
		if (playing.isEmpty()) {
			loadedAudio.get(key).play();
			playing.add((loadedAudio.get(key)));
		} else {
			playing.add((loadedAudio.get(key)));
		}
	}

	/**
	 * Immediately plays a sound, this will overlap currently playing audio
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	public void play(String key) {
		loadedAudio.get(key).play();
	}

	/**
	 * Loops and proceeds to play an audio track
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	public void loop(String key) {
		loadedAudio.get(key).setLooping(true);
		loadedAudio.get(key).play();
	}

	/**
	 * Immediately stops an sound
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	public void stop(String key) {
		loadedAudio.get(key).stop();
	}

	/**
	 * Stops a sound, and removes the request for it to be played in the queue,
	 * if it exists there.
	 */
	public void clear(String key) {
		stop(key);
		playing.remove(loadedAudio.get(key));
	}

	public void run() {

		if (playing.isEmpty() || playing.peek().isPlaying()) {
			System.out.println("IS EMPTY INTERL");
		} else {
			// It's done
			playing.remove(); // README used to pause here, but this
			                  // could conflict new methods

			System.out.println("IS DOING INTERL");
			// play the next
			if (!playing.isEmpty()) {
				(playing.peek()).play();
			}
		}
	}

	@Override
	public void dispose() {
		for (final Music music : loadedAudio.values()) {
			music.dispose();
		}

		loadedAudio.clear();
		playing.clear();
	}
}
