package uk.org.ulcompsoc.ld32.components;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class EntityLink extends Component {
	public List<Entity> children = new ArrayList<Entity>();

	public EntityLink(Entity child, Entity... others) {
		this.children.add(child);

		for (Entity e : others) {
			this.children.add(e);
		}
	}
}
