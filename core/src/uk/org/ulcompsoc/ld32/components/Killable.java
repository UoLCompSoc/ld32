package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class Killable extends Component {

    public float health = 100.0f;
    public float originalHealth = 100.0f;
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


}
