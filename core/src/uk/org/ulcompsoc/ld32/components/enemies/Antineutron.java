package uk.org.ulcompsoc.ld32.components.enemies;

public class Antineutron extends Enemy {
	public Antineutron() {
		health = 30f * multiplier;
		speed = 1.5f * multiplier;
		score = (int) (10 * multiplier);
	}
}
