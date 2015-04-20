package uk.org.ulcompsoc.ld32.components.enemies;

public class Antineutron extends Enemy {
	public Antineutron() {
		health = 20f * multiplier;
		speed = 0.5f * multiplier;
		score = (int) (10 * multiplier);
		damage = (int) (10 * multiplier);
	}
}
