package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class PositionDebugSystem extends IteratingSystem {
	private final ShapeRenderer renderer;

	@SuppressWarnings("unchecked")
	public PositionDebugSystem(int priority, final ShapeRenderer renderer) {
		super(Family.all(Position.class, Renderable.class).get(), priority);
		this.renderer = renderer;
	}

	@Override
	public void update(float deltaTime) {
		renderer.begin(ShapeType.Line);

		super.update(deltaTime);

		renderer.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Position p = Mappers.positionMapper.get(entity);
		final float size = 6.0f;

		renderer.point(p.getX(), p.getY(), 0.0f);
		renderer.rect(p.getX() - (size / 2), p.getY() - size / 2, size, size);
	}
}
