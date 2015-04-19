package uk.org.ulcompsoc.ld32.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.badlogic.gdx.math.Vector2;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Velocity;
import uk.org.ulcompsoc.ld32.components.Projectile;
import uk.org.ulcompsoc.ld32.util.Mappers;

/**
 * Created by Samy on 19/04/2015.
 * Description: Provides the movement for Projectiles, potential overlap with AtomMovementSystem...
 */
public class ProjectileMovementSystem extends IteratingSystem {

    //TODO Probably want to add a boundary for the projectiles where they disappear after reaching

    @SuppressWarnings("unchecked")
    public ProjectileMovementSystem(int priority) {
        this(Family.all(Position.class, Velocity.class, Projectile.class, SphericalBound.class).get(), priority);
    }

    protected ProjectileMovementSystem(Family family, int priority) {
        super(family, priority);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        Position p = Mappers.positionMapper.get(entity);
        Vector2 v = Mappers.velMapper.get(entity).velocity;

        p.translatePolarDistance(v.x);

    }


}
