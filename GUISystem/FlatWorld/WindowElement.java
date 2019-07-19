package FlatWorld;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class WindowElement {
	int leftTopVertexX, leftTopVertexY, rightBottomVertexX, rightBottomVertexY;
	float finalLTX = 0, finalLTY = 0, finalRBX = 0, finalRBY = 0;
	int zeroPointX, zeroPointY;
	int elID;
	WindowElement relativeTo;
	WinPos pos;
	
	ColorClass modifColor = ColorClass.Standard;
	
	public WindowElement(int leftTopVertexX, int leftTopVertexY, int rightBottomVertexX, int rightBottomVertexY, int elID) {
		this.leftTopVertexX     = leftTopVertexX;
		this.leftTopVertexY 	= leftTopVertexY;
		this.rightBottomVertexX = rightBottomVertexX;
		this.rightBottomVertexY = rightBottomVertexY;
		this.elID 				= elID;
	}
	
	public void setPosition(WinPos pos) {
		this.pos = pos;
		switch (this.pos) {
		case Center:
			zeroPointX = (int) (Display.getWidth()*0.5f);
			zeroPointY = (int) (Display.getHeight()*0.5f);
			break;

		default:
			break;
		}
	}

	public void rend(int LTXShift, int LTYShift, int RBXShift, int RBYShift) {	
		int LTX = 0, LTY = 0, RBX = 0, RBY = 0;
		
		LTX = (int)(this.zeroPointX+this.leftTopVertexX);
		LTY = (int)(this.zeroPointY+this.leftTopVertexY);
		RBX = (int)(this.zeroPointX+this.rightBottomVertexX);
		RBY = (int)(this.zeroPointY+this.rightBottomVertexY);
	
		Vector2f tempLT = MouseArrowClass.convertToGameSpace(LTX, LTY);
		Vector2f tempRB = MouseArrowClass.convertToGameSpace(RBX, RBY);
			
		finalLTX = tempLT.x;finalLTY = tempLT.y;
		finalRBX = tempRB.x;finalRBY = tempRB.y;
		
		modifColor.setColorFilter();
		GL11.glBegin(GL11.GL_QUADS);	
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(finalLTX, finalLTY, -(StartGame.zFar-1));
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(finalLTX, finalRBY, -(StartGame.zFar-1));
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(finalRBX, finalRBY, -(StartGame.zFar-1));
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(finalRBX, finalLTY, -(StartGame.zFar-1));
		GL11.glEnd();
		GL11.glLoadIdentity();
	}

	public void setScalingMode(ScalingMode scalingMode) {
		
	}

	public void setVertexPoints(int leftTopVertexX, int leftTopVertexY, int rightBottomVertexX, int rightBottomVertexY) {
		this.leftTopVertexX     = leftTopVertexX;
		this.leftTopVertexY 	= leftTopVertexY;
		this.rightBottomVertexX = rightBottomVertexX;
		this.rightBottomVertexY = rightBottomVertexY;
	}

	public void relativeTo(int wndElID, Windows windowsSys) {
		for(int i = 0; i < windowsSys.wndEls.size(); i++){
			if(windowsSys.wndEls.get(i).elID == wndElID)
				this.relativeTo = windowsSys.wndEls.get(i);
		}
	}

	public void shiftEl(int shiftX, int shiftY) {
		this.leftTopVertexX += shiftX;
		this.rightBottomVertexX += shiftX;
		this.leftTopVertexY += shiftY;
		this.rightBottomVertexY += shiftY;
	}

	public void update(MessagesHandler messagesHandler) {
		
	}
}