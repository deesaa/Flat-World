package FlatWorld;

public abstract class Action {
	BasicObjectClass ActionOwner;
	String systemIdent;
	Action(BasicObjectClass ActionOwner, String systemIdent){
		this.ActionOwner = ActionOwner;
		this.systemIdent = systemIdent;
	}
	
	public abstract void updateAction(BasicObjectClass Object);

	public abstract void rendAction(BasicObjectClass Object);

	public abstract void zeroAction(BasicObjectClass basicObjectClass);

	public abstract void doTheAction(BasicObjectClass Object, StructOfOffer Offer);
}
