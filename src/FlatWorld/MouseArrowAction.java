package FlatWorld;

import org.lwjgl.input.Mouse;

public class MouseArrowAction implements Action{
	int lastX = Mouse.getDX(), lastY = Mouse.getDY();
	
	public void updateAction(BasicObjectClass Object) {
		float X = Mouse.getX()* 0.05f;
		float Y = Mouse.getY()* 0.05f;
		Object.overRelocateObject(X, Y, 0);
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void rendButtons(BasicObjectClass Object) {
		
	}

	@Override
	public void zeroAction(BasicObjectClass basicObjectClass) {
		// TODO Auto-generated method stub
		
	}
}
