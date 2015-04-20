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

	/**
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @return true if we have enough in the wallet to match r, g, b
	 */
	public boolean checkBalance(int r, int g, int b) {
		return (red >= r && green >= g && blue >= b);
	}

	public void add(int r, int g, int b) {
		this.red += r;
		this.green += g;
		this.blue += b;
	}

	public void sub(int r, int g, int b) {
		this.red -= r;
		this.green -= g;
		this.blue -= b;
	}
}
