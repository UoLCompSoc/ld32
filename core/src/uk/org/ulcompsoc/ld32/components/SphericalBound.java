package uk.org.ulcompsoc.ld32.components;


import com.badlogic.ashley.core.Component;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class SphericalBound extends Component {

    public float radius = 0.0f;

    public SphericalBound() {
        this(10.0f);
    }

    public SphericalBound(float radius) {
        this.radius = radius;
    }
}
