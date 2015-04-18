package uk.org.ulcompsoc.ld32;

import uk.org.ulcompsoc.ld32.components.MapRenderable;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.systems.MapRenderSystem;
import uk.org.ulcompsoc.ld32.systems.RenderSystem;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class LD32 extends ApplicationAdapter {
	private final Engine engine;
	private final OrthographicCamera camera;

	private final CircleMap map;
	private final Entity mapEntity = new Entity();

	public LD32() {
		super();

		this.engine = new Engine();
		this.camera = new OrthographicCamera();
		this.map = new CircleMap(120.0f, 5);
	}

	@Override
	public void create() {
		this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// this.camera.position.set(Gdx.graphics.getWidth() / 2.0f,
		// Gdx.graphics.getHeight() / 2.0f, 0.0f);
		this.camera.position.set(0.0f, 0.0f, 0.0f);
		this.camera.zoom = 0.5f;

		mapEntity.add(Position.fromEuclidean(0.0f, 0.0f));
		mapEntity.add(new MapRenderable(map));
		engine.addEntity(mapEntity);

		engine.addSystem(new MapRenderSystem(10000, camera));
		engine.addSystem(new RenderSystem(20000, camera));
	}

	@Override
	public void render() {
		final float deltaTime = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		engine.update(deltaTime);
	}
}
