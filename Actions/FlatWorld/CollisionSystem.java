package FlatWorld;



public class CollisionSystem extends Action{
	float sphereRadius, shiftX, shiftY;
	
	public CollisionSystem() {
		super(null);
	}
	public CollisionSystem(BasicObjectClass Object, float sphereRadius, float shiftX, float shiftY) {
		super(Object);
		Object.Modifiers.pointerToCollisionSystem = this;
		this.sphereRadius = sphereRadius;
		this.shiftX = shiftX;
		this.shiftY = shiftY;
	}
	
	public static boolean checkCollision(BasicObjectClass Object1, BasicObjectClass Object2){
		CollisionSystem collSys1 = Object1.Modifiers.pointerToCollisionSystem;
		CollisionSystem collSys2 = Object2.Modifiers.pointerToCollisionSystem;
		if(Object1.Modifiers.pointerToCollisionSystem == null){
			Object1.printDefObjectInfo();
			System.out.println("Can't check collision. Object doesn't have CollisionSystem");
			return false;
		}
		if(Object2.Modifiers.pointerToCollisionSystem == null){
			Object2.printDefObjectInfo();
			System.out.println("Can't check collision. Object doesn't have CollisionSystem");
			return false;
		}
		
		float d = (Object1.PosGlobalX-Object2.PosGlobalX)*(Object1.PosGlobalX-Object2.PosGlobalX)+(Object1.PosGlobalY-Object2.PosGlobalY)*(Object1.PosGlobalY-Object2.PosGlobalY);
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
