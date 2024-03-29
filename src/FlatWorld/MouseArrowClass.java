package FlatWorld;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;

public class MouseArrowClass {
	private static ArrayList<BasicObjectClass> pickedObjects = new ArrayList<BasicObjectClass>();
	private static int pickedObjectTypeID = -1;
	private static ContainerCell throwerContainer = null;
	private static ContainerCell catcherContainer = null;
	static AnimationClass greenContourTex = new AnimationClass(0, "greenContour").addFrame(new ImageClass("data/GUI/GreenContour.png"), 300);
	private static ContourClass greenContour = (ContourClass) RandomizeTool.setAnimation(new ContourClass(), greenContourTex);
	
	static AnimationClass redContourTex = new AnimationClass(0, "greenContour").addFrame(new ImageClass("data/GUI/RedContour.png"), 300);
	private static ContourClass redContour = (ContourClass) RandomizeTool.setAnimation(new ContourClass(), redContourTex);
	
	static FloatBuffer dbProj  = BufferUtils.createFloatBuffer(16);
	static IntBuffer   dbPort  = BufferUtils.createIntBuffer(16);
	static FloatBuffer dbModel = BufferUtils.createFloatBuffer(16);
	static FloatBuffer obj_pos = BufferUtils.createFloatBuffer(4);
	static FloatBuffer win_pos = BufferUtils.createFloatBuffer(6);
	
	static float depth;
	static float ArrowWorldCoordX, ArrowWorldCoordY;
	static int mouseWheel;

	public static void addObject(BasicObjectClass object) {
		pickedObjectTypeID = object.ObjectTypeID;
		object.zeroObject();
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
				catcherContainer.PosGlobalX + QuadClass.standardQuad.height  < MouseArrowClass.ArrowWorldCoordX ||
				catcherContainer.PosGlobalY						            > MouseArrowClass.ArrowWorldCoordY ||
				catcherContainer.PosGlobalY + QuadClass.standardQuad.width   < MouseArrowClass.ArrowWorldCoordY)
			{
				catcherContainer = null;
			}
		}
		
		if (throwerContainer != null) {
			if(mouseWheel < 0){
				int size = throwerContainer.ObjectsArray.size();
				if(size > 0){
					MouseArrowClass.addObject(throwerContainer.ObjectsArray.get(throwerContainer.ObjectsArray.size() - 1));
					throwerContainer.ObjectsArray.remove(throwerContainer.ObjectsArray.size() - 1);
				}
				if (throwerContainer.ObjectsArray.size() == 0)
					throwerContainer.pickedObjectTypeID = -1;
			}
			if(mouseWheel > 0){
				int size = pickedObjects.size();
				if(size > 1){
					throwerContainer.addObject(pickedObjects.get(pickedObjects.size()-1));
					pickedObjects.remove(pickedObjects.size()-1);
				}
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
				//		tempObject.PosGlobalZ = -25.0f;

						pickedObjects.get(pickedObjects.size()-1).Modifiers.pOffersList.addCommand(Commands.FreeFromOwners);
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
					greenContour.rendObject(MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, QuadClass.standardQuad);
				} else {
					redContour.rendObject(MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, QuadClass.standardQuad);
				}
				
			} else {
				FlatWorld.StaticObjectsBase.rendObject(ContainerCell.ObjectTypeID,
						MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, QuadClass.standardQuad);
				
				FlatWorld.StaticObjectsBase.rendObject(ContourClass.ObjectTypeID,
						MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, QuadClass.standardQuad);
			}
			
			MouseArrowClass.pickedObjects.get(0).rendObject(MouseArrowClass.ArrowWorldCoordX+0.2f, MouseArrowClass.ArrowWorldCoordY+0.2f, QuadClass.iconQuad, 
					MouseArrowClass.pickedObjects.get(0).Animation.getCurrentImage());
			
			//FlatWorld.StaticObjectsBase.rendObject(pickedObjectTypeID,
			//		MouseArrowClass.ArrowWorldCoordX+0.2f, MouseArrowClass.ArrowWorldCoordY+0.2f, -24.99f, FlatWorld.IconQuad);
			
			if(pickedObjects.size() > 1)
			{
				TextFieldClass.rendText(String.valueOf(pickedObjects.size()),
						MouseArrowClass.ArrowWorldCoordX-0.23f, MouseArrowClass.ArrowWorldCoordY+0.04f, -24.99f, QuadClass.inventoryCounterQuad, 0.19f);
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
		GL11.glReadPixels(1, 1, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, z);
		
		GLU.gluUnProject(clickX, clickY, 0.9981501f, dbModel, dbProj, dbPort, obj_pos);
		depth = 0.9981501f;
	
		ArrowWorldCoordX = obj_pos.get(0);
		ArrowWorldCoordY = obj_pos.get(1);
		mouseWheel = Mouse.getDWheel();
	}
	
	public static Vector2f convertToGameSpace(int pointX, int pointY){
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, dbProj);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, dbModel);
		GL11.glGetInteger(GL11.GL_VIEWPORT, dbPort);
		
		FloatBuffer z = BufferUtils.createFloatBuffer(3);
		GL11.glReadPixels(1, 1, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, z);
		
		GLU.gluUnProject(pointX, pointY, 0.9981501f, dbModel, dbProj, dbPort, obj_pos);
		
		return new Vector2f(obj_pos.get(0), obj_pos.get(1));
	}
}
