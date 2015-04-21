package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.CircleMap.Ring;
import uk.org.ulcompsoc.ld32.components.MapRenderable;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MapRenderSystem extends IteratingSystem {
	private final ShapeRenderer renderer;
	private final OrthographicCamera camera;
	private final Batch batch;

	@SuppressWarnings("unchecked")
	public MapRenderSystem(int priority, final ShapeRenderer renderer, final Batch batch,
	        final OrthographicCamera camera) {
		super(Family.all(Position.class, MapRenderable.class).get(), priority);

		this.camera = camera;
		this.renderer = renderer;
		this.batch = batch;
	}

	@Override
	public void update(float deltaTime) {
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Line);
		// batch.setProjectionMatrix(camera.combined);
		// batch.begin();

		super.update(deltaTime);

		// batch.end();
		renderer.end();
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Position p = Mappers.positionMapper.get(entity);
		final MapRenderable mr = Mappers.mapRenderableMapper.get(entity);

		renderer.setColor(Color.BLACK);

		for (final Ring ring : mr.map.rings) {
			renderer.circle(p.getX(), p.getY(), ring.radius, 1000);

			// debug segment identification
			// final float segR = ring.radius - (ring.height / 2);
			// for (final RingSegment segment : ring.segments) {
			// renderer.circle(segR * (float) Math.cos(segment.startPhi), segR *
			// (float) Math.sin(segment.startPhi),
			// 5.0f);
			// }
		}
	}
}
