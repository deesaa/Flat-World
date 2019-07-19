package FlatWorld;

public abstract class Action {
	BasicObjectClass ActionOwner;
	Action(BasicObjectClass ActionOwner){
		this.ActionOwner = ActionOwner;
	}
	
	public abstract void updateAction(BasicObjectClass Object);

	public abstract void rendAction(BasicObjectClass Object);

	public abstract void zeroAction(BasicObjectClass basicObjectClass);

	public abstract void doTheAction(BasicObjectClass Object, StructOfOffer Offer);
}
