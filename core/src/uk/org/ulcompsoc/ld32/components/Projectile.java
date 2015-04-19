package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Samy Narrainen on 19/04/2015.
 * Description: An actual active projectile which will contain the damage information
 * which will be applied if a collision is met with one
 */
public class Projectile extends Component {

    public float damage = 0.0f;

    public Projectile(float damage) {
        this.damage = damage;
    }
}
