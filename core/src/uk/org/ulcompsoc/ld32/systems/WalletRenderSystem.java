package uk.org.ulcompsoc.ld32.systems;

import uk.org.ulcompsoc.ld32.components.Wallet;
import uk.org.ulcompsoc.ld32.util.Mappers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WalletRenderSystem extends IteratingSystem{
	private final Batch batch;
	private float xAxis;
	private float yAxis;
	private TextureRegion red;
	private TextureRegion blue;
	private TextureRegion green;
	
	@SuppressWarnings("unchecked")
	public WalletRenderSystem(Batch batch, float x,float y,int priority){
		super(Family.all(Wallet.class).get(), priority);
		this.batch = batch;
		this.xAxis = x;
		this.yAxis = y;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Wallet wallet = Mappers.walletMapper.get(entity);
	}
	
}
