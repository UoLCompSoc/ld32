package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Scalable extends Component {
	public float scale = 1.0f;

	public Scalable() {
		this(1.0f);
	}

	public Scalable(float scale) {
		this.scale = scale;
	}
}
