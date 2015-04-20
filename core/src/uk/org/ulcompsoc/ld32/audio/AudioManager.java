package uk.org.ulcompsoc.ld32.audio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class AudioManager implements IAudioManagement, OnCompletionListener {
	private HashMap<AudioName, Music> loadedAudio = new HashMap<AudioName, Music>();
	private Queue<Music> playing = new LinkedList<Music>();

	public AudioManager() {
	}

	@Override
	public void load() {
		for (AudioName audioName : AudioName.values()) {
			final Music m = Gdx.audio.newMusic(Gdx.files.internal(audioName.fileName));

			m.setVolume(audioName.volume);
			m.setLooping(audioName.loopByDefault);
			m.setOnCompletionListener(this);

			loadedAudio.put(audioName, m);
		}
	}

	/**
	 * Adds a sound to the queue, and plays it when it gets its turn
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	@Override
	public void queue(AudioName audioName) {
		if (playing.isEmpty()) {
			loadedAudio.get(audioName).play();
			playing.add(loadedAudio.get(audioName));
		} else {
			playing.add(loadedAudio.get(audioName));
		}
	}

	/**
	 * Immediately plays a sound, this will overlap currently playing audio
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	@Override
	public void play(AudioName key) {
		loadedAudio.get(key).play();
	}

	/**
	 * Loops and proceeds to play an audio track
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	@Override
	public void loop(AudioName key) {
		loadedAudio.get(key).setLooping(true);
		loadedAudio.get(key).play();
	}

	/**
	 * Immediately stops an sound
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	@Override
	public void stop(AudioName key) {
		loadedAudio.get(key).stop();
	}

	/**
	 * Stops a sound, and removes the request for it to be played in the queue,
	 * if it exists there.
	 */
	@Override
	public void clear(AudioName key) {
		stop(key);
		playing.remove(loadedAudio.get(key));
	}

	public void run() {
		if (playing.isEmpty() || playing.peek().isPlaying()) {

		} else {
			// It's done
			playing.remove();
			// README used to pause here, but this could conflict new methods

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

	@Override
	public void onCompletion(Music music) {
		playing.remove(music);
	}
}
