package uk.org.ulcompsoc.ld32;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.org.ulcompsoc.ld32.CircleMap.RingSegment;
import uk.org.ulcompsoc.ld32.audio.AudioName;
import uk.org.ulcompsoc.ld32.audio.IAudioManagement;
import uk.org.ulcompsoc.ld32.audio.SilentAudioManager;
import uk.org.ulcompsoc.ld32.components.Atom;
import uk.org.ulcompsoc.ld32.components.Damage;
import uk.org.ulcompsoc.ld32.components.DeathAnimation;
import uk.org.ulcompsoc.ld32.components.MapRenderable;
import uk.org.ulcompsoc.ld32.components.MouseListener;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseButtons;
import uk.org.ulcompsoc.ld32.components.MouseListener.MouseListenerHandler;
import uk.org.ulcompsoc.ld32.components.Paddle;
import uk.org.ulcompsoc.ld32.components.PaddleInputListener;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Rotatable;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.Velocity;
import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgradable;
import uk.org.ulcompsoc.ld32.mouse.EmptyTowerMouseListenerHandler;
import uk.org.ulcompsoc.ld32.mouse.RegularTowerMouseListenerHandler;
import uk.org.ulcompsoc.ld32.systems.AtomCollisionSystem;
import uk.org.ulcompsoc.ld32.systems.AtomMovementSystem;
import uk.org.ulcompsoc.ld32.systems.BasicFiringSystem;
import uk.org.ulcompsoc.ld32.systems.DoomedSystem;
import uk.org.ulcompsoc.ld32.systems.EnemySpawningSystem;
import uk.org.ulcompsoc.ld32.systems.GUIRenderSystem;
import uk.org.ulcompsoc.ld32.systems.MapRenderSystem;
import uk.org.ulcompsoc.ld32.systems.MouseListenerSystem;
import uk.org.ulcompsoc.ld32.systems.PaddleInputSystem;
import uk.org.ulcompsoc.ld32.systems.PathFollowingSystem;
import uk.org.ulcompsoc.ld32.systems.ProjectileLifeTimeSystem;
import uk.org.ulcompsoc.ld32.systems.ProjectileMovementSystem;
import uk.org.ulcompsoc.ld32.systems.RenderSystem;
import uk.org.ulcompsoc.ld32.systems.SphericalCollisionSystem;
import uk.org.ulcompsoc.ld32.systems.TowerSystem;
import uk.org.ulcompsoc.ld32.systems.WalletRenderSystem;
import uk.org.ulcompsoc.ld32.util.LDUtil;
import uk.org.ulcompsoc.ld32.util.Mappers;
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

	public static final TextureManager textureManager = new TextureManager();

	// public static final IAudioManagement audioManager = new AudioManager();
	public static final IAudioManagement audioManager = new SilentAudioManager(false);

	private final Entity paddle = new Entity();
	private TextureRegion paddleSprite = null;

	private final Entity enemy = new Entity();

	private final CircleMap map;
	private final Entity mapEntity = new Entity();

	private final Entity tower = new Entity();

	private ShapeRenderer shapeRenderer = null;
	private Batch spriteBatch = null;

	private Animation atomAnimation = null;

	private final Random random = new Random();

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

		this.shapeRenderer = new ShapeRenderer();
		this.spriteBatch = new SpriteBatch();
		textureManager.load();
		audioManager.load();
		audioManager.play(AudioName.ABSTRACTION);

		paddleSprite = new TextureRegion(textureManager.nameMap.get(TextureName.PADDLE));
		final float paddleScale = 0.2f;
		final Renderable paddleRenderable = new Renderable(paddleSprite).setScale(paddleScale);
		paddle.add(paddleRenderable);

		final Position paddlePosition = Position.fromPolar(map.radius + paddleRenderable.getWidth(), 0.0f);
		paddle.add(paddlePosition);
		final int[] leftKeys = { Keys.LEFT, Keys.A };
		final int[] rightKeys = { Keys.RIGHT, Keys.D };
		final int[] fireKeys = { Keys.UP, Keys.SPACE };
		paddle.add(new PaddleInputListener(leftKeys, rightKeys, fireKeys));
		paddle.add(new SphericalBound(30f));
		paddle.add(new Paddle());
		paddle.add(new Wallet(0, 0, 0));
		paddle.add(new Rotatable().matchPhi());
		paddle.add(new MouseListener(new MouseListenerHandler() {
			@Override
			public void handleMouseLeave(Entity tower, float mouseX, float mouseY) {
			}

			@Override
			public void handleMouseIn(Entity tower, float mouseX, float mouseY) {
			}

			@Override
			public void handleMouseEnter(Entity tower, float mouseX, float mouseY) {
			}

			@Override
			public void handleButtonDown(Entity tower, MouseButtons button, float mouseX, float mouseY) {
				if (button.equals(MouseButtons.MIDDLE)) {
					Mappers.walletMapper.get(paddle).add(1, 1, 1);
				}
			}
		}, new Circle(paddlePosition.getX(), paddlePosition.getY(), paddleRenderable.getWidth() * 0.5f)));

		engine.addEntity(paddle);

		final RingSegment firstSegment = map.getFirstSegment();
		enemy.add(Position.fromPolar(firstSegment.middleR, firstSegment.middlePhi));
		enemy.add(new Renderable(Color.BLUE, 16.0f));
		enemy.add(new PathFollower(firstSegment).continueToNull().killWhenDone());
		// enemy.add(new Enemy());
		// engine.addEntity(enemy);

		final Position towerPos = Position.fromPolar(map.radius, LDUtil.PI);
		final float towerScale = 0.25f;
		final Renderable towerRen = new Renderable(new TextureRegion(
		        textureManager.nameMap.get(TextureName.BASIC_TOWER))).setScale(towerScale);
		tower.add(towerPos);
		tower.add(towerRen);
		tower.add(new Tower(new Upgradable()));
		tower.add(new Damage(Tower.DFLT_DMG));
		tower.add(new Upgradable());
		tower.add(new MouseListener(new RegularTowerMouseListenerHandler(engine), new Circle(towerPos.getX(), towerPos
		        .getY(), towerRen.getHeight())));
		engine.addEntity(tower);

		engine.addEntity(makeAtom(false));
		engine.addEntity(makeAtom(false));
		// engine.addEntity(makeAtom());

		float phi = 3 * (LDUtil.PI / 2.0f);
		for (int i = 0; i < 3; ++i) {
			engine.addEntity(makeEmptyTower(phi + i * (LDUtil.PI / 2.0f)));
		}

		mapEntity.add(Position.fromEuclidean(0.0f, 0.0f));
		mapEntity.add(new MapRenderable(map));
		engine.addEntity(mapEntity);

		engine.addSystem(new GUIRenderSystem(-100, spriteBatch, camera, paddle));
		engine.addSystem(new EnemySpawningSystem(500, 0.5f, map));
		engine.addSystem(new PaddleInputSystem(1000));
		engine.addSystem(new MouseListenerSystem(2000, camera));
		engine.addSystem(new PathFollowingSystem(5000));

		engine.addSystem(new AtomMovementSystem(5500, new Circle(0, 0, map.radius)));
		engine.addSystem(new ProjectileMovementSystem(6000));
		engine.addSystem(new BasicFiringSystem(6500));
		engine.addSystem(new SphericalCollisionSystem(7500, new Circle(Gdx.graphics.getWidth() / 2, Gdx.graphics
		        .getHeight() / 2, map.radius)));
		engine.addSystem(new ProjectileLifeTimeSystem(8000));
		engine.addSystem(new TowerSystem(9000, paddle.getComponent(Wallet.class)));
		engine.addSystem(new MapRenderSystem(10000, shapeRenderer, camera));
		engine.addSystem(new RenderSystem(20000, spriteBatch, shapeRenderer, camera));

		engine.addSystem(new WalletRenderSystem(25000, spriteBatch, camera, Gdx.graphics.getWidth() * 0.9f,
		        Gdx.graphics.getHeight() * 0.95f));

		engine.addSystem(new DoomedSystem(100000, paddle, this));
		engine.addSystem(new AtomCollisionSystem(7500));

		// engine.addSystem(new PositionDebugSystem(50000, shapeRenderer));

		// engine.addSystem(new BoundingDebugSystem(5000, shapeRenderer));
		// engine.addSystem(new AtomMovementSystem(new
		// Circle(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,
		// map.radius), 2));

		// textureManager.makeWord(engine, "h3llo WORLD", 0, 5 + (int)
		// map.radius).add(new Fade(2.0f, true));
	}

	private List<Integer> frameCounts = new ArrayList<Integer>();

	@Override
	public void render() {
		final float deltaTime = Gdx.graphics.getDeltaTime();
		frameCounts.add(Gdx.graphics.getFramesPerSecond());

		if (frameCounts.size() > 100) {
			int total = 0;

			for (int i : frameCounts) {
				total += i;
			}

			final float fps = (float) total / (float) frameCounts.size();

			frameCounts.clear();
			Gdx.app.log("FPS", "Average FPS: " + fps);
		}

		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		engine.update(deltaTime);
	}

	@Override
	public void dispose() {
		super.dispose();

		engine.removeAllEntities();

		audioManager.dispose();
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

	public Entity makeAtom() {
		return makeAtom(true);
	}

	public Entity makeAtom(boolean randomDir) {
		if (atomAnimation == null) {
			atomAnimation = new Animation(0.15f, textureManager.animationRegionMap.get(TextureName.BALL_ANIM));
			atomAnimation.setPlayMode(PlayMode.LOOP);
		}

		final Entity e = new Entity();
		final Renderable r = new Renderable(atomAnimation).setScale(0.5f);

		e.add(Position.fromEuclidean(0.0f, 0.0f));
		e.add(r);
		e.add(new SphericalBound(r.getWidth() / 2.0f));
		e.add(new Atom());
		e.add(new DeathAnimation(atomAnimation));
		e.add(new Rotatable().animateRotation(1.0f));

		if (randomDir) {
			final float velocityAngle = random.nextFloat() * 2.0f * LDUtil.PI;
			final float velocityMag = 0.4f;
			e.add(new Velocity((float) Math.cos(velocityAngle) * velocityMag, (float) Math.sin(velocityAngle)
			        * velocityMag));
		} else {
			e.add(new Velocity(0.5f, 0.0f));
		}

		return e;
	}

	public Entity makeEmptyTower(float phi) {
		final Entity e = new Entity();
		final Position towerPos = Position.fromPolar(map.radius, phi);
		final Renderable towerRen = new Renderable(new TextureRegion(
		        textureManager.nameMap.get(TextureName.EMPTY_TOWER))).setScale(0.25f);
		e.add(towerPos);
		e.add(towerRen);
		e.add(new SphericalBound(towerRen.getWidth() / 2));
		e.add(new MouseListener(new EmptyTowerMouseListenerHandler(engine, paddle), new Circle(towerPos.getX(),
		        towerPos.getY(), towerRen.getHeight() * 0.95f)));
		return e;
	}
}
