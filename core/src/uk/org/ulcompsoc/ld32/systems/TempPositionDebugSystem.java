package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.*;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Samy Narrainen on 20/04/2015.
 */
public class TempPositionDebugSystem extends IteratingSystem {
    private ShapeRenderer renderer;

    @SuppressWarnings("unchecked")
    public TempPositionDebugSystem(int priority, final ShapeRenderer renderer) {
        super(Family.all(Atom.class, Position.class,
                Renderable.class).get(), priority);
        this.renderer = renderer;
        System.out.println("BoundingDebugSystem created: are you sure you meant to do this?");
    }

    @Override
    public void update(float deltaTime) {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.BLACK);

        super.update(deltaTime);

        renderer.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final Renderable bound = Mappers.renderableMapper.get(entity);
        final Position p = Mappers.positionMapper.get(entity);

        renderer.point(p.getX(), p.getY(), 0.0f);
        renderer.circle(p.getX(), p.getY(), bound.getWidth()/2);
    }
}
