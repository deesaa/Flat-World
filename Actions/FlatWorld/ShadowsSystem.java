package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class ShadowsSystem extends Action{
	ArrayList<ShadowEl> shadowsArray;
	
	ShadowsSystem(BasicObjectClass Object) {
		super(Object, "SHAD");
		Object.Modifiers.pShadowsSystem = this;
		shadowsArray = new ArrayList<ShadowEl>();
	}

	public void updateAction(BasicObjectClass Object) {
		this.objectModeUpdate(Object);
		MapsManager.MapsArray.get(Object.OwnedMapID).lightingMap.registerShadow(Object);
	}
	
	private void objectModeUpdate(BasicObjectClass Object){
		ArrayList<ChunkClass> VisibleChunksArray = MapsManager.MapsArray.get(Object.OwnedMapID).VisibleChunksArray;
		this.shadowsArray.clear();
		for(int i = 0; i < VisibleChunksArray.size(); i++){
			ChunkClass currentChunk = VisibleChunksArray.get(i);
			for(int i2 = 0; i2 < currentChunk.ObjectsArray.size(); i2++){
				BasicObjectClass currentObject = currentChunk.ObjectsArray.get(i2);
				
				
				if(currentObject.Modifiers.pLightingSystem != null && currentObject.Modifiers.pLightingSystem.lightObject != null){
					double finalDist = FlatMath.objectDist(Object, currentObject);
					if(finalDist < currentObject.Modifiers.pLightingSystem.lightObject.lightingDistance){
						float tempDirX = Object.PosGlobalX - currentObject.PosGlobalX;
						float tempDirY = Object.PosGlobalY - currentObject.PosGlobalY;
						this.shadowsArray.add(new ShadowEl(tempDirX, tempDirY, (float)finalDist, currentObject.Modifiers.pLightingSystem));
					}	
				}	
			}	
		}	
	}
	
	public void objectModeRend(BasicObjectClass Object){
		for(int i = 0; i < shadowsArray.size(); i++){
			float vecToObjectX = shadowsArray.get(i).dirX;
			float vecToObjectY = shadowsArray.get(i).dirY;
			float VecViewDirY = 1.0f;
			float VecViewDirX = 0.0f;
			double angle = Math.toDegrees(Math.atan2(vecToObjectX*VecViewDirY - VecViewDirX*vecToObjectY, vecToObjectX*VecViewDirX + vecToObjectY*VecViewDirY));
			GL11.glTranslatef(Object.PosGlobalX+QuadClass.standardQuad.width*0.5f, Object.PosGlobalY, Object.layerDepth);
			GL11.glRotated(-angle, 0, 0, 1);
			if(Math.abs(angle) > 90)
				GL11.glRotated(-180, 0, 1, 0);
			GL11.glTranslatef(-QuadClass.standardQuad.width*0.5f, -0.0f, 0);
			
			float curr = MapsManager.MapsArray.get(Object.OwnedMapID).WeatherSystem.SunLight.x;
			float max = MapsManager.MapsArray.get(Object.OwnedMapID).WeatherSystem.SunLightMax.x;
			
			curr = (1/max)*curr;
			float alpha = (1/shadowsArray.get(i).origin.lightObject.lightingDistance)*shadowsArray.get(i).distToOrigin;
			
			//float TheCostil = 0.3f; // йняршкэ
			//GL11.glColor4f(0, 0, 0, ((1.0f+TheCostil)-(curr+alpha))/shadowsArray.size());    //оюбепед аюи йняршкэ
			GL11.glColor4f(0, 0, 0, (1.0f-(curr+alpha))/(shadowsArray.size()*0.8f));
			
			int b = (int) (127*(1.0f-(curr+alpha))/(shadowsArray.size()*0.8f));
			if(b > 127)
				b = 127;
			
			ColorRGBAClass shadow = new ColorRGBAClass((byte)0, (byte) 0, (byte) 0, (byte) b, false);
			new QuadClass(QuadClass.standardQuad.width, QuadClass.standardQuad.height*1.25f, false).rend(Object.Animation.getCurrentImage(), shadow);
			GL11.glLoadIdentity();
		}
	}	

	public void rendAction(BasicObjectClass Object) {
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
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
}
