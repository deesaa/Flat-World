package FlatWorld;

import org.lwjgl.input.Keyboard;


public class PlayerControllerAct extends Action {
	public PlayerControllerAct(BasicObjectClass Object) {
		super(Object);
	}
	
	public void updateAction(BasicObjectClass Object) {
		float tempMoveX = 0.0f, tempMoveY = 0.0f;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
		    Object.Animations.pickAnimation("left");
			Object.Modifiers.pointerToLookingSystem.VecViewDirX = -1.0f; 
			Object.Modifiers.pointerToLookingSystem.VecViewDirY =  0.0f; 
			tempMoveX = -Object.moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			 Object.Animations.pickAnimation("right");
			Object.Modifiers.pointerToLookingSystem.VecViewDirX =  1.0f; 
			Object.Modifiers.pointerToLookingSystem.VecViewDirY =  0.0f; 
			tempMoveX = Object.moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			 Object.Animations.pickAnimation("forward");
			Object.Modifiers.pointerToLookingSystem.VecViewDirX =  0.0f; 
			Object.Modifiers.pointerToLookingSystem.VecViewDirY =  1.0f; 
			tempMoveY = Object.moveSpeed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			 Object.Animations.pickAnimation("back");
			Object.Modifiers.pointerToLookingSystem.VecViewDirX =  0.0f; 
			Object.Modifiers.pointerToLookingSystem.VecViewDirY = -1.0f; 
			tempMoveY = -Object.moveSpeed;
		}
		
		if (tempMoveX != 0 || tempMoveY != 0) {
			Object.move(tempMoveX, tempMoveY, 0.0f);
			Object.Animations.updateAnimation();
			MapsManager.updatePlayerPos(Object.OwnedMapID, Object.PosGlobalX,
					Object.PosGlobalY);
		} else {
			Object.Animations.restart();
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
