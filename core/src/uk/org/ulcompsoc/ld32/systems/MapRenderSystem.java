package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.CircleMap.Ring;
import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.MapRenderable;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MapRenderSystem extends IteratingSystem {
	private final ShapeRenderer renderer = new ShapeRenderer();
	private final OrthographicCamera camera;

	@SuppressWarnings("unchecked")
	public MapRenderSystem(int priority, final OrthographicCamera camera) {
		super(Family.all(Position.class, MapRenderable.class).get(), priority);

		this.camera = camera;
	}

	@Override
	public void update(float deltaTime) {
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Line);

		super.update(deltaTime);

		renderer.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Position p = Mappers.positionMapper.get(entity);
		final MapRenderable mr = Mappers.mapRenderableMapper.get(entity);

		renderer.setColor(Color.BLACK);

		for (final Ring ring : mr.map.rings) {
			renderer.circle(p.position.x, p.position.y, ring.radius, 1000);

			final float segR = ring.radius - (ring.height / 2);
			for (final RingSegment segment : ring.segments) {
				renderer.circle(segR * (float) Math.cos(segment.startPhi), segR * (float) Math.sin(segment.startPhi),
				        5.0f);
			}
		}
	}
}
