package uk.org.ulcompsoc.ld32.components;

import uk.org.ulcompsoc.ld32.util.DeathListener;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class DoomNotifier extends Component {
	public final DeathListener listener;
	public boolean hasNotified = false;

	public DoomNotifier(final DeathListener listener) {
		this.listener = listener;
	}

	public void notifyOfDeath(final Entity entity) {
		if (!hasNotified) {
			hasNotified = true;

			listener.notifyOfDeath(entity);
		}
	}
}
