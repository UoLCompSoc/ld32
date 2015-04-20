package uk.org.ulcompsoc.ld32.components.enemies;

public class Antiproton extends Enemy {
	public Antiproton() {
		health = 30f * multiplier;
		speed = 0.3f * multiplier;
		score = (int) (10 * multiplier);
		damage = (int) (10 * multiplier);

	}
}
