package FlatWorld;

import org.lwjgl.input.Keyboard;

public class PlayerControllerAct implements Action {
	public void updateAction(BasicObjectClass Object) {
		float tempMoveX = 0.0f, tempMoveY = 0.0f;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Object.Textures.setAnimation(3);
			Object.Modifiers.pointerToLookingSystem.VecViewDirX = -1.0f; 
			Object.Modifiers.pointerToLookingSystem.VecViewDirY =  0.0f; 
			tempMoveX = -Object.moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Object.Textures.setAnimation(2);
			Object.Modifiers.pointerToLookingSystem.VecViewDirX =  1.0f; 
			Object.Modifiers.pointerToLookingSystem.VecViewDirY =  0.0f; 
			tempMoveX = Object.moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			Object.Textures.setAnimation(0);
			Object.Modifiers.pointerToLookingSystem.VecViewDirX =  0.0f; 
			Object.Modifiers.pointerToLookingSystem.VecViewDirY =  1.0f; 
			tempMoveY = Object.moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Object.Textures.setAnimation(1);
			Object.Modifiers.pointerToLookingSystem.VecViewDirX =  0.0f; 
			Object.Modifiers.pointerToLookingSystem.VecViewDirY = -1.0f; 
			tempMoveY = -Object.moveSpeed;
		}
		if (tempMoveX != 0 || tempMoveY != 0) {
			Object.Textures.updateAnimation();
			Object.move(tempMoveX, tempMoveY, 0.0f);
			MapsManager.updatePlayerPos(Object.OwnedMapID, Object.PosGlobalX,
					Object.PosGlobalY);
		} else {
			Object.Textures.restartAnimation();
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
