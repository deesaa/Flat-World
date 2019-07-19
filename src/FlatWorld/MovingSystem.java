package FlatWorld;

public class MovingSystem implements Action{

	public MovingSystem(BasicObjectClass Object) {
		Object.Modifiers.pointerToMovingSystem = this;
	}
	
	public void directMovingTo(BasicObjectClass Origin, BasicObjectClass Dir){
		float x, y;
		float normalX, normalY;
		x = Dir.PosGlobalX - Origin.PosGlobalX;
		y = Dir.PosGlobalY - Origin.PosGlobalY;
		double vecLenght 	= Math.sqrt(x*x + y*y);
		double vecInvLenght = 1/vecLenght;
		normalX = (float)(x*vecInvLenght);
		normalY = (float)(y*vecInvLenght);
		
		Origin.move(normalX * Origin.moveSpeed, normalY * Origin.moveSpeed, 0.0f);
		Origin.Textures.updateAnimation();
	}
	
	public void updateAction(BasicObjectClass Object) {
		
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void rendButtons(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		
	}

	@Override
	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		// TODO Auto-generated method stub
		
	}
}
