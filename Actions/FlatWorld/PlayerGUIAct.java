package FlatWorld;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;


public class PlayerGUIAct implements Action{
	public PercentScaleModule PerHealScale;
	public TexturesClass PerHealScaleTex = null, PerHealScaleBGTex = null;
	public Vector3f PerHealScaleContourColor = null;
	
	FloatBuffer dbProj  = BufferUtils.createFloatBuffer(16);
	IntBuffer dbPort    = BufferUtils.createIntBuffer(16);
	FloatBuffer dbModel = BufferUtils.createFloatBuffer(16);
	FloatBuffer obj_pos = BufferUtils.createFloatBuffer(4);
	FloatBuffer win_pos = BufferUtils.createFloatBuffer(6);
	
	public void initHealScale(int maxHealpoints) {
		PerHealScaleTex = new TexturesClass("png", "data/GUI/PerHealScale.png");
		PerHealScaleBGTex = new TexturesClass("png", "data/GUI/PerHealScaleBG.png");
		PerHealScaleContourColor = new Vector3f(1.0f, 1.0f, 1.0f);
		PerHealScale = new PercentScaleModule(10.0f, 0.5f, maxHealpoints, PerHealScaleTex, PerHealScaleContourColor, PerHealScaleBGTex, 0.3f, 0.3f, 1.0f, 1.0f);
	}
	
	public void rendHealScale(int healpoints){
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, dbProj);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, dbModel);
		GL11.glGetInteger(GL11.GL_VIEWPORT, dbPort);
		
		float depthStandard = 0.9968301f;
		
	//	FloatBuffer z = BufferUtils.createFloatBuffer(3);
	//	GL11.glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, z);
		
		int zeroCoordX, CoordX;
		
		GLU.gluProject(0.0f, 0.0f,  -24.0f, dbModel, dbProj, dbPort, win_pos);
		zeroCoordX = (int) win_pos.get(0);
		GLU.gluProject(PerHealScale.lenght, 0.0f,  -24.0f, dbModel, dbProj, dbPort, win_pos);
		CoordX = (int) win_pos.get(0);
		
		int delta = CoordX - zeroCoordX;
		
		GLU.gluUnProject(dbPort.get(2)*0.5f-(delta*0.5f), 30.0f, depthStandard, dbModel, dbProj, dbPort, obj_pos);
		
		PerHealScale.rendScale(obj_pos.get(0), obj_pos.get(1), -24.97f, healpoints);
	}
	
	public void updateAction(BasicObjectClass Object) {
		
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void rendButtons(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}
}
