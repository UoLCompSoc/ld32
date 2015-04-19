package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Wallet extends Component {
	public int red;
	public int blue;
	public int green;

	public Wallet() {
		this(0, 0, 0);
	}

	public Wallet(int red, int blue, int green) {
		this.red = red;
		this.blue = blue;
		this.green = green;
	}
}
