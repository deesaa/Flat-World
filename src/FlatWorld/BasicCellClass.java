package FlatWorld;

import org.lwjgl.opengl.GL11;

public class BasicCellClass {
	float PosGlobalX, PosGlobalY, PosGlobalZ;
	int ObjectID;
	int ObjectTypeID;
	int OwnedChunkID, OwnedMapID;
	ObjectTypes ObjectType;
	
	public BasicCellClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID,
			ObjectTypes ObjectType, int ObjectID, int ObjectTypeID) {
		this.PosGlobalX = PosGlobalX;
		this.PosGlobalY = PosGlobalY;
		this.PosGlobalZ = PosGlobalZ;
		this.OwnedChunkID = OwnedChunkID;
		this.OwnedMapID = OwnedMapID;
		this.ObjectType = ObjectType;
		this.ObjectID = ObjectID;
		this.ObjectTypeID = ObjectTypeID;
	}

	public BasicCellClass(ObjectTypes ObjectType, float moveSpeed, int ObjectTypeID, boolean isSolid) {
		this.ObjectType = ObjectType;
		this.ObjectTypeID = ObjectTypeID;
	}

	public void warpObject(float PosX, float PosY, float PosZ) {
		this.PosGlobalX = PosX;
		this.PosGlobalY = PosY;
		this.PosGlobalZ = PosZ;
	}


	public void rendObject(QuadClass Quad) {
		GL11.glTranslatef(PosGlobalX, PosGlobalY, PosGlobalZ);
	//	ColorClass.Standard.setColorFilter();
		Quad.rend();
		GL11.glLoadIdentity();
	}
	
	public void rendObject(QuadClass Quad, ImageClass image) {
		GL11.glTranslatef(PosGlobalX, PosGlobalY, PosGlobalZ);
	//	ColorClass.Standard.setColorFilter();
		Quad.rend(image, null);
		GL11.glLoadIdentity();
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		GL11.glTranslatef(tPosGlobalX, tPosGlobalY, tPosGlobalZ);
	//	ColorClass.Standard.setColorFilter();
		Quad.rend();
		GL11.glLoadIdentity();
	}

	public void overRelocateObject(float PosX, float PosY, float PosZ) {
		this.PosGlobalX = PosX;
		this.PosGlobalY = PosY;
	}
}
