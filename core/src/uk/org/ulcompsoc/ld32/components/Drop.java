package uk.org.ulcompsoc.ld32.components;

import java.util.Random;

import com.badlogic.ashley.core.Component;

public class Drop extends Component{
	
	private static final float DFLT_RED_DROP = 0.2f;
	private static final float DFLT_BLUE_DROP = 0.2f;
	private static final float DFLT_GREEN_DROP = 0.2f;
	
	public float redDropChance;
	public float blueDropChance;
	public float greenDropChance;
	
	private float redChanceBooster;
	private float blueChanceBooster;
	private float greenChanceBooster;
	
	public Drop(){
		this.redDropChance = Drop.DFLT_RED_DROP;
		this.blueDropChance = Drop.DFLT_BLUE_DROP;
		this.greenDropChance = Drop.DFLT_GREEN_DROP;
		
		redChanceBooster = 0;
		blueChanceBooster = 0;
		greenChanceBooster = 0;
	}
	
}
