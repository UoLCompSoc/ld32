package uk.org.ulcompsoc.ld32.components.enemies;

import com.badlogic.ashley.core.Component;

/**
 * Created by Samy Narrainen on 19/04/2015. Description: Necessary as it
 * provides the BasicFiringSystem a means of detecting enemies.
 */
public abstract class Enemy extends Component {
	public static final float EASY = 0.5f;
	public static final float NORMAL = 1f;
	public static final float HARD = 1.5f;
	//segments per second
	public float speed;
	public float health;
	
	static float multiplier = NORMAL;
	
	public float score;
	public Enemy() {
	}

	public static void setMultiplier(float m) {
		multiplier = m;
	}
}
