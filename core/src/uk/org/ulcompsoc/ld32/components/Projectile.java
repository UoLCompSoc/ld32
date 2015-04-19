package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Samy Narrainen on 19/04/2015.
 * Description: An actual active projectile which will contain the damage information
 * which will be applied if a collision is met with one
 */
public class Projectile extends Component {

    //The damage this projectile was spawned with
    public float damage = 0.0f;
    //After how much elapsed time will this projectile disappear?
    public float maximumUptime = 2.0f;
    //How much time has currently passed since existence?
    public float elapsedTime = 0.0f;

    public Projectile(float damage) {
        this(damage, 2.0f);
    }

    public Projectile(float damage, float maximumUptime) {
        this.damage = damage;
        this.maximumUptime = maximumUptime;
    }

    /**
     * Allows us to update how much time has passed.
     * @param delta, time delta
     */
    public void updateDelta(float delta) {
        this.elapsedTime += delta;
    }


    public boolean hasExceededUptime() {
        if(elapsedTime >= maximumUptime)
            return true;
        else
            return false;
    }


}
