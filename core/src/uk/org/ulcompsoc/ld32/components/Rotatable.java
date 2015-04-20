package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Rotatable extends Component {
	public float rotation = 0.0f; // in rads

	public boolean matchPhi = false;

	public boolean animatedRotation = false;
	public float timeToRotate = 0.0f;
	public float animTime = 0.0f;

	public Rotatable() {
		this(0.0f);
	}

	public Rotatable(float rotation) {
		this.rotation = rotation;
	}

	// signifies that this rotation will always match the position.getPhi() of
	// the Entity
	// overrides rotation animation
	public Rotatable matchPhi() {
		this.matchPhi = true;
		return this;
	}

	public Rotatable animateRotation(float timeToRotate) {
		this.animatedRotation = true;
		this.timeToRotate = timeToRotate;
		this.animTime = 0.0f;

		return this;
	}
}
