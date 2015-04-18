package uk.org.ulcompsoc.ld32.components;

import uk.org.ulcompsoc.ld32.CircleMap;

import com.badlogic.ashley.core.Component;

public class MapRenderable extends Component {
	public final CircleMap map;

	public MapRenderable(final CircleMap map) {
		this.map = map;
	}
}
