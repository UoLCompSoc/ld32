package uk.org.ulcompsoc.ld32.components;

public class Player {
	public static int score = 100;
	String name;
	
	public Player(String n) {
		name = n;
	}
	
	public void addScore(int s) {
		score = s;
	}
}
