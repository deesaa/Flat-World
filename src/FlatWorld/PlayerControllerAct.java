package FlatWorld;

import org.lwjgl.input.Keyboard;

public class PlayerControllerAct implements Action{
	public void update(BasicObjectClass Object) {
		float tempMoveX = 0.0f, tempMoveY = 0.0f;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)){
			Object.Textures.setAnimation(3);
			tempMoveX = -Object.moveSpeed;
		}
	    if (Keyboard.isKeyDown(Keyboard.KEY_D)){
	    	Object.Textures.setAnimation(2);
	    	tempMoveX =  Object.moveSpeed;
	    }  
	    if (Keyboard.isKeyDown(Keyboard.KEY_W)){
	    	Object.Textures.setAnimation(0);
	    	tempMoveY =  Object.moveSpeed;
	    }
	    if (Keyboard.isKeyDown(Keyboard.KEY_S)){
	    	Object.Textures.setAnimation(1);
	    	tempMoveY = -Object.moveSpeed;
	    }
		if(tempMoveX != 0 || tempMoveY != 0) {
			Object.Textures.updateAnimation();
			Object.move(tempMoveX, tempMoveY, 0.0f);
			MapsManager.updatePlayerPos(Object.OwnedMapID, Object.PosGlobalX, Object.PosGlobalY);
		} else {
			Object.Textures.restartAnimation();
		}
	}
}
