package uk.org.ulcompsoc.ld32.components;

import uk.org.ulcompsoc.ld32.util.LDUtil;

import com.badlogic.ashley.core.Component;

public class PaddleInputListener extends Component {
	public final int[] leftKeys;
	public final int[] rightKeys;
	public final int[] fireKeys;

	public static final float MAX_VELOCITY = LDUtil.PI / 25;
	public static final float MAX_SPEED_TIME = 1.5f;

	/* when pressTime >= maxSpeedTime, velocity should be maxVelocity */
	public float pressTime = 0.0f;
	public float velocity = 0.0f;

	public PaddleInputListener(int leftKey, int rightKey, int fireKeys) {
		this.leftKeys = new int[1];
		this.rightKeys = new int[1];
		this.fireKeys = new int [1];

		this.leftKeys[0] = leftKey;
		this.rightKeys[0] = rightKey;
		this.fireKeys[0] = fireKeys;
	}

	public PaddleInputListener(final int[] leftKeys, final int[] rightKeys, final int [] fireKeys) {
		this.leftKeys = leftKeys;
		this.rightKeys = rightKeys;
		this.fireKeys = fireKeys;
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
