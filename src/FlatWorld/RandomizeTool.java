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
	
	public static BasicObjectClass setAnimation(BasicObjectClass Object, AnimationsList newAnimations){
		Object.Animations = newAnimations;
		return Object;
	}

	public static boolean setBattleObject(BasicObjectClass Object, BattleObjectClass newBattleObject, 
			BattleObjectClass minChange, BattleObjectClass maxChange, float chanceOfDoing) {
		BattleObjectAct tempBattleObjectAct = Object.Modifiers.pointerToBattleObjectAct;
		if(tempBattleObjectAct == null){
			Object.printDefObjectInfo();
			System.out.println("RandomizeTool: Object doesn't have BattleObjectAct; Skipped");
			return false;
		}
		if(RandomizeTool.percentChance(chanceOfDoing)){
			if(minChange != null || maxChange != null)
				Object.Modifiers.pointerToBattleObjectAct.battleObjectStatesList = BattleObjectClass.randomBattleObject(newBattleObject, minChange, maxChange);
			else
				Object.Modifiers.pointerToBattleObjectAct.battleObjectStatesList = newBattleObject;
			return true;
		}
		return false;
	}
	
	public static boolean percentChance(float chance){
		try { 
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		rand.setSeed((System.currentTimeMillis()));
		randInt = rand.nextInt(100);
		if(randInt*0.01f < chance){
			return true;
		}
		return false;
	}
}