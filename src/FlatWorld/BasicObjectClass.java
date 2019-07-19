package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

enum ObjectTypes {
	Cell, Object, Player, Mob
};

public class BasicObjectClass {
	float PosGlobalX, PosGlobalY, PosGlobalZ;
	float mapRendShiftX, mapRendShiftY;
	public float CollisionRightX = 0.5f, CollisionLeftX = 0.5f, CollisionUpY = 0.5f, CollisionDownY = 0.5f;
	int ObjectID;
	int ObjectTypeID;
	int OwnedChunkID, OwnedMapID;
	ObjectTypes ObjectType;
	float moveSpeed;

	public AnimationsList  Animations;


	ObjectModifiers Modifiers = new ObjectModifiers();
	public ArrayList<Action> ActionsArray = new ArrayList<Action>();
	
	ColorClass modifColor = ColorClass.Standard;

	public BasicObjectClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID,
			ObjectTypes ObjectType, float moveSpeed, int ObjectID, int ObjectTypeID, boolean isSolid, boolean isPickable) {
		this.PosGlobalX = PosGlobalX;
		this.PosGlobalY = PosGlobalY;
		this.PosGlobalZ = PosGlobalZ;
		this.OwnedChunkID = OwnedChunkID;
		this.OwnedMapID = OwnedMapID;
		this.ObjectType = ObjectType;
		this.moveSpeed = moveSpeed;
		this.ObjectID = ObjectID;
		this.ObjectTypeID = ObjectTypeID;

		Modifiers.isSolid = isSolid;
		Modifiers.isClickable = isPickable;
		if (Modifiers.isClickable)
			this.setButtonOnObject();
	}

	public BasicObjectClass(ObjectTypes ObjectType, float moveSpeed, int ObjectTypeID, boolean isSolid) {
		this.ObjectType = ObjectType;
		this.moveSpeed = moveSpeed;
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
		}
	}

	public void rendActions() {
		for (int i = 0; i < ActionsArray.size(); i++) {
			ActionsArray.get(i).rendAction(this);
		}
	}

	public void rendObject(QuadClass Quad) {
		GL11.glTranslatef(PosGlobalX, PosGlobalY, PosGlobalZ);
		modifColor.setColorFilter();
		this.Animations.setAnimation();
		Quad.rend();
		if (Modifiers.hasContour) {
			ContourClass.CellTexture.bind();
			Quad.rend();
		}
		GL11.glLoadIdentity();
		this.rendActions();
		Modifiers.hasContour = false;
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		GL11.glTranslatef(tPosGlobalX, tPosGlobalY, tPosGlobalZ);
		modifColor.setColorFilter();
		this.Animations.setAnimation();
		Quad.rend();
		GL11.glLoadIdentity();
		this.rendActions();
		Modifiers.hasContour = false;
	}
	
	public void setRendShift(float rendShiftX, float rendShiftY){
		this.mapRendShiftX = rendShiftX;
		this.mapRendShiftY = rendShiftY;
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

	public void zeroObject() {
		for (int i = 0; i < ActionsArray.size(); i++) {
			ActionsArray.get(i).zeroAction(this);
		}
	}
	
	public void printDefObjectInfo(){
		System.out.println("|ObTyID:" + this.ObjectTypeID + "; MID" + this.OwnedMapID +  " CID" + this.OwnedChunkID + " OID" + this.ObjectID + 
				"; X" + this.PosGlobalX + " Y" + this.PosGlobalY + " Z" + this.PosGlobalZ + "|");
	}
}
