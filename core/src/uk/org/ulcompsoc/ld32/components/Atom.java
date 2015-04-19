package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Samy Narrainen on 18/04/2015.
 */
public class Atom extends Component {

    //Means that there is a request to be fired!
    public boolean primed = false;
    //Means the atom is nested in the paddle
    public boolean atPaddle = false;

}
