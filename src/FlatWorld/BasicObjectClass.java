package FlatWorld;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

enum ObjectTypes {
	Cell, Object, Player, Mob
};

public class BasicObjectClass {
	float PosGlobalX, PosGlobalY, PosGlobalZ;
	public float CollisionRightX = 0.5f, CollisionLeftX = 0.5f, CollisionUpY = 0.5f, CollisionDownY = 0.5f;
	int ObjectID;
	int ObjectTypeID;
	int OwnedChunkID, OwnedMapID;
	ObjectTypes ObjectType;
	float moveSpeed;

	public byte buttonColorR, buttonColorG, buttonColorB;

	public TexturesClass Textures;

	ObjectModifiers Modifiers = new ObjectModifiers();
	public ArrayList<Action> ActionsArray = new ArrayList<Action>();

	public BasicObjectClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID,
			ObjectTypes ObjectType, float moveSpeed, boolean isAlphaBlend, int ObjectID, int ObjectTypeID, boolean isSolid, boolean isPickable) {
		this.PosGlobalX = PosGlobalX;
		this.PosGlobalY = PosGlobalY;
		this.PosGlobalZ = PosGlobalZ;
		this.OwnedChunkID = OwnedChunkID;
		this.OwnedMapID = OwnedMapID;
		this.ObjectType = ObjectType;
		this.moveSpeed = moveSpeed;
		this.ObjectID = ObjectID;
		this.ObjectTypeID = ObjectTypeID;

		Modifiers.isAlphaBlend = isAlphaBlend;
		Modifiers.isSolid = isSolid;
		Modifiers.isPickable = isPickable;
		if (Modifiers.isPickable)
			this.setButtonOnObject();
	}

	public BasicObjectClass(ObjectTypes ObjectType, float moveSpeed,
			boolean isAlphaBlend, int ObjectTypeID, boolean isSolid) {
		this.ObjectType = ObjectType;
		this.moveSpeed = moveSpeed;
		Modifiers.isAlphaBlend = isAlphaBlend;
		Modifiers.isSolid = isSolid;
		this.ObjectTypeID = ObjectTypeID;
	}

	public void setCollision(float CollisionRightX, float CollisionLeftX,
			float CollisionUpY, float CollisionDownY) {
		this.CollisionRightX = CollisionRightX;
		this.CollisionLeftX = CollisionLeftX;
		this.CollisionUpY = CollisionUpY;
		this.CollisionDownY = CollisionDownY;
	}

	public void move(float PosX, float PosY, float PosZ) {
		BasicObjectClass IntersectedObject = MapsManager.checkNoClip(this);
		if (IntersectedObject != null) {
			if (IntersectedObject.Modifiers.isSolid) {
				this.PosGlobalX += PosX * FlatWorld.delta;
				this.PosGlobalY += PosY * FlatWorld.delta;
				this.PosGlobalZ += PosZ * FlatWorld.delta;
			}
		}

		this.PosGlobalX += PosX * FlatWorld.delta;
		this.PosGlobalY += PosY * FlatWorld.delta;
		this.PosGlobalZ += PosZ * FlatWorld.delta;

		if (MapsManager.relocateToRelevantChunk(this) == false) {
			this.PosGlobalX -= PosX * FlatWorld.delta;
			this.PosGlobalY -= PosY * FlatWorld.delta;
			this.PosGlobalZ -= PosZ * FlatWorld.delta;
		}

		IntersectedObject = MapsManager.checkNoClip(this);
		if (IntersectedObject != null) {
			if (IntersectedObject.Modifiers.isSolid) {
				this.PosGlobalX -= PosX * FlatWorld.delta;
				this.PosGlobalY -= PosY * FlatWorld.delta;
				this.PosGlobalZ -= PosZ * FlatWorld.delta;
			}
		}
	}

	public void warpObject(float PosX, float PosY, float PosZ) {
		this.PosGlobalX = PosX;
		this.PosGlobalY = PosY;
		this.PosGlobalZ = PosZ;
	}

	public void updateObject() {
		for (int i = 0; i < ActionsArray.size(); i++) {
			ActionsArray.get(i).updateAction(this);
		}
	}

	public void setButtonOnObject() {
		if (Modifiers.isButton == false) {
			Modifiers.isButton = true;
			ButtonsManager.genNextButtonColor();
			buttonColorR = ButtonsManager.nextButtonColorR;
			buttonColorG = ButtonsManager.nextButtonColorG;
			buttonColorB = ButtonsManager.nextButtonColorB;
		}
	}

	private void rendActions() {
		for (int i = 0; i < ActionsArray.size(); i++) {
			ActionsArray.get(i).rendAction(this);
		}
	}

	public void rendObject(int QuadType, boolean rendAsButton) {
		GL11.glTranslatef(PosGlobalX, PosGlobalY, PosGlobalZ);
		if (rendAsButton) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3b((byte) buttonColorR, (byte) buttonColorG, (byte) buttonColorB);
			GL11.glCallList(QuadType);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		} else {
			GL11.glColor3b((byte) 127, (byte) 127, (byte) 127);
			GL11.glCallList(QuadType);
			if (Modifiers.hasContour) {
				ContourClass.Textures.setTextureByAnimation();
				GL11.glCallList(QuadType);
			}
		}
		GL11.glLoadIdentity();
		this.rendActions();
		Modifiers.hasContour = false;
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType,  boolean rendAsButton) {
		GL11.glTranslatef(tPosGlobalX, tPosGlobalY, tPosGlobalZ);

		if (rendAsButton) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3b((byte) buttonColorR, (byte) buttonColorG, (byte) buttonColorB);
			GL11.glCallList(QuadType);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		} else {
			GL11.glColor3b((byte) 127, (byte) 127, (byte) 127);
			GL11.glCallList(QuadType);
		}
		GL11.glLoadIdentity();
		this.rendActions();
		Modifiers.hasContour = false;
	}
	
	private void rendButton() {
		GL11.glTranslatef(PosGlobalX, PosGlobalY, PosGlobalZ);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3b((byte) buttonColorR, (byte) buttonColorG, (byte) buttonColorB);
		GL11.glCallList(FlatWorld.StandardQuad);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glLoadIdentity();
	}

	public void overMove(float PosX, float PosY, float PosZ) {
		this.PosGlobalX += PosX * FlatWorld.delta;
		this.PosGlobalY += PosY * FlatWorld.delta;
		this.PosGlobalZ += PosZ * FlatWorld.delta;
	}

	public void overRelocateObject(float PosX, float PosY, float PosZ) {
		this.PosGlobalX = PosX;
		this.PosGlobalY = PosY;
	}

	public void rendButtons() {
		if(Modifiers.isButton == true)
			this.rendButton();
		
		for (int i = 0; i < ActionsArray.size(); i++) {
			ActionsArray.get(i).rendButtons(this);
		}
	}

	public void zeroObject() {
		for (int i = 0; i < ActionsArray.size(); i++) {
			ActionsArray.get(i).zeroAction(this);
		}
	}
}
