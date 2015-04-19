package uk.org.ulcompsoc.ld32.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Projectile;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Velocity;
import uk.org.ulcompsoc.ld32.util.Mappers;

/**
 * Created by Samy Narrainen on 19/04/2015.
 * Description: Updates the uptime for projectiles, removing them once they have
 * exceeded their defined maximum lifetime.
 */
public class ProjectileLifeTimeSystem extends IteratingSystem {

    private Engine engine = null;


    @SuppressWarnings("unchecked")
    public ProjectileLifeTimeSystem(int priority) {
        super(Family.all(Projectile.class).get(), priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        this.engine = null;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        //Grab the projectile component from the entity
        Projectile projectile = Mappers.projectileMapper.get(entity);

        //If the time's up for the projectile, remove it
        if(projectile.hasExceededUptime()) {
            engine.removeEntity(entity); //TODO SIGNAL FOR DEATH ANIMATION
        } else {
            //Otherwise, increase the actual uptime for the projectile
            projectile.updateDelta(deltaTime);
        }

    }


}
