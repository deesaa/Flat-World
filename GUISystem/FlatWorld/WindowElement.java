package FlatWorld;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class WindowElement {
	int leftTopVertexX, leftTopVertexY, rightBottomVertexX, rightBottomVertexY;
	WinPos pos;
	
	public WindowElement(int leftTopVertexX, int leftTopVertexY, int rightBottomVertexX, int rightBottomVertexY) {
		this.leftTopVertexX     = leftTopVertexX;
		this.leftTopVertexY 	= leftTopVertexY;
		this.rightBottomVertexX = rightBottomVertexX;
		this.rightBottomVertexY = rightBottomVertexY;
	}
	
	public void setPosition(WinPos pos) {
		this.pos = pos;
	}

	public void rend(int LTXShift, int LTYShift, int RBXShift, int RBYShift) {
		float finalLTX = 0, finalLTY = 0, finalRBX = 0, finalRBY = 0;
		int LTX, LTY, RBX, RBY;
		
		LTX = this.leftTopVertexX+LTXShift;
		LTY = this.leftTopVertexY+(this.rightBottomVertexY)+RBYShift;
		RBX = this.rightBottomVertexX+(this.leftTopVertexX)+LTXShift;
	    RBY = this.rightBottomVertexY+RBYShift;

	    Vector2f tempLT = MouseArrowClass.convertToGameSpace(LTX, LTY);
		Vector2f tempRB = MouseArrowClass.convertToGameSpace(RBX, RBY);
		
		finalLTX = tempLT.x;finalLTY = tempLT.y;
		finalRBX = tempRB.x;finalRBY = tempRB.y;
		GL11.glBegin(GL11.GL_LINES);		
		GL11.glVertex3f(finalLTX, finalLTY, -(StartGame.zFar-1));
		GL11.glVertex3f(finalLTX, finalRBY, -(StartGame.zFar-1));
		
		GL11.glVertex3f(finalLTX, finalRBY, -(StartGame.zFar-1));
		GL11.glVertex3f(finalRBX, finalRBY, -(StartGame.zFar-1));
		
		GL11.glVertex3f(finalRBX, finalLTY, -(StartGame.zFar-1));
		GL11.glVertex3f(finalRBX, finalRBY, -(StartGame.zFar-1));
		
		GL11.glVertex3f(finalLTX, finalLTY, -(StartGame.zFar-1));
		GL11.glVertex3f(finalRBX, finalLTY, -(StartGame.zFar-1));
		GL11.glEnd();
	}
}