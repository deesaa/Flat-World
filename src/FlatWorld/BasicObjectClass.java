package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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
	boolean underArrow = false;

	public AnimationClass  Animation;

	public ObjectModifiers Modifiers = new ObjectModifiers();
	public ArrayList<Action> ActionsArray = new ArrayList<Action>();
	
	ColorClass modifColor = ColorClass.Standard;
	
	private LuaValue updateHook;
	public LuaValue luaThisObject;
	public String objectName;
	
	public boolean isPlayer = false;

	public BasicObjectClass() {
		this.ActionsArray.add(new OffersListAct(this));
	}
	
	public BasicObjectClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID,
			ObjectTypes ObjectType, int ObjectID, int ObjectTypeID, boolean isSolid, boolean isClickable) {
		this.PosGlobalX = PosGlobalX;
		this.PosGlobalY = PosGlobalY;
		this.PosGlobalZ = PosGlobalZ;
		this.OwnedChunkID = OwnedChunkID;
		this.OwnedMapID = OwnedMapID;
		this.ObjectType = ObjectType;
		this.ObjectID = ObjectID;
		this.ObjectTypeID = ObjectTypeID;
		
		this.ActionsArray.add(new OffersListAct(this));
	}

	public BasicObjectClass(ObjectTypes ObjectType, int ObjectTypeID, boolean isSolid) {
		this.ObjectType = ObjectType;
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
		System.out.println("|ObjectTypeID:" + this.ObjectTypeID + "; MapID" + this.OwnedMapID +  " ChunkID" + this.OwnedChunkID + " ObjectID" + this.ObjectID + 
				"; X" + this.PosGlobalX + " Y" + this.PosGlobalY + " Z" + this.PosGlobalZ + "|");
	}
	
	public void printFullObjectInfo(){
		if(Modifiers.pCollisionSystem == null)
			System.out.println("CollisionSystem: false");
		else{
			
		}
		
		if(Modifiers.pLightingSystem == null)
			System.out.println("LightingSystem: false");
		else{
			
		}
		
		if(Modifiers.pInventorySystem == null)
			System.out.println("InventorySystem: false");
		else{
			
		}
		
		if(Modifiers.pAnatomySystem == null)
			System.out.println("AnatomySystem: false");
		else{
			
		}
		
		if(Modifiers.pEquipmentSystem == null)
			System.out.println("EquipmentSystem: false");
		else{
			
		}
		
		if(Modifiers.pPickingSystem == null)
			System.out.println("PickingSystem: false");
		else{
			
		}
			
	}
	
	public LuaValue sendMessage(String message, LuaValue luaMessage){
		float tempMoveSpeed = 0.0f;
		if (message.compareTo("MOVE") == 0) { 
			LuaValue moveDir = luaMessage.get("DIRECTION");
			
			if(Modifiers.pMovingSystem != null)
				tempMoveSpeed = Modifiers.pMovingSystem.moveSpeed;
			else
				System.out.println("|MovingSystem: false; MoveSpeed = 0.0|");
				
			this.move(tempMoveSpeed*moveDir.get(1).tofloat(), tempMoveSpeed*moveDir.get(2).tofloat(), 0.0f);
			if(luaMessage.get("AUTO_VIEW_DIRECTION").toboolean()){
				if(this.Modifiers.pLookingSystem != null){
					Vector2f viewDir = new Vector2f(moveDir.get(1).tofloat(), moveDir.get(2).tofloat());
					FlatMath.normalize(viewDir);
					this.Modifiers.pLookingSystem.VecViewDirX = viewDir.x; 
					this.Modifiers.pLookingSystem.VecViewDirY = viewDir.y; 
				}
			}
			return null;
		}
		
		if (message.compareTo("MOVE_LEFT") == 0) { 
			this.move(-tempMoveSpeed, 0.0f, 0.0f);
			return null;
		}
		if (message.compareTo("MOVE_RIGHT") == 0) {
			this.move(tempMoveSpeed, 0.0f, 0.0f);
			return null;
		}
		if (message.compareTo("MOVE_FORWARD") == 0) {
			this.move(0.0f, tempMoveSpeed, 0.0f);
			return null;
		}
		if (message.compareTo("MOVE_BACK") == 0) {
			this.move(0.0f, -tempMoveSpeed, 0.0f);
			return null;
		}
		
		if (message.compareTo("SET_VIEW_DIRECTION") == 0){
			if(this.Modifiers.pLookingSystem != null){
				this.Modifiers.pLookingSystem.VecViewDirX = luaMessage.get(1).todouble(); 
				this.Modifiers.pLookingSystem.VecViewDirY = luaMessage.get(2).todouble(); 
				return null;
			}
		}
		
		if (message.compareTo("DIRECT_CAM_ON") == 0) {
			if(luaMessage != null){
				float camX = luaMessage.get("x").tofloat();
				float camY = luaMessage.get("y").tofloat();
				FlatWorld.mainCamera.setLook(new Vector3f(camX, camY, 1), new Vector3f(camX, camY, 0), null);
			} else {
				FlatWorld.mainCamera.setLook(new Vector3f(this.PosGlobalX, this.PosGlobalY, 1), new Vector3f(this.PosGlobalX, this.PosGlobalY, 0), null);
			}
			return null;
		}
		
		if (message.compareTo("FULL_INFO") == 0) {
			this.printFullObjectInfo();
			return null;
		}
		
		if (message.compareTo("SHOW_HIDE_INVECTORY") == 0) {
			if(luaMessage != null){
				BasicObjectClass object = ObjectsLoader.luaToJava(luaMessage);
				if(object.Modifiers.pInventorySystem != null){
					object.Modifiers.pInventorySystem.isInventoryVisible = !object.Modifiers.pInventorySystem.isInventoryVisible;
				}
			} else {
				if(this.Modifiers.pInventorySystem != null){
					this.Modifiers.pInventorySystem.isInventoryVisible = !this.Modifiers.pInventorySystem.isInventoryVisible;
				}
			}
			return null;
		}
		
		if (message.compareTo("GET_OBJECT_BY_POS") == 0) {
			BasicObjectClass object = MapsManager.getObjectByPos(new Vector2f(luaMessage.get("x").tofloat(), luaMessage.get("y").tofloat()), this.OwnedMapID);
			if(object != null && object.luaThisObject != null){
				return object.luaThisObject;
			}
			else
				return null;
		}
		
		if (message.compareTo("ADD_TO_INVENTORY") == 0) {
			if(Modifiers.pInventorySystem != null){
				BasicObjectClass pickedObject = ObjectsLoader.luaToJava(luaMessage);
				Modifiers.pInventorySystem.addObject(pickedObject);
				
				if(Modifiers.pPickableModif != null){
					Modifiers.pPickableModif.setOwner(this);
				}
				pickedObject.callUpdateHook("PICKED_UP", "PICK", this.luaThisObject);
			}
			return null;
		}

		if (message.compareTo("GET_CURSOR_POS") == 0) {
			LuaTable t = new LuaTable();
			t.rawset("x", MouseArrowClass.ArrowWorldCoordX);
			t.rawset("y", MouseArrowClass.ArrowWorldCoordY);
			return t;
		}
		
		if (message.compareTo("SET_PLAYER_POS") == 0) {
			MapsManager.updatePlayerPos(this.OwnedMapID, this.PosGlobalX, this.PosGlobalY);
			return null;
		}
		
		if (message.compareTo("GET_POS") == 0) {
			if(luaMessage != null){
				LuaTable t = new LuaTable();
				BasicObjectClass te = ObjectsLoader.luaToJava(luaMessage);
				t.rawset("x", te.PosGlobalX);
				t.rawset("y", te.PosGlobalY);
				return t;
			} else {
				LuaTable t = new LuaTable();
				t.rawset("x", this.PosGlobalX);
				t.rawset("y", this.PosGlobalY);
				return t;
			}
		}
		
		if (message.compareTo("DISABLE_LIGHT") == 0) {
			if(this.Modifiers.pLightingSystem != null){
				this.Modifiers.pLightingSystem.lightObject.deleteLight();
			}
			return null;
		}
		
		return null;
	}
	
	public String getName(){
		return this.objectName;
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
	
	public LuaValue callUpdateHook(String message, String systemIdent, LuaValue luaInterObject) {
		if(luaThisObject != null && updateHook != null){
			LuaTable messageTable = new LuaTable();
			messageTable.set("Sys", LuaValue.valueOf(systemIdent));
			messageTable.set("Arg", LuaValue.valueOf(message));
			if(luaInterObject != null)
				messageTable.set("InterObject", luaInterObject);
			
			return this.updateHook.call(this.luaThisObject, messageTable);
		}
		return null;
	}
}
