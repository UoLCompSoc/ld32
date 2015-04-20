package uk.org.ulcompsoc.ld32.components;

import java.util.Random;

import com.badlogic.ashley.core.Component;

public class Damage extends Component {
	private final Random rand = new Random();
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
	
	public void setDamage(float d) {
		damage = d;
	}

	public float getOriginalDamage() {
		return origDam;
	}

	public float getDamageDealt() {
		final float min = damage * 0.8f;
		final float max = damage * 1.2f;
		float random = rand.nextFloat();

		final float range = max - min;
		final float scaled = random * range;
		final float shifted = scaled + min;
		return shifted;
	}
}
