package FlatWorld;


public class BattleObjectAct implements Action{
	BattleObjectClass battleObjectStatesList;
	
	public BattleObjectAct(BasicObjectClass Object, BattleObjectClass battleObjectStatesList){
		Object.Modifiers.pointerToBattleObjectAct = this;
		
		this.battleObjectStatesList = battleObjectStatesList;
	}

	public void updateAction(BasicObjectClass Object) {

	}

	public void rendAction(BasicObjectClass Object) {

	}

	public void zeroAction(BasicObjectClass basicObjectClass) {

	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
	}
}
