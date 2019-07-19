package FlatWorld;

public class PickableModif extends Action{
		private BasicObjectClass OwnerObject;
		
		PickableModif(BasicObjectClass Object){
			super(Object, "PICKMODIF");
			Object.Modifiers.pPickableModif = this;
		}
		
		public void setOwner(BasicObjectClass OwnerObject){
			this.OwnerObject = OwnerObject;
		}

		public BasicObjectClass getOwner() {
			if(OwnerObject == null)
				return super.ActionOwner;
			
			PickableModif tempPickable = OwnerObject.Modifiers.pPickableModif;
			BasicObjectClass finalOwner = OwnerObject;
			if(tempPickable != null){
				finalOwner = tempPickable.getOwner();
			}
			return finalOwner;
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
