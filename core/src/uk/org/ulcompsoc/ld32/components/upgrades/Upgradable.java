package uk.org.ulcompsoc.ld32.components.upgrades;

import java.util.ArrayList;
import java.util.List;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade;

import com.badlogic.ashley.core.Component;

public class Upgradable extends Component{
	private static final float DFLT_UPGRADE_STATUS = 0.0f;
	public List<Upgrade> upgrades;
	public float upgradeStatus;
	
	public Upgradable(){
		upgrades = new ArrayList<Upgrade>();
	}
}
