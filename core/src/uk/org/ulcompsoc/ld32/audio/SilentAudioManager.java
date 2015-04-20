package uk.org.ulcompsoc.ld32.audio;

/**
 * Intentionally left blank so we have sweet peace and quiet while debugging.
 */
public class SilentAudioManager extends AudioManager {
	public final boolean muteAll;

	/**
	 * @param muteAll
	 *            if true, mute all sound, otherwise just mute music
	 */
	public SilentAudioManager(boolean muteAll) {
		this.muteAll = muteAll;
	}

	@Override
	public void load() {
		super.load();

		for (AudioName m : loadedAudio.keySet()) {
			if (muteAll || m.isMusic) {
				loadedAudio.get(m).setVolume(0.0f);
			}
		}
	}
}
