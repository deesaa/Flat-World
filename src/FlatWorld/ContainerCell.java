package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class ContainerCell extends BasicObjectClass {
	public static int ObjectTypeID;
	public static TexturesClass Textures = new TexturesClass("png",
			"data/GUI/InventoryCell.png");

	public int pickedObjectTypeID = -1;
	public ArrayList<BasicObjectClass> ObjectsArray = new ArrayList<BasicObjectClass>();

	BasicObjectClass Owner;
	float indentX, indentY;

	ContainerCell(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID,
			int ObjectID, BasicObjectClass Owner, float indentX, float indentY) 
	{
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false, ObjectID, ContainerCell.ObjectTypeID, false, false);
		
		this.Owner = Owner;
		this.indentX = indentX;
		this.indentY = indentY;
		super.setButtonOnObject();
	}

	ContainerCell() {
		super(ObjectTypes.Cell, 0.0f, false, ContainerCell.ObjectTypeID, false);
	}

	public static void initObject(int bObjectTypeID) {
		ObjectTypeID = bObjectTypeID;
	}

	public void rendObject(int QuadType, boolean rendAsButton) {
		ContainerCell.Textures.setTextureByAnimation();
		//this.PosGlobalX = Owner.PosGlobalX + indentX;
		//this.PosGlobalY = Owner.PosGlobalY + indentY;
		GL11.glTranslatef(Owner.PosGlobalX + indentX, Owner.PosGlobalY + indentY, 0.01f);
		super.rendObject(QuadType, rendAsButton);
	}

	public void rendCellContent() {
		if (pickedObjectTypeID != -1) {
			GL11.glTranslatef(Owner.PosGlobalX + indentX, Owner.PosGlobalY + indentY, 0.01f);
			ObjectsArray.get(0).rendObject(super.PosGlobalX + 0.19f, super.PosGlobalY + 0.19f, super.PosGlobalZ, FlatWorld.IconQuad, false);
		} else
			GL11.glLoadIdentity();
	}

	public void rendCellContentCounter() {
		if (pickedObjectTypeID != -1 && ObjectsArray.size() > 1) {
			GL11.glTranslatef(Owner.PosGlobalX + indentX, Owner.PosGlobalY + indentY, 0.01f);
			TextFieldClass.rendText(String.valueOf(ObjectsArray.size()),
					super.PosGlobalX - 0.2f, super.PosGlobalY + 0.05f,
					super.PosGlobalZ, FlatWorld.InventoryCounterQuad, 0.19f);
		} else
			GL11.glLoadIdentity();
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY,
			float tPosGlobalZ, int QuadType, boolean rendAsButton) {
		ContainerCell.Textures.setTextureByAnimation();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType,
				rendAsButton);
	}

	public void addObject(BasicObjectClass pickedObject) {
		this.pickedObjectTypeID = pickedObject.ObjectTypeID;
		ObjectsArray.add(pickedObject);
	}

	public void rendContur() {
		GL11.glTranslatef(Owner.PosGlobalX + indentX, Owner.PosGlobalY
				+ indentY, 0.01f);
		FlatWorld.StaticObjectsBase.rendObject(ContourClass.ObjectTypeID,
				super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ,
				FlatWorld.StandardQuad, false);
	}

	public void dropObject(float PosGlobalX, float PosGlobalY, float PosGlobalZ) {
		if (pickedObjectTypeID != -1 || ObjectsArray.size() < 0) {
			BasicObjectClass tempObject = null;
			
			tempObject = ObjectsArray.get(ObjectsArray.size() - 1);
		
			tempObject.PosGlobalX = PosGlobalX;
			tempObject.PosGlobalY = PosGlobalY;
			tempObject.PosGlobalZ = PosGlobalZ;

			ObjectsArray.remove(ObjectsArray.size() - 1);
			MapsManager.addObject(tempObject);
		}
		if (ObjectsArray.size() <= 0) {
			this.pickedObjectTypeID = -1;
		}
	}

	public void rendRedContur() {
		GL11.glTranslatef(Owner.PosGlobalX + indentX, Owner.PosGlobalY + indentY, 0.01f);
		FlatWorld.StaticObjectsBase.rendObject(RedContourClass.ObjectTypeID, super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ, FlatWorld.StandardQuad, false);
	}
}
