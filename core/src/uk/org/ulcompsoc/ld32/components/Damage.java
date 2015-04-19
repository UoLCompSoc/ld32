package uk.org.ulcompsoc.ld32.components;

import java.util.Random;

import com.badlogic.ashley.core.Component;

public class Damage extends Component {
	
	private float damage;
	private final float origDam;
	
	public Damage(float dam) {
		damage = dam;
		origDam = dam;
	}
	
	public void useMultiplier(float multiplier) {
		damage = damage * multiplier;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public float getOriginalDamage() {
		return origDam;
	}
	
	public float getDamageDealt() {
		float min = damage * 0.8f;
		float max = damage * 1.2f;
		Random rand = new Random();
		float random = rand.nextFloat();
		
		float range = max - min;
		float scaled = random * range;
		float shifted = scaled + min;
		return shifted;
	}
}
