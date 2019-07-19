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
		
		if (catcherContainer != null){
			catcherContainer.rendRedContur();
			
			if (catcherContainer.buttonColorR != FlatWorld.colorUnderArrow.get(0) ||
				catcherContainer.buttonColorG != FlatWorld.colorUnderArrow.get(1) ||
				catcherContainer.buttonColorB != FlatWorld.colorUnderArrow.get(2))
			{
				catcherContainer = null;
			}
		}

		if (!Mouse.isButtonDown(0)) {
			if (throwerContainer != null && catcherContainer != null) {
				int size = pickedObjects.size();
				if (catcherContainer.pickedObjectTypeID == -1 || pickedObjectTypeID == catcherContainer.pickedObjectTypeID) {
					for (int i = 0; i < size; i++) {
						catcherContainer.addObject(pickedObjects.get(pickedObjects.size()-1));
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
				int size = pickedObjects.size();
				for (int i = 0; i < size; i++) {
					throwerContainer.addObject(pickedObjects.get(pickedObjects.size()-1));
					pickedObjects.remove(pickedObjects.size()-1);
				}
				pickedObjectTypeID = -1;
				throwerContainer = null;
			}
		} 
		if(throwerContainer != null && pickedObjectTypeID != -1)
		{
			throwerContainer.rendObject(MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, -24.0f, FlatWorld.StandardQuad, false);
			
	
			if(catcherContainer == null){
				FlatWorld.StaticObjectsBase.rendObject(GreenContourClass.ObjectTypeID,
						MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, -24.0f, FlatWorld.StandardQuad, false);
			} else {
				FlatWorld.StaticObjectsBase.rendObject(ContourClass.ObjectTypeID,
						MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, -24.0f, FlatWorld.StandardQuad, false);
			}
			
			FlatWorld.StaticObjectsBase.rendObject(pickedObjectTypeID,
					MouseArrowClass.ArrowWorldCoordX+0.2f, MouseArrowClass.ArrowWorldCoordY+0.2f, -24.0f, FlatWorld.IconQuad, false);
			
			if(pickedObjects.size() > 1)
			{
				TextFieldClass.rendText(String.valueOf(pickedObjects.size()),
						MouseArrowClass.ArrowWorldCoordX-0.23f, MouseArrowClass.ArrowWorldCoordY+0.04f, -24.0f, FlatWorld.InventoryCounterQuad, 0.19f);
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
