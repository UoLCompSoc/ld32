package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class Killable extends Component {

    private float health = 100.0f;
    private float originalHealth = 100.0f;
    private final float DEATH_THRESHOLD = 0.0f;

    public Killable() {
        this(100.0f);
    }

    public Killable(float health) {
        this.health = health;
        this.originalHealth = health;
    }

    public boolean isDead() {
        if(health < DEATH_THRESHOLD)
            return true;
        else
            return false;
    }
    
    public void setHealth(float h) {
    	health = h;
    }

    public void removeHealth(float h) {
    	if(h < 0) {
    		try {
				throw new Exception("Negative damage");
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
        health -= h;
        if(health < 0) {
            health = 0;
        }

    }
    
    public void addHealth(float h) {
    	health = health + h;
    }
    
    public float getHealth() {
    	return health;
    }
    
    public float getOrigHealth() {
    	return originalHealth;
    }

}
