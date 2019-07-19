package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.lwjgl.util.vector.Vector4f;

public class TextWindow extends WindowElement {
	TextRenderModule text;
	
	public TextWindow(LuaValue luaScript, LuaValue luaMainFile, WindowElement ownerWnd){
		super(luaScript, luaMainFile, ownerWnd);
		text = new TextRenderModule(luaScript.get("Text"));
	}
	
	public int update(BasicObjectClass Owner) {
		super.update(Owner);
		return 0;
	}
	
	public void rend(BasicObjectClass Owner) {	
		text.rend(super.wndCurrentPos.x, super.wndCurrentPos.y, super.layerDepth);
		super.rend(Owner); // Сначало рендерим все рендерим
		
		//Тут рендерим само окно, перекрывая все низшие
	}
}
