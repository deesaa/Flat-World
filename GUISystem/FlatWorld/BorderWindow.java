package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

public class BorderWindow extends WindowElement{
	
	public BorderWindow(LuaValue luaScript, LuaValue luaMainFile, WindowElement ownerWnd){
		super(luaScript, luaMainFile, ownerWnd);
		super.rendMode = GL11.GL_LINE_LOOP;
	}

	public void rend(BasicObjectClass Owner) {	
		Vector4f pos = super.getFinalWorldPos(Owner);
		float worldXPos = pos.x, worldYPos = pos.y;
		float worldWidth = pos.z, worldHeight = pos.w;

		super.rendQuad(new Vector2f(worldXPos, worldYPos), new Vector2f(worldWidth, worldHeight), Owner); // Сначало рендерим все рендерим
		super.rend(Owner); // Сначало рендерим все рендерим
		//Тут рендерим само окно, перекрывая все низшие
	}
}
