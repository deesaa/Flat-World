package FlatWorld;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

public class CameraClass {
	int width, height;
	float fovy, zFar;
	private Vector3f eyePos = new Vector3f(0.0f, 0.0f, 1.0f), eyeVec = new Vector3f(0.0f, 0.0f, 0.0f), up = new Vector3f(0.0f, 1.0f, 0.0f);
	
	public CameraClass(int width, int height, float fovy, float zFar) {
		this.width = width; this.height = height;
		this.fovy  = fovy; this.zFar = zFar;	
			
		GL11.glViewport(0, 0, width, height); // Reset The Current Viewport
		GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
		GL11.glLoadIdentity(); // Reset The Projection Matrix
		GLU.gluPerspective(fovy, ((float) width / (float) height), 0.1f, zFar); // Calculate The Aspect Ratio Of The Window
		GLU.gluLookAt(eyePos.x, eyePos.y, eyePos.z, eyeVec.x, eyeVec.y, eyeVec.z, up.x, up.y, up.z);
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
		GL11.glLoadIdentity(); // Reset The Modelview Matrix
	}
	
	public void setLook(Vector3f eyePos, Vector3f eyeVec, Vector3f up){
		if(eyePos != null)
			this.eyePos.set(eyePos);
		if(eyeVec != null)
			this.eyeVec.set(eyeVec);
		if(up != null)
			this.up.set(up);
		this.updateCamera();
	}
	
	public void updateCamera(){
		GL11.glViewport(0, 0, width, height); // Reset The Current Viewport
		GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
		GL11.glLoadIdentity(); // Reset The Projection Matrix
		GLU.gluPerspective(fovy, ((float) width / (float) height), 0.1f, zFar); // Calculate The Aspect Ratio Of The Window
		GLU.gluLookAt(eyePos.x, eyePos.y, eyePos.z, eyeVec.x, eyeVec.y, eyeVec.z, up.x, up.y, up.z);
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
		GL11.glLoadIdentity(); // Reset The Modelview Matrix
	}

	public float getPosX() {
		return eyePos.x;
	}
	public float getPosY() {
		return eyePos.y;
	}
}
