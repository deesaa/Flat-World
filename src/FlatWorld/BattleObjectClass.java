package FlatWorld;

import java.util.ArrayList;

public class BattleObjectClass {
	ArrayList<BattleObjectClass> battleObjectState = new ArrayList<BattleObjectClass>();
	
	public void addState(BattleObjectClass battleState){
		battleObjectState.add(battleState);
	}
	
	public class Weapon extends BattleObjectClass{
		float damage;
		float radius;
		float angle;
		
		public Weapon(float damage, float radius, float angle) {
			this.damage = damage;
			this.radius = radius;
			this.angle  = angle;
		}
	}
	
	public class Armor extends BattleObjectClass{
		float resist;

		public Armor(float resist) {
			this.resist = resist;
		}
	}
}
