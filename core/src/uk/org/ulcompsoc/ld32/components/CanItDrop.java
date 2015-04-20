package uk.org.ulcompsoc.ld32.components;

import com.badlogic.ashley.core.Component;

public class CanItDrop extends Component {
	private static final float DFLT_CURRENCY_DROP_CHANCE = 0.15f;
	private static final float DFLT_ATOM_DROP_CHANCE = 0.025f;

	public float redDropChance = DFLT_CURRENCY_DROP_CHANCE;
	public float blueDropChance = DFLT_CURRENCY_DROP_CHANCE;
	public float greenDropChance = DFLT_CURRENCY_DROP_CHANCE;

	public float atomDropChance = DFLT_ATOM_DROP_CHANCE;

	// if doesn't drop, increase slightly - so player doesn't get unlucky streak
	public static float RED_BOOSTER = DFLT_CURRENCY_DROP_CHANCE;
	public static float BLUE_BOOSTER = DFLT_CURRENCY_DROP_CHANCE;
	public static float GREEN_BOOSTER = DFLT_CURRENCY_DROP_CHANCE;

	public static float ATOM_BOOSTER = DFLT_ATOM_DROP_CHANCE;

	public static final float DFLT_BOOSTER_INCREASE = 0.03f;

	public CanItDrop() {
	}
}
