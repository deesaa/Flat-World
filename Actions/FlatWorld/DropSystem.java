package FlatWorld;

public class DropSystem extends Action{
	DropSystem(BasicObjectClass ActionOwner) {
		super(ActionOwner, "DROP");
		RandomizeTool.randValue(2, 5);
	}
	
	public void addDrop(BasicObjectClass Object, int dropChance){
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
