package FlatWorld;

import java.util.Random;


public class RandomizeTool {
	private static Random rand = new Random();
	private static int randInt = 0;
	public static boolean setColor(BasicObjectClass Object, ColorClass newColor, int minChange, int maxChange, float chanceOfDoing){
		if(RandomizeTool.percentChance(chanceOfDoing)){
			if(minChange != 0 || maxChange != 0)
				Object.modifColor = ColorClass.randomColor(newColor, minChange, maxChange);
			else
				Object.modifColor = newColor;
			return true;
		}
		return false;
	}
	
	public static BasicObjectClass setAnimation(BasicObjectClass Object, AnimationClass newAnimation){
		Object.Animation = newAnimation;
		return Object;
	}

	public static boolean setBattleObject(BasicObjectClass Object, BattleObjectClass newBattleObject, 
			BattleObjectClass minChange, BattleObjectClass maxChange, float chanceOfDoing) {
		BattleObjectAct tempBattleObjectAct = Object.Modifiers.pBattleObjectAct;
		if(tempBattleObjectAct == null){
			Object.printDefObjectInfo();
			System.out.println("RandomizeTool: Object doesn't have BattleObjectAct; Skipped");
			return false;
		}
		if(RandomizeTool.percentChance(chanceOfDoing)){
			if(minChange != null || maxChange != null)
				Object.Modifiers.pBattleObjectAct.battleObjectStatesList = BattleObjectClass.randomBattleObject(newBattleObject, minChange, maxChange);
			else
				Object.Modifiers.pBattleObjectAct.battleObjectStatesList = newBattleObject;
			return true;
		}
		return false;
	}
	
	public static boolean percentChance(float chance){
		RandomizeTool.updateSeed();
		randInt = rand.nextInt(100);
		if(randInt*0.01f < chance){
			return true;
		}
		return false;
	}
	
	public static int randValue(int min, int max){
		RandomizeTool.updateSeed();
		return (int)(rand.nextFloat()*(max-min+1))+min;
	}
	
	private static void updateSeed(){
		//try { 
		//	Thread.sleep(1);
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//} 
		//rand.setSeed((System.currentTimeMillis()));
		
		rand.nextGaussian();
	}
}
