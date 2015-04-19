package uk.org.ulcompsoc.ld32.util;

public enum TextureName {
	BASIC_TOWER("towers/tower_0.png"), //
	PADDLE("paddle.png"), //
	BALL_ANIM("ball.png"), //
	ENEMY_R("enemies/enemy_0r.png"), //
	ENEMY_G("enemies/enemy_0g.png"), //
	ENEMY_B("enemies/enemy_0b.png");

	public final String assetName;

	private TextureName(final String name) {
		this.assetName = name;
	}
}
