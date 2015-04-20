package uk.org.ulcompsoc.ld32.audio;

import com.badlogic.gdx.utils.Disposable;

public interface IAudioManagement extends Disposable {
	/**
	 * Must be called after the constructor of the application listener
	 */
	public void load();

	/**
	 * Adds a sound to the queue, and plays it when it gets its turn
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	public void queue(AudioName key);

	/**
	 * Immediately plays a sound, this will overlap currently playing audio
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	public void play(AudioName key);

	/**
	 * Loops and proceeds to play an audio track
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	public void loop(AudioName key);

	/**
	 * Immediately stops an sound
	 * 
	 * @param key
	 *            , the key for the sound
	 */
	public void stop(AudioName key);

	/**
	 * Stops a sound, and removes the request for it to be played in the queue,
	 * if it exists there.
	 */
	public void clear(AudioName key);

	@Override
	public void dispose();
}
