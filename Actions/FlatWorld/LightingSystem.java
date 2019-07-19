package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.GL11;

enum LightingMode{Origin, Object};

public class LightingSystem implements Action{
	LightingMode mode;
	ArrayList<ShadowEl> shadowsArray;
	int currentLightID;
	float lightingRadius;
	
	LightingSystem(BasicObjectClass Object){
		Object.Modifiers.pointerToLightingSystem = this;
		this.mode = LightingMode.Object;
		shadowsArray = new ArrayList<ShadowEl>();
	}
	
	LightingSystem(BasicObjectClass Object, float lightingRadius){
		Object.Modifiers.pointerToLightingSystem = this;
		this.mode = LightingMode.Origin;
		this.lightingRadius = lightingRadius;
	}
	
	public void updateAction(BasicObjectClass Object) {
		if(mode == LightingMode.Object)
			this.objectModeUpdate(Object);
		
		if(mode == LightingMode.Origin)
			MapsManager.MapsArray.get(Object.OwnedMapID).lightingMap.addLMEl(Object);
			
	}
	
	private void objectModeUpdate(BasicObjectClass Object){
		ArrayList<ChunkClass> VisibleChunksArray = MapsManager.MapsArray.get(Object.OwnedMapID).VisibleChunksArray;
		this.shadowsArray.clear();
		for(int i = 0; i < VisibleChunksArray.size(); i++){
			ChunkClass currentChunk = VisibleChunksArray.get(i);
			for(int i2 = 0; i2 < currentChunk.ObjectsArray.size(); i2++){
				BasicObjectClass currentObject = currentChunk.ObjectsArray.get(i2);
				
				
				if(currentObject.Modifiers.pointerToLightingSystem != null && currentObject.Modifiers.pointerToLightingSystem.mode == LightingMode.Origin){
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
		if(mode == LightingMode.Object)
			this.objectModeRend(Object);
	}

	private void objectModeRend(BasicObjectClass Object){
		for(int i = 0; i < shadowsArray.size(); i++){
			GL11.glDisable(GL11.GL_LIGHTING);
			float vecToObjectX = shadowsArray.get(i).dirX;
			float vecToObjectY = shadowsArray.get(i).dirY;
			float VecViewDirY = 1.0f;
			float VecViewDirX = 0.0f;
			double angle = Math.toDegrees(Math.atan2(vecToObjectX*VecViewDirY - VecViewDirX*vecToObjectY, vecToObjectX*VecViewDirX + vecToObjectY*VecViewDirY));
			Object.Animations.Animations.get(Object.Animations.pickedAnimation).getCurrentFrame().bind();
			GL11.glTranslatef(Object.PosGlobalX+QuadClass.standardQuad.width*0.5f, Object.PosGlobalY, Object.PosGlobalZ);
			GL11.glRotated(-angle, 0, 0, 1);
			if(Math.abs(angle) > 90)
				GL11.glRotated(-180, 0, 1, 0);
			GL11.glTranslatef(-QuadClass.standardQuad.width*0.5f, -0.0f, 0);
			byte alpha = (byte) (127-(127/shadowsArray.get(i).origin.lightingRadius)*shadowsArray.get(i).distToOrigin);
			GL11.glColor4b((byte) 0, (byte) 0, (byte) 0, (byte) (alpha-0));
			new QuadClass(QuadClass.standardQuad.width, QuadClass.standardQuad.height*1.25f, false).rend();
			GL11.glLoadIdentity();
			GL11.glEnable(GL11.GL_LIGHTING);
		}
	}
	
	public void originModeRend(BasicObjectClass Object, int lightID) {
		System.out.println(lightID);
		currentLightID = lightID;
		 GL11.glLight(lightID, GL11.GL_POSITION,FlatWorld.floatBuffer(Object.PosGlobalX, Object.PosGlobalY, Object.PosGlobalZ+2, 1));
         GL11.glLight(lightID, GL11.GL_DIFFUSE,FlatWorld.floatBuffer(1.0f, 1.0f, 1.0f, 0));
         GL11.glLight(lightID, GL11.GL_SPECULAR,FlatWorld.floatBuffer(1.0f, 1.0f, 1.0f, 0));
         GL11.glLight(lightID, GL11.GL_AMBIENT,FlatWorld.floatBuffer(1.0f, 1.0f, 1.0f, 0));
         
         float kQ;
         float kL;
         float kC;
         float radius;
         float att;

         att = 2;
         radius = 4;
         kQ = att / (3* radius * radius);
         kL = att / (3 * radius);
         kC = att / 3; 

         GL11.glLightf(lightID, GL11.GL_CONSTANT_ATTENUATION, kC);
         GL11.glLightf(lightID, GL11.GL_LINEAR_ATTENUATION, kL);
         GL11.glLightf(lightID, GL11.GL_QUADRATIC_ATTENUATION, kQ);
         
         GL11.glEnable(lightID);
         
		/*GL11.glTranslatef(Object.PosGlobalX+QuadClass.standardQuad.width*0.5f, Object.PosGlobalY+QuadClass.standardQuad.height*0.5f, Object.PosGlobalZ);
		GL11.glBegin( GL11.GL_TRIANGLE_FAN );
		GL11.glColor4b((byte) 60, (byte) 30,  (byte) 0, (byte)35);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f); 
		for( int i = 0; i <= 50; i++ ) {
			float a = (float)i / 50.0f * 3.1415f * 2.0f;
			GL11.glColor4b((byte) 0, (byte) 0,  (byte) 0, (byte)0);
			GL11.glVertex3f((float)Math.cos(a)*7.0f,(float)Math.sin(a)*7.0f, 0.0f);
		}
		GL11.glEnd();
		GL11.glLoadIdentity();*/
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		GL11.glDisable(currentLightID);
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
