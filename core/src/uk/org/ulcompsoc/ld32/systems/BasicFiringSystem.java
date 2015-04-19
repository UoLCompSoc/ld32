package uk.org.ulcompsoc.ld32.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import uk.org.ulcompsoc.ld32.components.Tower;

/**
 * Created by Samy Narrainen on 19/04/2015.
 * Description: Provides a basic system for an entity to fire a single
 * projectile at a single detected enemy.
 */
public class BasicFiringSystem extends IteratingSystem {

    public BasicFiringSystem() {
        super(Family.all(Tower.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

}
