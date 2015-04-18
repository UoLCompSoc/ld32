package uk.org.ulcompsoc.ld32.components;


import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class Velocity extends Component {
    public Vector2	velocity	= null;

    public Velocity() {
        this(0.0f, 0.0f);
    }

    public Velocity(float x, float y) {
        velocity = new Vector2(x, y);
    }

}
