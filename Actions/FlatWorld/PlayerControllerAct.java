package FlatWorld;

import org.lwjgl.input.Keyboard;


public class PlayerControllerAct extends Action {
	public PlayerControllerAct(BasicObjectClass Object) {
		super(Object, "PLCONT");
	}
	
	public void updateAction(BasicObjectClass Object) {
		float tempMoveX = 0.0f, tempMoveY = 0.0f;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
		    Object.Animation.pickAnimation(2);
			Object.Modifiers.pLookingSystem.VecViewDirX = -1.0f; 
			Object.Modifiers.pLookingSystem.VecViewDirY =  0.0f; 
			tempMoveX = -Object.moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Object.Animation.pickAnimation(3);
			Object.Modifiers.pLookingSystem.VecViewDirX =  1.0f; 
			Object.Modifiers.pLookingSystem.VecViewDirY =  0.0f; 
			tempMoveX = Object.moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			 Object.Animation.pickAnimation(1);
			Object.Modifiers.pLookingSystem.VecViewDirX =  0.0f; 
			Object.Modifiers.pLookingSystem.VecViewDirY =  1.0f; 
			tempMoveY = Object.moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Object.Animation.pickAnimation(0);
			Object.Modifiers.pLookingSystem.VecViewDirX =  0.0f; 
			Object.Modifiers.pLookingSystem.VecViewDirY = -1.0f; 
			tempMoveY = -Object.moveSpeed;
		}
		
		if (tempMoveX != 0 || tempMoveY != 0) {
			Object.move(tempMoveX, tempMoveY, 0.0f);
			MapsManager.updatePlayerPos(Object.OwnedMapID, Object.PosGlobalX,
					Object.PosGlobalY);
		} else {
			Object.Animation.restart();
		}
	}

	public void rendAction(BasicObjectClass Object) {

	}

	@Override
	public void zeroAction(BasicObjectClass basicObjectClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		// TODO Auto-generated method stub
		
	}
}
