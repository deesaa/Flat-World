package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class Console {
	public static Console GlobalConsole = new Console();
	
	LuaValue luaConsole;
	Console(){
		luaConsole = CoerceJavaToLua.coerce(this);
	}
	
	public void msg(LuaValue luaMsg) {
		
	}
}
