package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class Wallet extends Component {
	public int redCurrency;
	public int blueCurrency;
	public int greenCurrency;
	
	
	public Wallet(){
		redCurrency = 0;
		blueCurrency = 0;
		greenCurrency = 0;
	}
}
