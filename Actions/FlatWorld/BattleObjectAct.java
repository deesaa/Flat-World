package FlatWorld;


public class BattleObjectAct extends Action{
	BattleObjectClass battleObjectStatesList;
	
	public BattleObjectAct(BasicObjectClass Object, BattleObjectClass battleObjectStatesList){
		super(Object, "BATTOBJ");
		Object.Modifiers.pBattleObjectAct = this;
		
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
