package FlatWorld;

import java.nio.ByteBuffer;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
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
	
//	ColorClass modifColor = ColorClass.Standard;
	
	
	
	Vector2f wndCurrentPos = new Vector2f(), wndCurrentWidthHeight = new Vector2f();
	
	
	String Name;
	LocationValue xPos, yPos, width, height;
	WindowElement Owner = null;
	WindowElement[] contains;
	ImageClass texture = null;
//	/ColorClass color;
	UniteColorClass uniteColor = ColorRGBAClass.Standard;
	int rendMode = GL11.GL_QUADS;
	boolean lighting = true;
	boolean attachToOwner = false;
	float layerDepth = LayerClass.getDepth("PlayerGUI");
	
	BorderWindow modifBorder;
	
	public LuaValue updateHook = null;
	
	public WindowElement(int leftTopVertexX, int leftTopVertexY, int rightBottomVertexX, int rightBottomVertexY, int elID) {
		this.leftTopVertexX     = leftTopVertexX;
		this.leftTopVertexY 	= leftTopVertexY;
		this.rightBottomVertexX = rightBottomVertexX;
		this.rightBottomVertexY = rightBottomVertexY;
		this.elID 				= elID;
	}
	
	public static WindowElement createWindow(LuaValue luaWindowsEl, LuaValue luaMainFile, WindowElement ownerWnd){
		WindowElement newWindowEl = null;
		String type = luaWindowsEl.get("Type").tojstring();
		
		if(type.compareTo("Bar") == 0){
			newWindowEl = new BarWindow(luaWindowsEl, luaMainFile, ownerWnd);
		}
		if(type.compareTo("Scale") == 0){
			newWindowEl = new ScaleWindow(luaWindowsEl, luaMainFile, ownerWnd);
		}	
		if(type.compareTo("Slider") == 0){
			newWindowEl = new SliderWindow(luaWindowsEl, luaMainFile, ownerWnd);
		}
		if(type.compareTo("Window") == 0){
			newWindowEl = new WindowWindow(luaWindowsEl, luaMainFile, ownerWnd);
		}
		if(type.compareTo("Text") == 0){
			newWindowEl = new TextWindow(luaWindowsEl, luaMainFile, ownerWnd);
		}
		if(type.compareTo("Button") == 0){
			newWindowEl = new ButtonWindow(luaWindowsEl, luaMainFile, ownerWnd);
		}
		if(type.compareTo("FillingBar") == 0){
			newWindowEl = new FillingBarWindow(luaWindowsEl, luaMainFile, ownerWnd);
		}
		if(type.compareTo("Border") == 0){
			newWindowEl = new BorderWindow(luaWindowsEl, luaMainFile, ownerWnd);
		}
		return newWindowEl;
	}

	public WindowElement(LuaValue luaScript, LuaValue luaMainFile, WindowElement ownerWnd) {
		LuaValue updateHookName = luaScript.get("UpdateHook");
		if(!updateHookName.isnil()){
			LuaValue luaUpdateHook = luaMainFile.get(updateHookName);
			if(!luaUpdateHook.isnil())
				this.updateHook = luaUpdateHook;
		} else {
			if(ownerWnd != null && ownerWnd.updateHook != null)
				this.updateHook = ownerWnd.updateHook;
		}
		
		//if(ownerWnd != null && ownerWnd.updateHook != null && !ownerWnd.updateHook.isnil())
		//	this.updateHook = ownerWnd.updateHook;
		
		Name = luaScript.get("Name").tojstring();
		LuaValue luaDepth = luaScript.get("Depth");
		if(!luaDepth.isnil()){
			if(luaDepth.isnumber())
				this.layerDepth = luaDepth.tofloat();	
			else {
				if(luaDepth.tojstring().compareTo("MAX_DEPTH") == 0)
					this.layerDepth = -FlatWorld.mainCamera.zFar+1;
				else 
					this.layerDepth = LayerClass.getDepth(luaDepth.tojstring());
			}
		}
		
		LuaValue pathToTex = luaScript.get("Texture");
		if(!pathToTex.isnil()){
			this.texture = new ImageClass(pathToTex.tojstring());
		}
		
		LuaValue luaColor = luaScript.get("Color");
		if(!luaColor.isnil()){
			this.uniteColor = UniteColorClass.createLuaColor(luaColor);
		}
		
		LuaValue lighting = luaScript.get("Lighting");
		if(!lighting.isnil()){
			this.lighting = lighting.toboolean();
		}
		
		LuaValue location = luaScript.get("Location");
		if(!location.isnil()){
			xPos = new LocationValue(location.get("x"));
			yPos = new LocationValue(location.get("y"));
			width = new LocationValue(location.get("width"));
			height = new LocationValue(location.get("height"));
			attachToOwner = location.get("attach").toboolean();
		}
		
		LuaValue luaBorder = luaScript.get("Border");
		if(!luaBorder.isnil()){
			this.modifBorder = new BorderWindow(luaBorder, luaMainFile, this);
		}
		
		LuaValue luaContains = luaScript.get("Contains");
		if(!luaContains.isnil() && luaContains.length() >= 1){
			contains = new WindowElement[luaContains.length()];
			for(int i = 1; i <= luaContains.length(); i++){
				contains[i-1] = WindowElement.createWindow(luaContains.get(i), luaMainFile, this);
				contains[i-1].Owner = this;
			}
		}
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
	
	public int update(BasicObjectClass Owner) {
		Vector4f pos = this.getFinalWorldPos(Owner);
		float worldXPos = pos.x, worldYPos = pos.y;
		float worldWidth = pos.z, worldHeight = pos.w;
		wndCurrentPos.set(worldXPos, worldYPos);
		wndCurrentWidthHeight.set(worldWidth, worldHeight);
		
		
		if(contains != null){
			for(int i = 0; i < contains.length; i++){
				contains[i].update(Owner);
			}
		}
		return 0;
	}
	
	public void rend(BasicObjectClass Owner) {	
		
		if(contains != null){
			for(int i = 0; i < contains.length; i++){
				contains[i].rend(Owner);
			}
		}
	}
	
	public void rend(int LTXShift, int LTYShift, int RBXShift, int RBYShift) {
		GL11.glEnable(GL11.GL_LIGHTING);
		int LTX = 0, LTY = 0, RBX = 0, RBY = 0;
		
		LTX = (int)(this.zeroPointX+this.leftTopVertexX);
		LTY = (int)(this.zeroPointY+this.leftTopVertexY);
		RBX = (int)(this.zeroPointX+this.rightBottomVertexX);
		RBY = (int)(this.zeroPointY+this.rightBottomVertexY);
	
		Vector2f tempLT = MouseArrowClass.convertToGameSpace(LTX, LTY);
		Vector2f tempRB = MouseArrowClass.convertToGameSpace(RBX, RBY);
			
		finalLTX = tempLT.x;finalLTY = tempLT.y;
		finalRBX = tempRB.x;finalRBY = tempRB.y;
		
		//modifColor.setColorFilter();
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
	
	//public void update(MessagesHandler messagesHandler) {
	//	
	////}
	
	public Vector4f getFinalWorldPos(BasicObjectClass Owner){
		Vector4f pos = new Vector4f();
		Vector2f worldPos = WindowElement.convertToWorldSpace(xPos.getScreenCoordByX(true, false, true), 
														yPos.getScreenCoordByY(true, false, true));
		Vector2f worldWidthHeight = WindowElement.convertToWorldSpace((int)(xPos.getScreenCoordByX(true, false, true)+width.getScreenCoordByX(false, true, false)), 
																(int)(yPos.getScreenCoordByY(true, false, true)+height.getScreenCoordByY(false, true, false)));

		worldWidthHeight.x -= worldPos.x;
		worldWidthHeight.y -= worldPos.y;


	//	if(attachToOwner && xPos.space == LocationValue.WORLD_SPACE && yPos.space == LocationValue.WORLD_SPACE){
	//		worldPos.x = Owner.PosGlobalX + xPos.value;
	//	 	worldPos.y = Owner.PosGlobalY + yPos.value;
	//	}
		

		pos.set(worldPos.x, worldPos.y, worldWidthHeight.x, worldWidthHeight.y);
		return pos;
	}
	
	class LocationValue {
		public static final int PERCENT_SPACE = 1, PIXEL_SPACE = 2, WORLD_SPACE = 3;
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
			case "World":
				space = WORLD_SPACE;
				break;
			}
		}

		public int getScreenCoordByX(boolean owenrsShift, boolean widthSizeCostil, boolean addShiftToSize){
			int finalVal = 0;
			if(widthSizeCostil){
				if(space == PERCENT_SPACE)
					owenrsShift = true;
				if(space == PIXEL_SPACE)
					owenrsShift = false;
			}
			
			switch (space) {
			case PERCENT_SPACE:
				if(owenrsShift && Owner != null){
					if(addShiftToSize)
						finalVal = (int) (Owner.width.getScreenCoordByX(owenrsShift, false, false) * (value * 0.01) + Owner.xPos.getScreenCoordByX(owenrsShift, false, false));
					else
						finalVal = (int) (Owner.width.getScreenCoordByX(owenrsShift, false, false) * (value * 0.01));
				} else {
					finalVal = (int) (FlatWorld.mainCamera.width * (value * 0.01));
				}
				break;
			case PIXEL_SPACE:
				if(owenrsShift && Owner != null){
					finalVal = (int) (Owner.xPos.getScreenCoordByX(true, false, true) + value);
				} else {
					finalVal = (int) value;
				}
				break;
			}
			return finalVal;
		}
		

		public int getScreenCoordByY(boolean owenrsShift, boolean widthSizeCostil, boolean addShiftToSize){
			int finalVal = 0;
			if(widthSizeCostil){
				if(space == PERCENT_SPACE)
					owenrsShift = true;
				if(space == PIXEL_SPACE)
					owenrsShift = false;
			}
			
			switch (space) {
			case PERCENT_SPACE:
				if(owenrsShift && Owner != null){
					if(addShiftToSize)
						finalVal = (int) (Owner.height.getScreenCoordByY(owenrsShift, false, false) * (value * 0.01) + Owner.yPos.getScreenCoordByY(owenrsShift, false, false));
					else
						finalVal = (int) (Owner.height.getScreenCoordByY(owenrsShift, false, false) * (value * 0.01));
				} else {
					finalVal = (int) (FlatWorld.mainCamera.height * (value * 0.01));
				}
				break;
			case PIXEL_SPACE:
				if(owenrsShift && Owner != null){
					finalVal = (int) (Owner.yPos.getScreenCoordByY(true, false, true) + value);
				} else {
					finalVal = (int) value;
				}
				break;
			}
			return finalVal;
		}
	}

	public void rendQuad(Vector2f pos, Vector2f widthHeight, BasicObjectClass Owner) {
		if(!lighting)
			GL11.glDisable(GL11.GL_LIGHTING);

		
		if(uniteColor != null)
			uniteColor.setColor();
		
		if(texture != null){
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			QuadClass tempQuad = new QuadClass(widthHeight.x, widthHeight.y, false);
			GL11.glTranslatef(pos.x, pos.y, this.layerDepth);
			tempQuad.rend(texture, null);
			GL11.glLoadIdentity();
		} else {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			
			GL11.glTranslatef(pos.x, pos.y, layerDepth);
			GL11.glBegin(rendMode);
			GL11.glVertex3f(0, 				0, 			 	0.0f);
			GL11.glVertex3f(widthHeight.x, 	0, 			 	0.0f);
		    GL11.glVertex3f(widthHeight.x, 	widthHeight.y, 	0.0f);
		    GL11.glVertex3f(0, 				widthHeight.y,	0.0f);
		    GL11.glEnd();
		    GL11.glLoadIdentity();
		    
		    
		  /* GL11.glTranslatef(pos.x, pos.y, this.layerDepth);
			GL11.glBegin(rendMode);
			GL11.glVertex3f(0, 				0, 			 	0.0f);
			GL11.glVertex3f(widthHeight.x, 	0, 			 	0.0f);
		    GL11.glVertex3f(widthHeight.x, 	widthHeight.y, 	0.0f);
		    GL11.glVertex3f(0, 				widthHeight.y,	0.0f);
		    GL11.glEnd();
		    GL11.glLoadIdentity();*/
		}
		
		if(!lighting)
			GL11.glEnable(GL11.GL_LIGHTING);
		
		if(modifBorder != null)
			modifBorder.rendQuad(pos, widthHeight, Owner);
	}

	public WindowElement getWindow(String wndName) {
		if(this.Name.compareTo(wndName) == 0){
			return this;
		} else {
			if(contains != null){
				for(int i = 0; i < contains.length; i++){
					WindowElement tWnd = contains[i].getWindow(wndName);
					if(tWnd != null)
						return tWnd;
				}
			}
		}
		return null;
	}

	public static LuaValue luaOf(String path) {
		LuaValue luaVal = JsePlatform.standardGlobals();
		luaVal.get("dofile").call(LuaValue.valueOf(path));
		  
		return luaVal;
	}
	
	public static Vector2f convertToWorldSpace(int screenCoordByX, int screenCoordByY) {
		return MouseArrowClass.convertToGameSpace(screenCoordByX, screenCoordByY);
	}
}