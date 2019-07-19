package FlatWorld;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class MouseArrowClass {
	private static ArrayList<BasicObjectClass> pickedObjects = new ArrayList<BasicObjectClass>();
	private static int pickedObjectTypeID = -1;
	private static ContainerCell throwerContainer = null;
	private static ContainerCell catcherContainer = null;
	
	static FloatBuffer dbProj  = BufferUtils.createFloatBuffer(16);
	static IntBuffer   dbPort  = BufferUtils.createIntBuffer(16);
	static FloatBuffer dbModel = BufferUtils.createFloatBuffer(16);
	static FloatBuffer obj_pos = BufferUtils.createFloatBuffer(4);
	static FloatBuffer win_pos = BufferUtils.createFloatBuffer(6);
	
	static float ArrowWorldCoordX, ArrowWorldCoordY;
	static byte colorUnderArrowR, colorUnderArrowG, colorUnderArrowB;

	public static void addObject(BasicObjectClass object) {
		pickedObjectTypeID = object.ObjectTypeID;
		pickedObjects.add(object);
	}

	public static ArrayList<BasicObjectClass> getObject() {
		return pickedObjects;
	}

	public static void addContainer(ContainerCell container) {
		if (throwerContainer == null)
			throwerContainer = container;
		else {
			catcherContainer = container;
		}
	}

	public static void updateArrow() {
		MouseArrowClass.updateArrowCoords();
		if (throwerContainer != null)
			throwerContainer.rendRedContur();
		if (catcherContainer != null)
			catcherContainer.rendRedContur();
		MouseArrowClass.rend();
		
		if(catcherContainer != null){
			if (catcherContainer.PosGlobalX  								> MouseArrowClass.ArrowWorldCoordX ||
				catcherContainer.PosGlobalX + FlatWorld.StandardQuadHeight  < MouseArrowClass.ArrowWorldCoordX ||
				catcherContainer.PosGlobalY						            > MouseArrowClass.ArrowWorldCoordY ||
				catcherContainer.PosGlobalY + FlatWorld.StandardQuadWidth   < MouseArrowClass.ArrowWorldCoordY)
			{
				catcherContainer = null;
			}
		}

		if (!Mouse.isButtonDown(0)) {
			if (throwerContainer != null && catcherContainer != null) {
				int size = pickedObjects.size();
				if (catcherContainer.pickedObjectTypeID == -1 || pickedObjectTypeID == catcherContainer.pickedObjectTypeID) {
					for (int i = 0; i < size; i++) {
						if(catcherContainer.addObject(pickedObjects.get(pickedObjects.size()-1)) == false){
							backContainers();
							break;
						}
						else 
							pickedObjects.remove(pickedObjects.size()-1);
					}
					pickedObjectTypeID = -1;
					throwerContainer = null;
					catcherContainer = null;
				} else {
					for (int i = 0; i < size; i++) {
						throwerContainer.addObject(pickedObjects.get(pickedObjects.size()-1));
						pickedObjects.remove(pickedObjects.size()-1);
					}
					pickedObjectTypeID = -1;
					throwerContainer = null;
					catcherContainer = null;
				}
			}
			
			if(catcherContainer == null && throwerContainer != null)
			{
				float fObjPosX = MapsManager.getPlayerPosX()+0.5f;
				float fObjPosY = MapsManager.getPlayerPosY()+0.5f;
				float sObjPosX = MouseArrowClass.ArrowWorldCoordX;
				float sObjPosY = MouseArrowClass.ArrowWorldCoordY;
				
				double distX = (fObjPosX - sObjPosX) * (fObjPosX - sObjPosX);
				double distY = (fObjPosY - sObjPosY) * (fObjPosY - sObjPosY);
				double finalDist = Math.sqrt((distX + distY));
				
				if(finalDist <= 3.0d)
				{
					int size = pickedObjects.size();
					for(int i = 0; i < size; i++){
						BasicObjectClass tempObject = null;
						tempObject = pickedObjects.get(pickedObjects.size()-1);
						tempObject.PosGlobalX = sObjPosX-0.5f;
						tempObject.PosGlobalY = sObjPosY;
						tempObject.PosGlobalZ = -25.0f;

						pickedObjects.remove(pickedObjects.size()-1);
						MapsManager.addObject(tempObject);
					}
					pickedObjectTypeID = -1;
					throwerContainer = null;
				} else {
					int size = pickedObjects.size();
					for (int i = 0; i < size; i++) {
						throwerContainer.addObject(pickedObjects.get(pickedObjects.size()-1));
						pickedObjects.remove(pickedObjects.size()-1);
					}
					pickedObjectTypeID = -1;
					throwerContainer = null;
				}
			}
		} 
	}
	
	private static void backContainers(){
		int size = pickedObjects.size();
		
		for (int i = 0; i < size; i++) {
			throwerContainer.addObject(pickedObjects.get(pickedObjects.size()-1));
			pickedObjects.remove(pickedObjects.size()-1);
		}
		pickedObjectTypeID = -1;
		throwerContainer = null;
		catcherContainer = null;
	}
	
	private static void rend(){
		if(throwerContainer != null && pickedObjectTypeID != -1)
		{	
			if(catcherContainer == null){
				float fObjPosX = MapsManager.getPlayerPosX()+0.5f;
				float fObjPosY = MapsManager.getPlayerPosY()+0.5f;
				float sObjPosX = MouseArrowClass.ArrowWorldCoordX;
				float sObjPosY = MouseArrowClass.ArrowWorldCoordY;
				
				double distX = (fObjPosX - sObjPosX) * (fObjPosX - sObjPosX);
				double distY = (fObjPosY - sObjPosY) * (fObjPosY - sObjPosY);
				double finalDist = Math.sqrt((distX + distY));
				
				if(finalDist <= 3.0d)
				{
					FlatWorld.StaticObjectsBase.rendObject(ContourTemplateClass.childrenBase.getChild("GCo"),
							MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, -24.99f, FlatWorld.StandardQuad);
				} else {
					FlatWorld.StaticObjectsBase.rendObject(ContourTemplateClass.childrenBase.getChild("RCo"),
							MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, -24.99f, FlatWorld.StandardQuad);
				}
				
			} else {
				FlatWorld.StaticObjectsBase.rendObject(ContainerCell.ObjectTypeID,
						MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, -24.99f, FlatWorld.StandardQuad);
				
				FlatWorld.StaticObjectsBase.rendObject(ContourClass.ObjectTypeID,
						MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, -24.99f, FlatWorld.StandardQuad);
			}
			
			MouseArrowClass.pickedObjects.get(0).rendObject(MouseArrowClass.ArrowWorldCoordX+0.2f, MouseArrowClass.ArrowWorldCoordY+0.2f, -24.99f, FlatWorld.IconQuad);
			
			//FlatWorld.StaticObjectsBase.rendObject(pickedObjectTypeID,
			//		MouseArrowClass.ArrowWorldCoordX+0.2f, MouseArrowClass.ArrowWorldCoordY+0.2f, -24.99f, FlatWorld.IconQuad);
			
			if(pickedObjects.size() > 1)
			{
				TextFieldClass.rendText(String.valueOf(pickedObjects.size()),
						MouseArrowClass.ArrowWorldCoordX-0.23f, MouseArrowClass.ArrowWorldCoordY+0.04f, -24.99f, FlatWorld.InventoryCounterQuad, 0.19f);
			}
		}
	}

	public static void updateArrowCoords(){
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, dbProj);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, dbModel);
		GL11.glGetInteger(GL11.GL_VIEWPORT, dbPort);
		
		int clickX = Mouse.getX();
		int clickY = Mouse.getY();
		FloatBuffer z = BufferUtils.createFloatBuffer(3);
		GL11.glReadPixels(clickX, clickY, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, z);
		
		GLU.gluUnProject(clickX, clickY, z.get(0), dbModel, dbProj, dbPort, obj_pos);
		ArrowWorldCoordX = obj_pos.get(0);
		ArrowWorldCoordY = obj_pos.get(1);
	}
}
