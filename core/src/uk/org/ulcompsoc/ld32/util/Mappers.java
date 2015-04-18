package uk.org.ulcompsoc.ld32.util;

import uk.org.ulcompsoc.ld32.components.MapRenderable;
import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {
	private Mappers() {
	}

	public static final ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
	public static final ComponentMapper<Renderable> renderableMapper = ComponentMapper.getFor(Renderable.class);
	public static final ComponentMapper<MapRenderable> mapRenderableMapper = ComponentMapper
	        .getFor(MapRenderable.class);
}
