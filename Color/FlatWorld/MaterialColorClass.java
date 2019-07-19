package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;

public class MaterialColorClass extends UniteColorClass{
	ColorRGBAClass Diffuse;
	ColorRGBAClass Specular;
	ColorRGBAClass Ambient;
	
	public static final MaterialColorClass Red = new MaterialColorClass(ColorRGBAClass.Red, null, null);
	
	public MaterialColorClass(ColorRGBAClass diff, ColorRGBAClass spec, ColorRGBAClass amb) {
		super(true);
		this.Diffuse = diff;
		this.Specular = spec;
		this.Ambient = amb;
	}
	
    MaterialColorClass(LuaValue luaValue){
		super(luaValue);
		
		LuaValue diff = luaValue.get("Diffuse");
		if(!diff.isnil())
			Diffuse = new ColorRGBAClass(diff);
		
		LuaValue spec = luaValue.get("Specular");
		if(!spec.isnil())
			Diffuse = new ColorRGBAClass(spec);
		
		LuaValue amb = luaValue.get("Ambient");
		if(!amb.isnil())
			Diffuse = new ColorRGBAClass(amb);
	}
	
	public void setColor(){
		super.setColor();
		
		if(Diffuse != null){
			GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_DIFFUSE);
			Diffuse.setColor();
		}
		if(Specular != null){
			GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_SPECULAR);
			Specular.setColor();
		}
		if(Ambient != null){
			GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT);
			Ambient.setColor();
		}
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
	}
}
