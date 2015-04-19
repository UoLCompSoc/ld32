package uk.org.ulcompsoc.ld32.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import uk.org.ulcompsoc.ld32.components.*;
import uk.org.ulcompsoc.ld32.util.Mappers;

/**
 * Created by Samy Narrainen on 19/04/2015.
 * Description: Provides a basic system for an entity to fire a single
 * projectile at a single detected enemy.
 */
public class BasicFiringSystem extends IteratingSystem {

    private Engine engine = null;

    public BasicFiringSystem(int priority) {
        super(Family.all(Tower.class, Position.class).get(), priority);
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
    protected void processEntity(Entity entity, float deltaTime) {
        //The tower we're checking for
        Tower tower = Mappers.towerMapper.get(entity);
        //The position of the tower
        Position towerPos = Mappers.positionMapper.get(entity);

        //Increment known time for the tower
        tower.TimePassed(deltaTime);

        if(tower.isReadyToFire()) {
            /**
             * Look for an enemy to fire at!
             */

            //Get all enemies in the engine
            ImmutableArray<Entity> enemies = engine.getEntitiesFor(Family.all(Position.class,
                    SphericalBound.class, Enemy.class).get());

            //Iterate finding one to fire at
            for(int i = 0; i < enemies.size(); i++) {
                //The known position of the enemy
                Position enemyPos = Mappers.positionMapper.get(enemies.get(i));
                //The bound, or radius of the enemy.
                SphericalBound enemyRadius = Mappers.sphericalBoundsMapper.get(enemies.get(i));

                //Representation of the towers attack range
                Circle towerRange = new Circle(towerPos.getX(), towerPos.getY(), tower.range);

                //Representation of the enemies visible range
                Circle enemyRange = new Circle(enemyPos.getX(), enemyPos.getY(), enemyRadius.radius);

                //The tower is able to see the enemy, fire!!!
                if(enemyRange.overlaps(towerRange)) {
                    //Create a projectile to send towards the enemy.
                    //TODO IMPLEMENT A POOLEDENGINE FOR THIS?
                    Entity projectile = new Entity();

                    projectile.add(new Projectile(tower.damageComp.getDamageDealt()));
                    projectile.add(Position.fromEuclidean(towerPos.getX(), towerPos.getY()));
                    projectile.add(new Renderable(Color.RED, 2.0f));
                    projectile.add(new SphericalBound(2.0f));

                    //TODO GIVE VELOCITY OF THE RIGHT (R, THETA)
                    projectile.add(new Velocity(0.2f,0.2f));

                    engine.addEntity(projectile);

                    //Update the tower to be fired
                    tower.shotHasBeenFired();

                }

            }

        }


    }

}
