package FlatWorld;

import java.util.ArrayList;

public class BattleObjectClass {
	ArrayList<BattleObjectElement> battleObjectStateElements = new ArrayList<BattleObjectElement>();
	
	public void addState(BattleObjectElement battleState){
		battleObjectStateElements.add(battleState);
	}
	
	public float getGlobalDamage(){
		float finalDamage = 0;
		for(int i = 0; i < battleObjectStateElements.size(); i++){
			finalDamage += battleObjectStateElements.get(i).damage;
		}
		return finalDamage;
	}

	public float getGlobalResist() {
		float finalResist = 0;
		for(int i = 0; i < battleObjectStateElements.size(); i++){
			finalResist += battleObjectStateElements.get(i).resist;
		}
		return finalResist;
	}
}
