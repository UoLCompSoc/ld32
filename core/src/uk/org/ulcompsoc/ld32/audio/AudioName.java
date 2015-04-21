package uk.org.ulcompsoc.ld32.audio;

public enum AudioName {
	ABSTRACTION("data/abstraction.ogg", true, true, 0.3f), //
	DROP("data/drop.mp3"), //
	WOOSH("data/woosh.mp3"), //
	POPO("data/popo.mp3", false, false, 0.2f);

	public static final float DEFAULT_VOLUME = 0.50f;

	public final String fileName;
	public final boolean isMusic;

	public final boolean loopByDefault;
	public final float volume;

	private AudioName(String fileName) {
		this(fileName, false, false, DEFAULT_VOLUME);
	}

	private AudioName(String fileName, boolean isMusic) {
		this(fileName, isMusic, false, DEFAULT_VOLUME);
	}

	private AudioName(String fileName, boolean isMusic, boolean loopByDefault, float defaultVolume) {
		this.fileName = fileName;
		this.isMusic = isMusic;
		this.loopByDefault = loopByDefault;
		this.volume = defaultVolume;
	}
}
