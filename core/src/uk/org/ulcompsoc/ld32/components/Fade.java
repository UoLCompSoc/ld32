package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Fade extends Component {
	public float targetAlpha = 0.0f;

	public float fadeTime = 2.0f;
	public float timeElapsed = 0.0f;

	public boolean doomAfterFade = false;

	public Fade() {
		this(2.0f, false);
	}

	public Fade(float fadeTime) {
		this(fadeTime, false);
	}

	public Fade(float fadeTime, boolean doomAfterFade) {
		this.fadeTime = fadeTime;
		this.doomAfterFade = doomAfterFade;
	}

	public Fade fadeTo(float targetAlpha) {
		if (targetAlpha < 0.0f) {
			targetAlpha = 0.0f;
		} else if (targetAlpha > 1.0f) {
			targetAlpha = 1.0f;
		}

		this.targetAlpha = targetAlpha;
		return this;
	}

	public Fade doomAfterFade() {
		this.doomAfterFade = true;
		return this;
	}
}
