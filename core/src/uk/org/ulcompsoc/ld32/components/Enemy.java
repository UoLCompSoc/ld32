package uk.org.ulcompsoc.ld32.components;

import java.util.Random;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade;

import com.badlogic.ashley.core.Component;

/**
 * Created by Samy Narrainen on 19/04/2015.
 * Description: Necessary as it provides the BasicFiringSystem a means of detecting enemies.
 */
public abstract class Enemy extends Component {
	
	public float speed;
	public float health;
	
	// Can be used to increase game difficulty
	static float multiplier = 1;
	
	public Enemy() {
	
	}
	
	public void setMultiplier(float m) {
		multiplier = m;
	}
}
