package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;

public class UniteColorClass {
	boolean lighting;
	
	UniteColorClass(boolean lighting){
		this.lighting = lighting;
	}
	
	public UniteColorClass(LuaValue color) {
		LuaValue lt = color.get("Lighting");
		if(!lt.isnil())
			this.lighting = lt.toboolean();
	}

	public void setColor(){
		if(!lighting)
			GL11.glDisable(GL11.GL_LIGHTING);
	}

	public static UniteColorClass createLuaColor(LuaValue luaColor) {
		int mode = luaColor.get("Mode").toint();
		
		if(mode == 1){
			return new ColorRGBAClass(luaColor);
		} else if(mode == 2) {
			return new MaterialColorClass(luaColor);
		}
		
		return null;
	}
}
