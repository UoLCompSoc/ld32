package uk.org.ulcompsoc.ld32.components;

public class Nexus {
	
	private static float health = 100f;
	public static boolean dead = false;
	
	Nexus() {
		
	}
	
	public static void reduceHealth(float h) {
		health = health - h;
		if(health <= 0) {
			health = 0;
			dead = true;
			System.out.println("YOU HAVE LOST");
		}
	}
	
	public static float getHealth() {
		return health;
	}
	
	
}
