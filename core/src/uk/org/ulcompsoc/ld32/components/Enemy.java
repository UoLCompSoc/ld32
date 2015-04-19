package uk.org.ulcompsoc.ld32.components;

import java.util.Random;

import uk.org.ulcompsoc.ld32.components.upgrades.Upgrade;

import com.badlogic.ashley.core.Component;

/**
 * Created by Samy Narrainen on 19/04/2015.
 * Description: Necessary as it provides the BasicFiringSystem a means of detecting enemies.
 */
public class Enemy extends Component {
	
	Killable killable;
	String name;
	int speed;
	int damage;
	
	public Enemy() {
		//killable = new Killable();
	}
	
	public Upgrade.UpgradeRoute randomDrop() {
		Random rand = new Random();
		int num = rand.nextInt(3);
		
		if(num == 0) {
			return Upgrade.UpgradeRoute.RED;
		} else if(num == 1) {
			return Upgrade.UpgradeRoute.GREEN;
		} else {
			return Upgrade.UpgradeRoute.BLUE;
		}
	}
	
	
}
