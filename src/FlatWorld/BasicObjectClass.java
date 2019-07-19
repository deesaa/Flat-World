package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;

enum ObjectTypes {
	Cell, Object, Player, Mob
};

public class BasicObjectClass {
	float PosGlobalX, PosGlobalY, PosGlobalZ;
	float mapRendShiftX, mapRendShiftY;
	int ObjectID;
	public int ObjectTypeID;
	int OwnedChunkID, OwnedMapID;
	ObjectTypes ObjectType;
	float moveSpeed;
	boolean underArrow = false;

	public AnimationClass  Animation;

	public ObjectModifiers Modifiers = new ObjectModifiers();
	public ArrayList<Action> ActionsArray = new ArrayList<Action>();
	
	ColorClass modifColor = ColorClass.Standard;
	
	LuaValue updateHook, luaThisObject;

	public BasicObjectClass() {
		this.ActionsArray.add(new OffersListAct(this));
	}
	
	public BasicObjectClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID,
			ObjectTypes ObjectType, float moveSpeed, int ObjectID, int ObjectTypeID, boolean isSolid, boolean isClickable) {
		this.PosGlobalX = PosGlobalX;
		this.PosGlobalY = PosGlobalY;
		this.PosGlobalZ = PosGlobalZ;
		this.OwnedChunkID = OwnedChunkID;
		this.OwnedMapID = OwnedMapID;
		this.ObjectType = ObjectType;
		this.moveSpeed = moveSpeed;
		this.ObjectID = ObjectID;
		this.ObjectTypeID = ObjectTypeID;
		
		this.ActionsArray.add(new OffersListAct(this));
	}

	public BasicObjectClass(ObjectTypes ObjectType, float moveSpeed, int ObjectTypeID, boolean isSolid) {
		this.ObjectType = ObjectType;
		this.moveSpeed = moveSpeed;
		this.ObjectTypeID = ObjectTypeID;
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
		//	if (IntersectedObject.Modifiers.pointerToCollisionSystem != null) {
				this.PosGlobalX -= PosX * FlatWorld.delta;
				this.PosGlobalY -= PosY * FlatWorld.delta;
				this.PosGlobalZ -= PosZ * FlatWorld.delta;
			//}
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
		if(this.updateHook != null && this.updateHook != LuaValue.NIL)
			this.updateHook.call(luaThisObject);
		
		this.underArrow = false;
	}

	public void rendActions() {
		for (int i = 0; i < ActionsArray.size(); i++) {
			ActionsArray.get(i).rendAction(this);
		}
	}
	
	public void rendObject(QuadClass Quad, ImageClass image) {
		this.rendActions();
		GL11.glTranslatef(PosGlobalX, PosGlobalY, PosGlobalZ);
		modifColor.setColorFilter();
		Quad.rend(image);
		if (Modifiers.hasContour) {
			ContourClass.CellTexture.bind();
			Quad.rend();
		}
		GL11.glLoadIdentity();
		Modifiers.hasContour = false;
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad, ImageClass image) {
		this.rendActions();
		GL11.glTranslatef(tPosGlobalX, tPosGlobalY, tPosGlobalZ);
		modifColor.setColorFilter();
		Quad.rend(image);
		GL11.glLoadIdentity();
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
	
	
	
	/* OBJECT LOAD */
	
	public void setPos(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID){
		this.PosGlobalX = PosGlobalX;
		this.PosGlobalY = PosGlobalY;
		this.PosGlobalZ = PosGlobalZ;
		this.OwnedChunkID = OwnedChunkID;
		this.OwnedMapID = OwnedMapID;
		this.ObjectID = ObjectID;
	}
	
	public AnimationClass initMainAnimation(int animationID, String animationName){
		this.Animation = new AnimationClass(animationID, animationName);
		return this.Animation;
	}
	
	public void enableShadows(){
		this.ActionsArray.add(new LightingSystem(this));
	}
	
	public void pickableTrue(){
		new PickableModif(this);
	}
	
	public void initInventory(int numCellsInLine, int numLines, float shiftX, float shiftY){
		this.ActionsArray.add(new InventorySystem(this, numCellsInLine, numLines, shiftX, shiftY, null));
	}
	
	public AnimationClass getMainAnimation(){
		return this.Animation;
	}
	
	public ObjectModifiers getModifiers(){
		return this.Modifiers;
	}

	public void setLuaUpdateHook(LuaValue updateHook, LuaValue luaThisObject) {
		this.updateHook = updateHook;
		this.luaThisObject = luaThisObject;
	}

	public void callUpdateHook(String message, String systemIdent) {
		if(luaThisObject != null && updateHook != null)
		this.updateHook.call(this.luaThisObject, LuaValue.valueOf(message), LuaValue.valueOf(systemIdent));
	}
}
