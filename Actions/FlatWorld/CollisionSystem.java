package FlatWorld;

import org.luaj.vm2.LuaValue;



public class CollisionSystem extends Action{
	float sphereRadius, shiftX, shiftY;
	
	public CollisionSystem() {
		super(null, "COLL");
	}
	public CollisionSystem(BasicObjectClass Object, float sphereRadius, float shiftX, float shiftY) {
		super(Object, "COLL");
		Object.Modifiers.pCollisionSystem = this;
		this.sphereRadius = sphereRadius;
		this.shiftX = shiftX;
		this.shiftY = shiftY;
	}
	
	public CollisionSystem(BasicObjectClass Object, LuaValue configs) {
		super(Object, "COLL");
		Object.Modifiers.pCollisionSystem = this;
	//	Object.Modifiers.isSolid = true;
		
		float tSphereRadius = -1f,  tShiftX = 0, tShiftY = 0;
		this.sphereRadius = ObjectsLoader.getValue(configs, "sphereRadius", tSphereRadius);
		this.shiftX       = ObjectsLoader.getValue(configs, "shiftX", tShiftX);
		this.shiftY       = ObjectsLoader.getValue(configs, "shiftY", tShiftY);
	}
	
	public static boolean checkCollision(BasicObjectClass Object1, BasicObjectClass Object2){
		CollisionSystem collSys1 = Object1.Modifiers.pCollisionSystem;
		CollisionSystem collSys2 = Object2.Modifiers.pCollisionSystem;
		if(Object1.Modifiers.pCollisionSystem == null){
			Object1.printDefObjectInfo();
			System.out.println("Can't check collision. Object doesn't have CollisionSystem");
			return false;
		}
		if(Object2.Modifiers.pCollisionSystem == null){
			Object2.printDefObjectInfo();
			System.out.println("Can't check collision. Object doesn't have CollisionSystem");
			return false;
		}
		
		float d = ((Object1.PosGlobalX+collSys1.shiftX)-(Object2.PosGlobalX+collSys2.shiftX))*((Object1.PosGlobalX+collSys1.shiftX)-(Object2.PosGlobalX+collSys2.shiftX))+
				  ((Object1.PosGlobalY+collSys1.shiftY)-(Object2.PosGlobalY+collSys2.shiftY))*((Object1.PosGlobalY+collSys1.shiftY)-(Object2.PosGlobalY+collSys2.shiftY));
		if(d < (collSys1.sphereRadius+collSys2.sphereRadius)*(collSys1.sphereRadius+collSys2.sphereRadius) && d > (collSys1.sphereRadius-collSys2.sphereRadius)*(collSys1.sphereRadius-collSys2.sphereRadius)){
			return true;
		}
		return false;
	}
	
	public void updateAction(BasicObjectClass Object) {
	}

	public void rendAction(BasicObjectClass Object) {
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
	
	}
}
