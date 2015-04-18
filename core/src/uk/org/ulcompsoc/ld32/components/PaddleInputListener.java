package uk.org.ulcompsoc.ld32.components;

import uk.org.ulcompsoc.ld32.util.LDUtil;

import com.badlogic.ashley.core.Component;

public class PaddleInputListener extends Component {
	public final int[] leftKeys;
	public final int[] rightKeys;

	public static final float MAX_VELOCITY = LDUtil.PI / 50;
	public static final float MAX_SPEED_TIME = 3.0f;

	/* when pressTime >= maxSpeedTime, velocity should be maxVelocity */
	public float pressTime = 0.0f;
	public float velocity = 0.0f;

	public PaddleInputListener(int leftKey, int rightKey) {
		this.leftKeys = new int[1];
		this.rightKeys = new int[1];

		this.leftKeys[0] = leftKey;
		this.rightKeys[0] = rightKey;
	}

	public PaddleInputListener(final int[] leftKeys, final int[] rightKeys) {
		this.leftKeys = leftKeys;
		this.rightKeys = rightKeys;
	}

	public void setVelocity(float velocity) {
		if (velocity <= 0.0f) {
			this.velocity = 0.0f;
		} else if (velocity > MAX_VELOCITY) {
			this.velocity = MAX_VELOCITY;
		} else {
			this.velocity = velocity;
		}
	}
}
