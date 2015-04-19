package uk.org.ulcompsoc.ld32.util;

public enum TextureName {
	BASIC_TOWER("towers/tower_0.png"), //
	PADDLE("paddle.png"), //
	BALL_ANIM("ball.png"), //

	ENEMY_R("enemies/enemy_0r.png"), //
	ENEMY_G("enemies/enemy_0g.png"), //
	ENEMY_B("enemies/enemy_0b.png"), //

	BALL_R("upgrade_balls/red.png"), //
	BALL_B("upgrade_balls/blue.png"), //
	BALL_G("upgrade_balls/green.png"),
	
	ZERO("numbers/0.png"),
	ONE("numbers/1.png"),
	TWO("numbers/2.png"),
	THREE("numbers/3.png"),
	FOUR("numbers/4.png"),
	FIVE("numbers/5.png"),
	SIX("numbers/6.png"),
	SEVEN("numbers/7.png"),
	EIGHT("numbers/8.png"),
	NINE("numbers/9.png"),
	
	FRAME_1("GUI/frame.png");

	public final String assetName;

	private TextureName(final String name) {
		this.assetName = name;
	}
}
