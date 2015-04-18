package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Tower extends Component {

	private static final float DFLT_RANGE = 10.0f; // starting range
	
	
	public float range;
	
	public Tower(){
		range = Tower.DFLT_RANGE;
	}
	
	
}
