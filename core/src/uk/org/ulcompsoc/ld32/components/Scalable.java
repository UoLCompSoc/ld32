package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Scalable extends Component {
	public final float baseScale;
	public float scale = 1.0f;

	public float totalAnimTime = 0.25f;
	public float timeElapsed = 0.0f;

	public Scalable() {
		this(1.0f);
	}

	public Scalable(float scale) {
		this.scale = scale;
		this.baseScale = scale;
	}
}
