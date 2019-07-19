package FlatWorld;

import java.util.Random;

public class BattleObjectClass {
	private static Random rand = new Random();
	public static final BattleObjectClass standardAxe = new BattleObjectClass(3.0f, 1.5f, 50.0f, 0.0f); 
	public static final BattleObjectClass standardAxeMin = new BattleObjectClass(-2.3f, -1.3f, -15.0f, -0.0f); 
	public static final BattleObjectClass standardAxeMax = new BattleObjectClass(3.7f, 1.8f, 5.0f, 0.01f);
	
	public static final BattleObjectClass rareRedAxeMin = new BattleObjectClass(1.3f, 0.3f, 5.0f, 0.01f); 
	public static final BattleObjectClass rareRedAxeMax = new BattleObjectClass(3.7f, 2.5f, 20.0f, 0.03f);
	
	public static final BattleObjectClass commonBlueAxeMin = new BattleObjectClass(-2.3f, -1.3f, -15.0f, -0.0f); 
	public static final BattleObjectClass commonBlueAxeMax = new BattleObjectClass(2.7f, 0.8f, 4.0f, 0.02f);
	
	public static final BattleObjectClass commonGreenAxeMin = new BattleObjectClass(-1.3f, -0.3f, -3.0f, -0.0f); 
	public static final BattleObjectClass commonGreenAxeMax = new BattleObjectClass(1.7f, 2.5f, 15.0f, 0.01f);
	
	private final float damage, radius, angle, resist;
	
	BattleObjectClass(float damage, float radius, float angle, float resist){
		this.damage = damage;
		this.radius = radius;
		this.angle  = angle;
		this.resist = resist;
	}
	
	public float getDamage(){
		return damage;
	}

	public float getResist() {
		return resist;
	}
	public static BattleObjectClass randomBattleObject(BattleObjectClass seed, BattleObjectClass minVal, BattleObjectClass maxVal) {
		rand.setSeed(System.currentTimeMillis());
		float number = (float) ((Math.random()*(maxVal.damage)) + minVal.damage);
		float newDamage = seed.damage+number;
		
		number = (float) ((Math.random()*(maxVal.radius)) + minVal.radius);
		float newRadius = seed.radius+number;
		
		number = (float) ((Math.random()*(maxVal.angle)) + minVal.angle);
		float newAngle  = seed.angle+number;
		
		number = (float) ((Math.random()*(maxVal.resist)) + minVal.resist);
		float newResist = seed.resist+number;
		
		return new BattleObjectClass(newDamage, newRadius, newAngle, newResist);
	}
}
