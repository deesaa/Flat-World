package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

public class WindowElement {
	int leftTopVertexX, leftTopVertexY, rightBottomVertexX, rightBottomVertexY;
	float finalLTX = 0, finalLTY = 0, finalRBX = 0, finalRBY = 0;
	int zeroPointX, zeroPointY;
	int elID;
	WinPos pos;
	
	ColorClass modifColor = ColorClass.Standard;
	
	
	String Name;
	LocationValue xPos, yPos, width, height;
	WindowElement Owner = null;
	WindowElement[] contains;
	
	public WindowElement(int leftTopVertexX, int leftTopVertexY, int rightBottomVertexX, int rightBottomVertexY, int elID) {
		this.leftTopVertexX     = leftTopVertexX;
		this.leftTopVertexY 	= leftTopVertexY;
		this.rightBottomVertexX = rightBottomVertexX;
		this.rightBottomVertexY = rightBottomVertexY;
		this.elID 				= elID;
	}

	public WindowElement(LuaValue luaScript) {
		LuaValue location = luaScript.get("Location");
		Name = luaScript.get("Name").tojstring();
		
		xPos = new LocationValue(location.get("x"));
		yPos = new LocationValue(location.get("y"));
		width = new LocationValue(location.get("width")).add(xPos);
		height = new LocationValue(location.get("height")).add(yPos);
		
		LuaValue luaContains = luaScript.get("Contains");
		if(!luaContains.isnil() && luaContains.length() >= 1){
			contains = new WindowElement[luaContains.length()];
			for(int i = 1; i <= luaContains.length(); i++){
				contains[i-1] = Windows.createWindow(luaContains.get(i));
				contains[i-1].Owner = this;
			}
		}
	}

	/*private WindowElement findWindow(String searchingWndName) {
		if(Owner != null){
			if(Owner.Name.compareTo(searchingWndName) == 0)
				return Owner;
			else 
				return Owner.findWindow(searchingWndName);
		} else 
			return null;
	}*/

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
	
	public void rend() {	
		
		if(contains != null){
			for(int i = 0; i < contains.length; i++){
				contains[i].rend();
			}
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

	//public void relativeTo(int wndElID, Windows windowsSys) {
	//	for(int i = 0; i < windowsSys.wndEls.size(); i++){
	//		if(windowsSys.wndEls.get(i).elID == wndElID)
	//			this.relativeTo = windowsSys.wndEls.get(i);
	//	}
	//}

	public void shiftEl(int shiftX, int shiftY) {
		this.leftTopVertexX += shiftX;
		this.rightBottomVertexX += shiftX;
		this.leftTopVertexY += shiftY;
		this.rightBottomVertexY += shiftY;
	}

	public void update(MessagesHandler messagesHandler) {
		
	}
	
	public Vector4f getFinalWorldPos(){
		Vector4f pos = new Vector4f();
		Vector2f worldPos = Windows.convertToWorldSpace(xPos.getScreenCoordByX(), yPos.getScreenCoordByY());
		Vector2f worldWidthHeight = Windows.convertToWorldSpace(xPos.getScreenCoordByX()+width.getScreenCoordByX(), yPos.getScreenCoordByY()+height.getScreenCoordByY());
		worldWidthHeight.x -= worldPos.x;
		worldWidthHeight.y -= worldPos.y;
		pos.set(worldPos.x, worldPos.y, worldWidthHeight.x, worldWidthHeight.y);
		return pos;
	}
	
	class LocationValue {
		public static final int PERCENT_SPACE = 1, PIXEL_SPACE = 2;
		float value = -1;
		int space = -1;

		public LocationValue(LuaValue locValue) {
			value = locValue.get(1).tofloat();
			String strSpace = locValue.get(2).tojstring();
			
			switch (strSpace) {
			case "Percent":
				space = PERCENT_SPACE;
				break;
			case "Pixel":
				space = PIXEL_SPACE;
				break;
			}
		}

		public LocationValue add(LocationValue locVal) {
			if(this.space == locVal.space){
				this.value += locVal.value;
			}
			return this;
		}
		
		public int getScreenCoordByX(){
			int finalVal = 0;
			switch (space) {
			case PERCENT_SPACE:
				if(Owner != null){
					finalVal = (int) (Owner.width.value * (value * 0.01) + Owner.xPos.getScreenCoordByX());
				} else {
					finalVal = (int) (FlatWorld.mainCamera.width * (value * 0.01));
				}
				break;
			case PIXEL_SPACE:
				if(Owner != null){
					finalVal = (int) (Owner.xPos.getScreenCoordByX() + value);
				} else {
					finalVal = (int) value;
				}
				break;
			}
			return finalVal;
		}
		

		public int getScreenCoordByY(){
			int finalVal = 0;
			switch (space) {
			case PERCENT_SPACE:
				if(Owner != null){
					finalVal = (int) (Owner.height.value * (value * 0.01) + Owner.xPos.getScreenCoordByY());
				} else {
					finalVal = (int) (FlatWorld.mainCamera.height * (value * 0.01));
				}
				break;
			case PIXEL_SPACE:
				if(Owner != null){
					finalVal = (int) (Owner.yPos.getScreenCoordByY() + value);
				} else {
					finalVal = (int) value;
				}
				break;
			}
			return finalVal;
		}
	}
}