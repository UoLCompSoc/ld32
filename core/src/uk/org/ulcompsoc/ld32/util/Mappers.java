package uk.org.ulcompsoc.ld32.util;

import uk.org.ulcompsoc.ld32.components.Position;
import uk.org.ulcompsoc.ld32.components.Renderable;

import com.badlogic.ashley.core.ComponentMapper;

public class Mappers {
	private Mappers() {
	}

	public static ComponentMapper<Position> positionMapper = ComponentMapper.getFor(Position.class);
	public static ComponentMapper<Renderable> renderableMapper = ComponentMapper.getFor(Renderable.class);
}
