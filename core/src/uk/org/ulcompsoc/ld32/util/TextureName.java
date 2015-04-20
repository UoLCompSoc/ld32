package uk.org.ulcompsoc.ld32.util;

public enum TextureName {
	PADDLE("paddle.png"), //
	BALL_ANIM("ball.png"), //

	EMPTY_TOWER("towers/tower_empty.png"), //
	BASIC_TOWER("towers/tower_0.png"), //

	ENEMY_R("enemies/enemy_0r.png"), //
	ENEMY_G("enemies/enemy_0g.png"), //
	ENEMY_B("enemies/enemy_0b.png"), //

	BALL_R("upgrade_balls/red.png"), //
	BALL_B("upgrade_balls/blue.png"), //
	BALL_G("upgrade_balls/green.png"), //
	BALL_GREY("upgrade_balls/grey.png"), //

	UPGRADE_BALL_GREY("upgrade_balls/upgrade_ball_grey.png"), //

	ZERO("numbers/0.png"), //
	ONE("numbers/1.png"), //
	TWO("numbers/2.png"), //
	THREE("numbers/3.png"), //
	FOUR("numbers/4.png"), //
	FIVE("numbers/5.png"), //
	SIX("numbers/6.png"), //
	SEVEN("numbers/7.png"), //
	EIGHT("numbers/8.png"), //
	NINE("numbers/9.png"), //

	PONG_ZERO("pong_level/pong0.png"), //
	PONG_ONE("pong_level/pong1.png"), //
	PONG_TWO("pong_level/pong2.png"), //
	PONG_THREE("pong_level/pong3.png"), //
	PONG_FOUR("pong_level/pong4.png"), //
	PONG_FIVE("pong_level/pong5.png"), //
	PONG_SIX("pong_level/pong6.png"), //
	PONG_SEVEN("pong_level/pong7.png"), //
	PONG_EIGHT("pong_level/pong8.png"), //
	PONG_NINE("pong_level/pong9.png"), //
	PONG_TEN("pong_level/pong10.png"), //

	FRAME_1("GUI/frame.png"), //

	AMMO("ammo.png");//

	public final String assetName;

	private TextureName(final String name) {
		this.assetName = name;
	}
	public TextureName getNumber(int number){
		switch(number){
		case 0: return TextureName.ZERO;
		case 1: return TextureName.ONE;
		case 2: return TextureName.TWO;
		case 3: return TextureName.THREE;
		case 4: return TextureName.FOUR;
		case 5: return TextureName.FIVE;
		case 6: return TextureName.SIX;
		case 7: return TextureName.SEVEN;
		case 8: return TextureName.EIGHT;
		case 9: return TextureName.NINE;
		default: return null;
		}
	}
}
