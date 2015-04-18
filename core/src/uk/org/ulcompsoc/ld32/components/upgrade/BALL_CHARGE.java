package uk.org.ulcompsoc.ld32.components.upgrade;

public class Ball_Charge extends Upgradable {
		
	public final float dmg;
	public final float time;
	public final float drops;
	public final float costs;
	public final int simultanousFire;
	
	public Ball_Charge() {
		super();
		dmg = 1.1f;
		time = 1;
		drops = 1;
		costs = 1;
		simultanousFire = 0;
		stage = 2;
		
	}
}
