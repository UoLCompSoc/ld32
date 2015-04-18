package uk.org.ulcompsoc.ld32.util;

import uk.org.ulcompsoc.ld32.components.Doomed;
import uk.org.ulcompsoc.ld32.components.Killable;
import uk.org.ulcompsoc.ld32.components.MapRenderable;
import uk.org.ulcompsoc.ld32.components.PaddleInputListener;
import uk.org.ulcompsoc.ld32.components.PathFollower;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;
import uk.org.ulcompsoc.ld32.components.Rotatable;
import uk.org.ulcompsoc.ld32.components.SphericalBound;
import uk.org.ulcompsoc.ld32.components.Tower;
import uk.org.ulcompsoc.ld32.components.upgrades.Upgradable;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {
	private Mappers() {
	}

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

	public static final ComponentMapper<Killable> killableMapper = ComponentMapper.getFor(Killable.class);
	public static final ComponentMapper<SphericalBound> sphericalBoundsMapper = ComponentMapper
	        .getFor(SphericalBound.class);
	public static final ComponentMapper<Rotatable> rotatableMapper = ComponentMapper.getFor(Rotatable.class);

}