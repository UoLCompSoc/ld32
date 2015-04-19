package uk.org.ulcompsoc.ld32.components;

import java.util.Random;

import com.badlogic.ashley.core.Component;

public class Drop extends Component{
	public static enum Colour{
		RED,GREEN,BLUE;
	}
	Colour colour;
	
	public Drop(Colour colour){
		this.colour = colour;
	}
	
}
