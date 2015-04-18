package uk.org.ulcompsoc.ld32.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import uk.org.ulcompsoc.ld32.components.Atom;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Velocity;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class AtomMovementSystem extends IteratingSystem {

    /**
     * Defines the external limits of the Atom
     */
    private Circle                      outerBorder	= null;
    private ComponentMapper<Position>	posMapper	= null;
    private ComponentMapper<Velocity>	velMapper	= null;

    @SuppressWarnings("unchecked")
    public AtomMovementSystem(Circle outerBorder, int priority) {
        this(Family.all(Position.class, Velocity.class, Atom.class, SphericalBound.class).get(), outerBorder, priority);
    }

    protected AtomMovementSystem(Family family, Circle outerBorder, int priority) {
        super(family, priority);

        posMapper = ComponentMapper.getFor(Position.class);
        velMapper = ComponentMapper.getFor(Velocity.class);
        this.outerBorder = outerBorder;

    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {

        Position p = posMapper.get(entity);
        Vector2 v = velMapper.get(entity).velocity;

        p.setX(p.getX() + v.x);
        p.setY(p.getY() + v.y);

        if(p.getY() > outerBorder.radius) {
            v.y = -v.y;

            System.out.println("test");
        } else if(p.getX() > outerBorder.radius /2) {
            v.x = -v.x;
            System.out.println("test2");
        } else if(p.getY() < -outerBorder.radius/2) {
            v.y = -v.y;
            System.out.println("reached2");
        } else if(p.getX() < -outerBorder.radius /2) {
            v.x = -v.x;
        }


    }
}
