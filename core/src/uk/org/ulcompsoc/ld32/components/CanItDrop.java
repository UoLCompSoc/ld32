package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class CanItDrop extends Component {

	private static final float DFLT_RED_DROP_CHANCE = 0.2f;
	private static final float DFLT_BLUE_DROP_CHANCE = 0.2f;
	private static final float DFLT_GREEN_DROP_CHANCE = 0.2f;
	
	public float redDropChance;
	public float blueDropChance;
	public float greenDropChance;
	
	public float redBooster; // if doesn't drop, increase slightly - so player doesn't get unlucky streak
	public float blueBooster;
	public float greenBooster;
	
	
	public CanItDrop(){
		this.redDropChance = CanItDrop.DFLT_RED_DROP_CHANCE;
		this.blueDropChance = CanItDrop.DFLT_BLUE_DROP_CHANCE;
		this.greenDropChance = CanItDrop.DFLT_GREEN_DROP_CHANCE;
		
		redBooster = 0;
		blueBooster = 0;
		greenBooster = 0;
	}
}
