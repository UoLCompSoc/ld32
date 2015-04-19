package uk.org.ulcompsoc.ld32.util;


import uk.org.ulcompsoc.ld32.components.Atom;
import uk.org.ulcompsoc.ld32.components.CanItDrop;
import uk.org.ulcompsoc.ld32.components.Damage;
import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.MapRenderable;
import uk.org.ulcompsoc.ld32.components.Paddle;
import uk.org.ulcompsoc.ld32.components.PaddleInputListener;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Rotatable;
import uk.org.ulcompsoc.ld32.components.Scalable;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.Velocity;
import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgradable;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {
	public static final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
	public static final ComponentMapper<Renderable> renderableMapper = ComponentMapper.getFor(Renderable.class);
	public static final ComponentMapper<MapRenderable> mapRenderableMapper = ComponentMapper
	        .getFor(MapRenderable.class);
	public static final ComponentMapper<Tower> towerMapper = ComponentMapper.getFor(Tower.class);
	public static final ComponentMapper<Upgradable> upgradeableMapper = ComponentMapper.getFor(Upgradable.class);
	public static final ComponentMapper<PathFollower> pathFollowerMapper = ComponentMapper.getFor(PathFollower.class);
	public static final ComponentMapper<Doomed> doomedMapper = ComponentMapper.getFor(Doomed.class);
	public static final ComponentMapper<PaddleInputListener> paddleInputListener = ComponentMapper
	        .getFor(PaddleInputListener.class);
	public static final ComponentMapper<SphericalBound> sphericalBoundsMapper = ComponentMapper
	        .getFor(SphericalBound.class);
	public static final ComponentMapper<Rotatable> rotatableMapper = ComponentMapper.getFor(Rotatable.class);
	public static final ComponentMapper<Scalable> scalableMapper = ComponentMapper.getFor(Scalable.class);
	public static final ComponentMapper<Atom> atomMapper = ComponentMapper.getFor(Atom.class);
	public static final ComponentMapper<Paddle> paddleMapper = ComponentMapper.getFor(Paddle.class);
	public static final ComponentMapper<Velocity> velMapper = ComponentMapper.getFor(Velocity.class);
	//Added by Damian - just in case if it needs to be found and quickly fixed :P
	public static final ComponentMapper<CanItDrop> dropMapper = ComponentMapper.getFor(CanItDrop.class);

	//Added by Musty
	public static final ComponentMapper<Damage> damageMapper = ComponentMapper.getFor(Damage.class);
	public static final ComponentMapper<Killable> killableMapper= ComponentMapper.getFor(Killable.class);

	public static final ComponentMapper<Wallet> walletMapper = ComponentMapper.getFor(Wallet.class);

	
	private Mappers() {
	}

}
