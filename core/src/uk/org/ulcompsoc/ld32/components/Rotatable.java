package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Rotatable extends Component {
	public float rotation = 0.0f; // in rads

	public Rotatable() {
		this(0.0f);
	}

	public Rotatable(float rotation) {
		this.rotation = rotation;
	}
}
