package uk.org.ulcompsoc.ld32.components.enemies;

public class Positron extends Enemy {
	public Positron() {
		speed = 0.8f * multiplier;
		health = 10f * multiplier;
		score = (int) (10 * multiplier);
		damage = (int) (10 * multiplier);

	}
}
