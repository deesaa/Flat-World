package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

public class LightObject {
	public static ArrayList<Integer> freeLightList = initLightList();
	BasicObjectClass OwnerObject;
	ArrayList<LightObject> includedLights = new ArrayList<LightObject>();
	boolean deleteMark = false;
	Vector4f Diffuse, Specular, Ambient;
	
	float lightingDistance;
	
	int lightID = -1;
	//Integer pToLightID = null;
	float i = 0;
	
	LightObject(BasicObjectClass OwnerObject, Vector4f Diffuse, Vector4f Specular, Vector4f Ambient){
	//	this.pToLightID = pToLightID;
		this.OwnerObject = OwnerObject;
		this.Diffuse  = Diffuse;
		this.Specular = Specular;
		this.Ambient  = Ambient;
	}
	
	public LightObject(BasicObjectClass OwnerObject, LuaValue tempLuaValue) {
		this.OwnerObject = OwnerObject;
		LuaValue lDif = tempLuaValue.get("diffuse");
		this.Diffuse = new Vector4f(lDif.get(1).tofloat(), lDif.get(2).tofloat(), lDif.get(3).tofloat(), lDif.get(4).tofloat());
		LuaValue lSpec = tempLuaValue.get("specular");
		this.Specular = new Vector4f(lSpec.get(1).tofloat(), lSpec.get(2).tofloat(), lSpec.get(3).tofloat(), lSpec.get(4).tofloat());
		LuaValue lAmb = tempLuaValue.get("ambient");
		this.Ambient = new Vector4f(lAmb.get(1).tofloat(), lAmb.get(2).tofloat(), lAmb.get(3).tofloat(), lAmb.get(4).tofloat());
		
		this.lightingDistance = tempLuaValue.get("lightingDistance").tofloat();
	}

	public void createLight(BasicObjectClass OriginObject){
		lightID = getNextLightID();
		
		GL11.glLight(lightID, GL11.GL_POSITION,FlatWorld.floatBuffer(OriginObject.PosGlobalX+QuadClass.standardQuad.width*0.5f, 
				OriginObject.PosGlobalY+QuadClass.standardQuad.height*0.5f, OriginObject.PosGlobalZ+2, 1));
        GL11.glLight(lightID, GL11.GL_DIFFUSE,FlatWorld.floatBuffer(Diffuse.x, Diffuse.y, Diffuse.z, Diffuse.w));
        GL11.glLight(lightID, GL11.GL_SPECULAR,FlatWorld.floatBuffer(Specular.x, Specular.y, Specular.z, Specular.w));
        GL11.glLight(lightID, GL11.GL_AMBIENT,FlatWorld.floatBuffer(Ambient.x, Ambient.y, Ambient.z, Ambient.w));
        
        float kQ, kL, kC, radius, att;

        att = 2;
        radius = this.lightingDistance;
        kQ = att / (3* radius * radius);
        kL = att / (3 * radius);
        kC = att / 3; 

        GL11.glLightf(lightID, GL11.GL_CONSTANT_ATTENUATION, kC);
        GL11.glLightf(lightID, GL11.GL_LINEAR_ATTENUATION, kL);
        GL11.glLightf(lightID, GL11.GL_QUADRATIC_ATTENUATION, kQ);
	}
	
	public void updatePos(BasicObjectClass OriginObject) {
		PickableModif tempPickable = OriginObject.Modifiers.pPickableModif;
		BasicObjectClass finalOrigin = null;
		if(tempPickable != null){
			finalOrigin = tempPickable.getOwner();
			if(finalOrigin != null){
				GL11.glLight(lightID, GL11.GL_POSITION,FlatWorld.floatBuffer(finalOrigin.PosGlobalX+QuadClass.standardQuad.width*0.5f, 
						finalOrigin.PosGlobalY+QuadClass.standardQuad.height*0.5f, finalOrigin.PosGlobalZ+2, 1));
				return;
			}
		}
		GL11.glLight(lightID, GL11.GL_POSITION,FlatWorld.floatBuffer(OriginObject.PosGlobalX+QuadClass.standardQuad.width*0.5f, 
				OriginObject.PosGlobalY+QuadClass.standardQuad.height*0.5f, OriginObject.PosGlobalZ+2, 1));
	}
	
	public void setOwner(BasicObjectClass Owner){
		this.OwnerObject = Owner;
	}
	
	
	public void enableLight(){
		if(lightID != -1)
			GL11.glEnable(lightID);
	}
	
	public void deleteLight(){
		if(lightID != -1){
			GL11.glDisable(lightID);
			freeLightList.add(lightID);
			lightID = -1;
		}	
	}
	
	public void deleteMessage() {
		this.deleteMark = true;
	}
	
	public static int getNextLightID(){
		if(freeLightList.size() > 0){
			int newLightID = freeLightList.get(freeLightList.size()-1);
			freeLightList.remove(freeLightList.size()-1);
			return newLightID;
		}
		return -1;
	}
	
	public static ArrayList<Integer> initLightList(){
		ArrayList<Integer> newFreeLightList = new ArrayList<Integer>();
		newFreeLightList.add(GL11.GL_LIGHT0);
		newFreeLightList.add(GL11.GL_LIGHT1);
		newFreeLightList.add(GL11.GL_LIGHT2);
		newFreeLightList.add(GL11.GL_LIGHT3);
		newFreeLightList.add(GL11.GL_LIGHT4);
		newFreeLightList.add(GL11.GL_LIGHT5);
		newFreeLightList.add(GL11.GL_LIGHT6);
		newFreeLightList.add(GL11.GL_LIGHT7);
		return newFreeLightList;
	}
}
