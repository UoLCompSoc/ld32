package uk.org.ulcompsoc.ld32.components;

public class Player {
	float score;
	String name;
	
	public Player(String n) {
		name = n;
	}
	
	public void addScore(float s) {
		score = s;
	}
}
