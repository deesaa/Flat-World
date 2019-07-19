package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

enum LightingMode{Origin, Object};

public class LightingSystem extends Action{
	LightObject lightObject;
	ArrayList<ShadowEl> shadowsArray;
	float lightingRadius;
	
	LightingSystem(BasicObjectClass Object){
		super(Object);
		Object.Modifiers.pointerToLightingSystem = this;
		shadowsArray = new ArrayList<ShadowEl>();
	}
	
	LightingSystem(BasicObjectClass Object, float lightingRadius, LightObject lightObject){
		super(Object);
		Object.Modifiers.pointerToLightingSystem = this;
		this.lightObject = lightObject;
		this.lightingRadius = lightingRadius;
	}
	
	public void updateAction(BasicObjectClass Object) {
		if(shadowsArray != null){
			this.objectModeUpdate(Object);
			MapsManager.MapsArray.get(Object.OwnedMapID).lightingMap.registerShadow(Object);
		}
		
		
		if(lightObject != null){
			MapsManager.MapsArray.get(Object.OwnedMapID).lightingMap.addLight(lightObject, Object);
		}
			
	}
	
	private void objectModeUpdate(BasicObjectClass Object){
		ArrayList<ChunkClass> VisibleChunksArray = MapsManager.MapsArray.get(Object.OwnedMapID).VisibleChunksArray;
		this.shadowsArray.clear();
		for(int i = 0; i < VisibleChunksArray.size(); i++){
			ChunkClass currentChunk = VisibleChunksArray.get(i);
			for(int i2 = 0; i2 < currentChunk.ObjectsArray.size(); i2++){
				BasicObjectClass currentObject = currentChunk.ObjectsArray.get(i2);
				
				
				if(currentObject.Modifiers.pointerToLightingSystem != null && currentObject.Modifiers.pointerToLightingSystem.lightObject != null){
					double finalDist = FlatMath.objectDist(Object, currentObject);
					if(finalDist < currentObject.Modifiers.pointerToLightingSystem.lightingRadius){
						float tempDirX = Object.PosGlobalX - currentObject.PosGlobalX;
						float tempDirY = Object.PosGlobalY - currentObject.PosGlobalY;
						this.shadowsArray.add(new ShadowEl(tempDirX, tempDirY, (float)finalDist, currentObject.Modifiers.pointerToLightingSystem));
					}	
				}	
			}	
		}	
	}	
	
	public void rendAction(BasicObjectClass Object) {
	}	
	
	public void objectModeRend(BasicObjectClass Object){
		for(int i = 0; i < shadowsArray.size(); i++){
			float vecToObjectX = shadowsArray.get(i).dirX;
			float vecToObjectY = shadowsArray.get(i).dirY;
			float VecViewDirY = 1.0f;
			float VecViewDirX = 0.0f;
			double angle = Math.toDegrees(Math.atan2(vecToObjectX*VecViewDirY - VecViewDirX*vecToObjectY, vecToObjectX*VecViewDirX + vecToObjectY*VecViewDirY));
			GL11.glTranslatef(Object.PosGlobalX+QuadClass.standardQuad.width*0.5f, Object.PosGlobalY, Object.PosGlobalZ);
			GL11.glRotated(-angle, 0, 0, 1);
			if(Math.abs(angle) > 90)
				GL11.glRotated(-180, 0, 1, 0);
			GL11.glTranslatef(-QuadClass.standardQuad.width*0.5f, -0.0f, 0);
			
			float curr = MapsManager.MapsArray.get(Object.OwnedMapID).WeatherSystem.SunLight.x;
			float max = MapsManager.MapsArray.get(Object.OwnedMapID).WeatherSystem.SunLightMax.x;
			
			curr = (1/max)*curr;
			float alpha = (1/shadowsArray.get(i).origin.lightingRadius)*shadowsArray.get(i).distToOrigin;
			
			//float TheCostil = 0.3f; // йняршкэ
			//GL11.glColor4f(0, 0, 0, ((1.0f+TheCostil)-(curr+alpha))/shadowsArray.size());    //оюбепед аюи йняршкэ
			GL11.glColor4f(0, 0, 0, (1.0f-(curr+alpha))/(shadowsArray.size()*0.8f));

			new QuadClass(QuadClass.standardQuad.width, QuadClass.standardQuad.height*1.25f, false).rend(Object.Animation.getCurrentImage());
			GL11.glLoadIdentity();
		}
	}	

	public void zeroAction(BasicObjectClass basicObjectClass) {
		if(lightObject != null)
			lightObject.deleteMessage();
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}
	
	public class ShadowEl {
		float dirX, dirY;
		float distToOrigin;
		LightingSystem origin;
		
		public ShadowEl(float dirX, float dirY, float distToOrigin, LightingSystem origin) {
			this.dirX = dirX;
			this.dirY = dirY;
			this.distToOrigin = distToOrigin;
			this.origin = origin;
		}
	}
	
	public void setType(){
		System.out.println(322);
	}
}
