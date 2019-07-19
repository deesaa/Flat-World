package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class LayoutClass {
	int leftTopVertexX, leftTopVertexY, rightBottomVertexX, rightBottomVertexY;
	WinPos layoutPos;
	ScalingMode scalingMode;
	ArrayList<WindowElement> wndEls = new ArrayList<WindowElement>(); 
	
	public void addElement(WindowElement wndEl){
		wndEls.add(wndEl);
	}
	
	public void setPosition(WinPos layoutPos) {
		this.layoutPos = layoutPos;
	}

	public void setScalingMode(ScalingMode scalingMode) {
		this.scalingMode = scalingMode;
	}

	public void setVertexPoints(int leftTopVertexX, int leftTopVertexY, int rightBottomVertexX, int rightBottomVertexY) {
		this.leftTopVertexX     = leftTopVertexX;
		this.leftTopVertexY 	= leftTopVertexY;
		this.rightBottomVertexX = rightBottomVertexX;
		this.rightBottomVertexY = rightBottomVertexY;
	}

	public void rend() {
		float finalLTX = 0, finalLTY = 0, finalRBX = 0, finalRBY = 0;
		int LTX = 0, LTY = 0, RBX = 0, RBY = 0;
		
		if(layoutPos == WinPos.Center){
			LTX = (int)(Windows.displayWidth*0.5f-this.leftTopVertexX);
			LTY = (int)(Windows.displayHeight*0.5f+this.leftTopVertexY);
			RBX = (int)(Windows.displayWidth*0.5f+this.rightBottomVertexX);
		    RBY = (int)(Windows.displayHeight*0.5f-this.rightBottomVertexY);
	
			Vector2f tempLT = MouseArrowClass.convertToGameSpace(LTX, LTY);
			Vector2f tempRB = MouseArrowClass.convertToGameSpace(RBX, RBY);
			
			finalLTX = tempLT.x;finalLTY = tempLT.y;
			finalRBX = tempRB.x;finalRBY = tempRB.y;
		}
		
		if(layoutPos == WinPos.Left){
			LTX = 0;
			LTY = (int)(Windows.displayHeight*0.5f+this.leftTopVertexY);
			RBX = this.rightBottomVertexX;
		    RBY = (int)(Windows.displayHeight*0.5f-this.rightBottomVertexY);
	
			Vector2f tempLT = MouseArrowClass.convertToGameSpace(LTX, LTY);
			Vector2f tempRB = MouseArrowClass.convertToGameSpace(RBX, RBY);
			
			finalLTX = tempLT.x;finalLTY = tempLT.y;
			finalRBX = tempRB.x;finalRBY = tempRB.y;
		}
		
		if(layoutPos == WinPos.LeftTop){
			LTX = 0;
			LTY = Windows.displayHeight;
			RBX = this.rightBottomVertexX;
		    RBY = Windows.displayHeight-this.rightBottomVertexY;
	
			Vector2f tempLT = MouseArrowClass.convertToGameSpace(LTX, LTY);
			Vector2f tempRB = MouseArrowClass.convertToGameSpace(RBX, RBY);
			
			finalLTX = tempLT.x;finalLTY = tempLT.y;
			finalRBX = tempRB.x;finalRBY = tempRB.y;
		}
		
		if(layoutPos == WinPos.LeftBottom){
			LTX = this.leftTopVertexX;
			LTY = this.leftTopVertexY;
			RBX = 0;
		    RBY = 0;
	
			Vector2f tempLT = MouseArrowClass.convertToGameSpace(LTX, LTY);
			Vector2f tempRB = MouseArrowClass.convertToGameSpace(RBX, RBY);
			
			finalLTX = tempLT.x;finalLTY = tempLT.y;
			finalRBX = tempRB.x;finalRBY = tempRB.y;
		}
		
		if(layoutPos == WinPos.RightTop){
			LTX = Windows.displayWidth-this.leftTopVertexX;
			LTY = Windows.displayHeight;
			RBX = Windows.displayWidth;
		    RBY = Windows.displayHeight-this.rightBottomVertexY;
	
			Vector2f tempLT = MouseArrowClass.convertToGameSpace(LTX, LTY);
			Vector2f tempRB = MouseArrowClass.convertToGameSpace(RBX, RBY);
			
			finalLTX = tempLT.x;finalLTY = tempLT.y;
			finalRBX = tempRB.x;finalRBY = tempRB.y;
		}
		
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
		
		for(int i = 0; i < wndEls.size(); i++){
			wndEls.get(i).rend(LTX, LTY, RBX, RBY);
		}
	}
}
