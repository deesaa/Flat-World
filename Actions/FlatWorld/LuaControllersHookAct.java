package FlatWorld;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class LuaControllersHookAct extends Action{
	boolean sendControlles = false;
	KeyboardManager controllers = new KeyboardManager();
	LuaValue luaControllers = CoerceJavaToLua.coerce(controllers);
	
	LuaControllersHookAct(BasicObjectClass ActionOwner, LuaValue tempLuaValue) {
		super(ActionOwner, "LUACONTROLLERS");
		sendControlles = tempLuaValue.get("SendControlles").toboolean();
	}

	public void updateAction(BasicObjectClass Object) {
		if(this.sendControlles){
			LuaTable tt = new LuaTable();
			tt.set(LuaValue.valueOf("Name"), LuaValue.valueOf("CONTROLLERS"));
			tt.set(LuaValue.valueOf("Arg"), luaControllers);
			Object.callUpdateHook(tt, super.systemIdent);
		} 
		
		if(!this.sendControlles){
			if(Mouse.isButtonDown(0))
				Object.callUpdateHook("LEFT_BUTTON", super.systemIdent);
			if(Mouse.isButtonDown(1))
				Object.callUpdateHook("RIGHT_BUTTON", super.systemIdent);
			
			if(Keyboard.isKeyDown(Keyboard.KEY_A))
				Object.callUpdateHook("KEY_A", super.systemIdent);
			if(Keyboard.isKeyDown(Keyboard.KEY_W))
				Object.callUpdateHook("KEY_W", super.systemIdent);
			if(Keyboard.isKeyDown(Keyboard.KEY_D))
				Object.callUpdateHook("KEY_D", super.systemIdent);
			if(Keyboard.isKeyDown(Keyboard.KEY_S))
				Object.callUpdateHook("KEY_S", super.systemIdent);
		}
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}
}
