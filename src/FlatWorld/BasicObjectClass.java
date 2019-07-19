package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

enum ObjectTypes{Cell, Object, Player, Mob};

public class BasicObjectClass {
	float PosGlobalX, PosGlobalY, PosGlobalZ;
	int ObjectID;
	int OwnedChunkID, OwnedMapID;
	ObjectTypes ObjectType;
	float moveSpeed;
	
	public TexturesClass Textures;
	boolean isAlphaBlend = false;
	
	public ArrayList<Action> ActionsArray = new ArrayList<Action>();
	
	public BasicObjectClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, 
			int OwnedChunkID, int OwnedMapID, ObjectTypes ObjectType, float moveSpeed, boolean isAlphaBlend, int ObjectID) {
		this.PosGlobalX   = PosGlobalX;
		this.PosGlobalY   = PosGlobalY;
		this.PosGlobalZ   = PosGlobalZ;
		this.OwnedChunkID = OwnedChunkID;
		this.OwnedMapID   = OwnedMapID;
		this.ObjectType   = ObjectType;
		this.moveSpeed    = moveSpeed;
		this.isAlphaBlend = isAlphaBlend;
		this.ObjectID     = ObjectID;
	}
	
	public void move(float PosX, float PosY, float PosZ) {
		this.PosGlobalX += PosX * FlatWorld.delta;
		this.PosGlobalY += PosY * FlatWorld.delta;
		this.PosGlobalZ += PosZ * FlatWorld.delta;
		MapsManager.relocateToRelevantChunk(OwnedMapID, OwnedChunkID, this.PosGlobalX, this.PosGlobalY, this);
	}
	
	public void updateObject(){
		for(int i = 0; i < ActionsArray.size(); i++){
			ActionsArray.get(i).update(this);
		}
	}
	
	public void rendObject(){
		GL11.glTranslatef(PosGlobalX, PosGlobalY, PosGlobalZ);  
		if(isAlphaBlend == false){
			GL11.glCallList(FlatWorld.StandardQuad);
		} else {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glCallList(FlatWorld.StandardQuad);
			GL11.glDisable(GL11.GL_BLEND); 
		}
		GL11.glLoadIdentity();
	}
}
