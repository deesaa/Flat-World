package FlatWorld;

import java.util.Random;

public class ZombieControllerAct implements Action {
	public void updateAction(BasicObjectClass Object) {

		int minGenMoveVec = 1, maxGenMoveVec = 8;
		Random generator = new Random(System.currentTimeMillis() >> 1 * Object.ObjectID);
		int moveVector = minGenMoveVec + generator.nextInt(maxGenMoveVec - minGenMoveVec + 1);

		float tempMoveX = 0.0f, tempMoveY = 0.0f;

		switch (moveVector) {
		case 1:
			tempMoveX = Object.moveSpeed;
			break;
		case 2:
			tempMoveX = -Object.moveSpeed;
			break;
		case 3:
			tempMoveY = Object.moveSpeed;
			break;
		case 4:
			tempMoveY = -Object.moveSpeed;
			break;
		case 5:
			tempMoveX = Object.moveSpeed;
			tempMoveY = Object.moveSpeed;
			break;
		case 6:
			tempMoveX = -Object.moveSpeed;
			tempMoveY = Object.moveSpeed;
			break;
		case 7:
			tempMoveX = Object.moveSpeed;
			tempMoveY = -Object.moveSpeed;
			break;
		case 8:
			tempMoveX = -Object.moveSpeed;
			tempMoveY = -Object.moveSpeed;
			break;
		}

		if (tempMoveX != 0 || tempMoveY != 0) {
			Object.Animations.updateAnimation();
			Object.move(tempMoveX, tempMoveY, 0.0f);
		} else {
			Object.Animations.restart();
		}
	}

	public void rendAction(BasicObjectClass Object) {
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
	
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}
}
