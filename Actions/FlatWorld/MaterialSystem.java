package FlatWorld;

public class MaterialSystem extends Action{
	MaterialClass material;
	MaterialSystem(BasicObjectClass ActionOwner, MaterialClass material) {
		super(ActionOwner);
		ActionOwner.Modifiers.pointerToMaterialSystem = this;
		this.material = material;
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
