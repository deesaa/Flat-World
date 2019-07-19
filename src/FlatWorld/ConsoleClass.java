package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class ConsoleClass {
	LuaValue luaConsole;
	
	public ConsoleClass() {
		luaConsole = CoerceJavaToLua.coerce(this);
	}
	
	public void sendMessage(LuaValue message){
		
	}
}
