package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class RenderSystem extends IteratingSystem {
	private final ShapeRenderer renderer = new ShapeRenderer();
	private final OrthographicCamera camera;

	@SuppressWarnings("unchecked")
	public RenderSystem(int priority, final OrthographicCamera camera) {
		super(Family.all(Position.class, Renderable.class).get());

		this.camera = camera;
	}

	@Override
	public void update(float deltaTime) {
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Filled);

		super.update(deltaTime);

		renderer.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Position p = Mappers.positionMapper.get(entity);
		final Renderable r = Mappers.renderableMapper.get(entity);

		final float w = 5.0f;
		final float h = 5.0f;

		renderer.setColor(r.color);
		renderer.rect(p.getX() - (w / 2.0f), p.getY() - (h / 2.0f), w, h);
	}
}
