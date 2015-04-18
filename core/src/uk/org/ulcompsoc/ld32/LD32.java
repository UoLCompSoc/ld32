package uk.org.ulcompsoc.ld32;

import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.systems.RenderSystem;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class LD32 extends ApplicationAdapter {
	private final Engine engine;
	private final OrthographicCamera camera;

	private final Entity[] testEntities = new Entity[5];
	private final float r = 0.0f;
	private float phi = 0.0f;
	private final float iStep = 100.0f;

	public LD32() {
		super();

		this.engine = new Engine();
		this.camera = new OrthographicCamera();
	}

	@Override
	public void create() {
		this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// this.camera.position.set(Gdx.graphics.getWidth() / 2.0f,
		// Gdx.graphics.getHeight() / 2.0f, 0.0f);
		this.camera.position.set(0.0f, 0.0f, 0.0f);

		for (int i = 0; i < 5; ++i) {
			testEntities[i] = new Entity();
			testEntities[i].add(Position.fromPolar(r + i * iStep, phi));
			testEntities[i].add(new Renderable((Math.random() > 0.5 ? Color.RED : Color.BLUE)));
			engine.addEntity(testEntities[i]);
		}

		engine.addSystem(new RenderSystem(10000, camera));
	}

	@Override
	public void render() {
		final float deltaTime = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			phi += Math.PI / 4.0f;
			if (phi >= 2 * Math.PI) {
				phi -= 2 * Math.PI;
			}

			for (int i = 0; i < testEntities.length; ++i) {
				Mappers.positionMapper.get(testEntities[i]).setPolar(r + i * iStep, phi);
			}
		}

		camera.update();
		engine.update(deltaTime);
	}
}
