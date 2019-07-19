package FlatWorld;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class ButtonWindow extends WindowElement {
	
	public ButtonWindow(LuaValue luaScript, LuaValue luaMainFile, WindowElement ownerWnd){
		super(luaScript, luaMainFile, ownerWnd);
	}
	
	public int update(BasicObjectClass Owner) {
		super.update(Owner);
		LuaTable msg = LuaValue.listOf(null);
		if (super.wndCurrentPos.x   							   < MouseArrowClass.ArrowWorldCoordX &&
			super.wndCurrentPos.x + super.wndCurrentWidthHeight.x  > MouseArrowClass.ArrowWorldCoordX &&
			super.wndCurrentPos.y 						           < MouseArrowClass.ArrowWorldCoordY &&
			super.wndCurrentPos.y + super.wndCurrentWidthHeight.y  > MouseArrowClass.ArrowWorldCoordY) 
		{
			msg.set("ArrowAims", LuaValue.TRUE);
			if(FlatWorld.globalKeyLocker.isMouseButtonDown(0, true) == KeyboardManager.MOUSE_RELEASED){
				msg.set("ArrowReleased", LuaValue.TRUE);
			}
			
		}
		
		LuaTable wndInfo = LuaValue.tableOf(null);
		wndInfo.set("Name", LuaValue.valueOf(super.Name));
		msg.set("WndInf", wndInfo);		
		
		super.updateHook.call(Console.GlobalConsole.luaConsole, msg);
		return 0;
	}
	
	public void rend(BasicObjectClass Owner) {	

		
		super.rendQuad(super.wndCurrentPos, super.wndCurrentWidthHeight, Owner); 
		super.rend(Owner); // Сначало рендерим все рендерим
		
		//Тут рендерим само окно, перекрывая все низшие
	}
}
