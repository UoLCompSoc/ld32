package uk.org.ulcompsoc.ld32;

import java.util.HashMap;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.components.Atom;
import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.MapRenderable;
import uk.org.ulcompsoc.ld32.components.Paddle;
import uk.org.ulcompsoc.ld32.components.PaddleInputListener;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Scalable;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.Velocity;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgradable;
import uk.org.ulcompsoc.ld32.systems.AtomMovementSystem;
import uk.org.ulcompsoc.ld32.systems.BasicFiringSystem;
import uk.org.ulcompsoc.ld32.systems.DoomedSystem;
import uk.org.ulcompsoc.ld32.systems.EnemySpawningSystem;
import uk.org.ulcompsoc.ld32.systems.MapRenderSystem;
import uk.org.ulcompsoc.ld32.systems.PaddleInputSystem;
import uk.org.ulcompsoc.ld32.systems.PathFollowingSystem;
import uk.org.ulcompsoc.ld32.systems.PositionDebugSystem;
import uk.org.ulcompsoc.ld32.systems.ProjectileMovementSystem;
import uk.org.ulcompsoc.ld32.systems.RenderSystem;
import uk.org.ulcompsoc.ld32.systems.SphericalCollisionSystem;
import uk.org.ulcompsoc.ld32.util.AudioManager;
import uk.org.ulcompsoc.ld32.util.LDUtil;
import uk.org.ulcompsoc.ld32.util.TextureManager;
import uk.org.ulcompsoc.ld32.util.TextureName;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class LD32 extends ApplicationAdapter {
	private final Engine engine;
	private final OrthographicCamera camera;

	private final TextureManager textureManager;

	private final Entity paddle = new Entity();

	private final Entity enemy = new Entity();

	private final CircleMap map;
	private final Entity mapEntity = new Entity();

	private final Entity tower = new Entity();

	private ShapeRenderer shapeRenderer = null;
	private Batch spriteBatch = null;

	private Animation ballAnimation = null;

	public LD32() {
		super();

		this.engine = new Engine();
		this.camera = new OrthographicCamera();
		this.map = new CircleMap(120.0f, 5);
		this.textureManager = new TextureManager();
	}

	@Override
	public void create() {
		this.camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// this.camera.position.set(Gdx.graphics.getWidth() / 2.0f,
		// Gdx.graphics.getHeight() / 2.0f, 0.0f);
		this.camera.position.set(0.0f, 0.0f, 0.0f);
		this.camera.zoom = 0.5f;

		this.shapeRenderer = new ShapeRenderer();
		this.spriteBatch = new SpriteBatch();
		this.textureManager.load();

		final Renderable paddleRenderable = new Renderable(new TextureRegion(
		        textureManager.nameMap.get(TextureName.PADDLE)));
		paddle.add(paddleRenderable);

		final float paddleScale = 0.2f;
		final Position paddlePosition = Position
		        .fromPolar(map.radius + paddleScale * paddleRenderable.getWidth(), 0.0f);
		paddle.add(paddlePosition);
		final int[] leftKeys = { Keys.LEFT, Keys.A };
		final int[] rightKeys = { Keys.RIGHT, Keys.D };
		paddle.add(new PaddleInputListener(leftKeys, rightKeys));
		paddle.add(new SphericalBound(30f));
		paddle.add(new Scalable(paddleScale));

		paddle.add(new Paddle());

		engine.addEntity(paddle);

		final RingSegment firstSegment = map.getFirstSegment();
		enemy.add(Position.fromPolar(firstSegment.middleR, firstSegment.middlePhi));
		enemy.add(new Renderable(Color.BLUE, 16.0f));
		enemy.add(new PathFollower(firstSegment).continueToNull().killWhenDone());
		// enemy.add(new Enemy());
		// engine.addEntity(enemy);

		tower.add(Position.fromPolar(map.radius, LDUtil.PI));
		tower.add(new Renderable(new TextureRegion(textureManager.nameMap.get(TextureName.BASIC_TOWER))));
		tower.add(new Tower(new Upgradable()));
		tower.add(new Killable(100));
		tower.add(new Upgradable());
		tower.add(new Scalable(0.25f));
		engine.addEntity(tower);

		engine.addEntity(makeAtom());

		mapEntity.add(Position.fromEuclidean(0.0f, 0.0f));
		mapEntity.add(new MapRenderable(map));
		engine.addEntity(mapEntity);

		engine.addSystem(new EnemySpawningSystem(500, 5.0f, map, textureManager));
		engine.addSystem(new PaddleInputSystem(1000));
		engine.addSystem(new PathFollowingSystem(5000));
		engine.addSystem(new MapRenderSystem(10000, shapeRenderer, camera));
		engine.addSystem(new RenderSystem(20000, spriteBatch, shapeRenderer, camera));

		engine.addSystem(new PositionDebugSystem(50000, shapeRenderer));

		engine.addSystem(new DoomedSystem(100000));

		// engine.addSystem(new AudioIntervalSystem(1f, audioTest()));

		// engine.addSystem(new AtomMovementSystem(new
		// Circle(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,
		// map.radius), 2));
		engine.addSystem(new SphericalCollisionSystem(2, new Circle(Gdx.graphics.getWidth() / 2, Gdx.graphics
		        .getHeight() / 2, map.radius)));

		// engine.addSystem(new AudioIntervalSystem(1f, audioTest()));

		engine.addSystem(new AtomMovementSystem(new Circle(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2,
		        map.radius), 2));

		/**
		 * FIRING SYSTEM FOR TOWERS
		 */
		engine.addSystem(new BasicFiringSystem(2));
		engine.addSystem(new ProjectileMovementSystem(2));
	}

	@Override
	public void render() {
		final float deltaTime = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		engine.update(deltaTime);
	}

	@Override
	public void dispose() {
		super.dispose();

		engine.removeAllEntities();

		textureManager.dispose();

		if (shapeRenderer != null) {
			shapeRenderer.dispose();
			shapeRenderer = null;
		}

		if (spriteBatch != null) {
			spriteBatch.dispose();
			spriteBatch = null;
		}
	}

	public AudioManager audioTest() {
		HashMap<String, String> files = new HashMap<String, String>();
		files.put("drop", "data/drop.mp3");
		files.put("woosh", "data/woosh.mp3");

		AudioManager x = new AudioManager(files);

		x.queue("drop");
		x.queue("drop");
		x.queue("drop");
		// x.loop("woosh");
		x.queue("woosh");
		x.queue("drop");
		x.queue("drop");
		x.clear("woosh");

		return x;
	}

	public Entity makeAtom() {
		if (ballAnimation == null) {
			final TextureRegion[] regions = TextureRegion.split(textureManager.nameMap.get(TextureName.BALL_ANIM), 64,
			        64)[0];

			ballAnimation = new Animation(0.15f, regions);
			ballAnimation.setPlayMode(PlayMode.LOOP);
		}

		Entity e = new Entity();

		e.add(Position.fromEuclidean(2.0f, 2.0f));
		e.add(new Renderable(ballAnimation));
		e.add(new Scalable(0.5f));
		e.add(new SphericalBound(10.0f));
		e.add(new Velocity(0.5f, 0.5f));
		e.add(new Atom());

		return e;
	}

}
