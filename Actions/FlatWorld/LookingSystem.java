package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaValue;


public class LookingSystem extends Action{
	public ArrayList<BasicObjectClass> VisibleObjectsArray = new ArrayList<BasicObjectClass>();
	
	double VecViewDirX, VecViewDirY;
	double initialVecViewDirX, initialVecViewDirY;
	float viewAngle;
	float viewSphereRadius;
	float rotateSpeed;
	
	LookingSystem(BasicObjectClass Object, float VecViewDirX, float VecViewDirY, float viewAngle, float viewSphereRadius, float rotateSpeed){
		super(Object, "LOOK");
		this.VecViewDirX 	  = initialVecViewDirX = VecViewDirX;
		this.VecViewDirY	  = initialVecViewDirY = VecViewDirY;
		this.viewAngle 	 	  = viewAngle;
		this.viewSphereRadius = viewSphereRadius;
		this.rotateSpeed 	  = rotateSpeed;
		
		Object.Modifiers.pLookingSystem = this;
	}
	
	public LookingSystem(BasicObjectClass Object, LuaValue tempLuaValue) {
		super(Object, "LOOK");
		Object.Modifiers.pLookingSystem = this;
		
		this.VecViewDirX 	  = tempLuaValue.get("VecViewDirX").tofloat();
		this.VecViewDirY 	  = tempLuaValue.get("VecViewDirY").tofloat();
		this.viewAngle 		  = tempLuaValue.get("viewAngle").tofloat();
		this.viewSphereRadius = tempLuaValue.get("viewSphereRadius").tofloat();
		this.rotateSpeed 	  = tempLuaValue.get("rotateSpeed").tofloat();	
	}

	public void updateAction(BasicObjectClass Object) {
		if(Object.ObjectType != ObjectTypes.Player){
			VisibleObjectsArray.clear();
			MapClass tempMap = MapsManager.MapsArray.get(Object.OwnedMapID);	
			double angle;
			for(int i = 0; i < tempMap.VisibleChunksArray.size(); i++){
				ChunkClass tempChunk = tempMap.VisibleChunksArray.get(i);
				for(int i2 = 0; i2 < tempChunk.ObjectsArray.size(); i2++){
					angle = this.findAngleToView(Object, tempChunk.ObjectsArray.get(i2).PosGlobalX, tempChunk.ObjectsArray.get(i2).PosGlobalY);
					if (angle < viewAngle && angle > -viewAngle){
						double finalDist = FlatMath.objectDist(Object, tempChunk.ObjectsArray.get(i2));
						if(finalDist <= viewSphereRadius){
							VisibleObjectsArray.add(tempChunk.ObjectsArray.get(i2));
						}
					}
				}
			}
		} else {
			VecViewDirX = MouseArrowClass.ArrowWorldCoordX - Object.PosGlobalX;
			VecViewDirY = MouseArrowClass.ArrowWorldCoordY - Object.PosGlobalY;
		}
	}
	
	public void rotateViewAt(StructOfOffer Offer){
		double angle = Offer.OfferElement.angle;
		double finalRotateSpeed = (rotateSpeed+(Math.abs(angle)*0.003f))*FlatWorld.delta;
		double oldX = VecViewDirX;
		double oldY = VecViewDirY;
		
		if(angle > 0){
			this.VecViewDirX = oldX*Math.cos(Math.toRadians(-finalRotateSpeed)) - oldY*Math.sin(Math.toRadians(-finalRotateSpeed));
			this.VecViewDirY = oldX*Math.sin(Math.toRadians(-finalRotateSpeed)) + oldY*Math.cos(Math.toRadians(-finalRotateSpeed));
		} else {
			this.VecViewDirX = oldX*Math.cos(Math.toRadians(finalRotateSpeed)) - oldY*Math.sin(Math.toRadians(finalRotateSpeed));
			this.VecViewDirY = oldX*Math.sin(Math.toRadians(finalRotateSpeed)) + oldY*Math.cos(Math.toRadians(finalRotateSpeed));
		}
	}
	
	public void rotateViewAt(BasicObjectClass Origin, float DirX, float DirY){
		double angle = this.findAngleToView(Origin, DirX, DirY);
		
		double finalRotateSpeed = (rotateSpeed+(Math.abs(angle)*0.003f))*FlatWorld.delta;
		double oldX = VecViewDirX;
		double oldY = VecViewDirY;
		
		if(angle > 0){
			this.VecViewDirX = oldX*Math.cos(Math.toRadians(-finalRotateSpeed)) - oldY*Math.sin(Math.toRadians(-finalRotateSpeed));
			this.VecViewDirY = oldX*Math.sin(Math.toRadians(-finalRotateSpeed)) + oldY*Math.cos(Math.toRadians(-finalRotateSpeed));
		} else {
			this.VecViewDirX = oldX*Math.cos(Math.toRadians(finalRotateSpeed)) - oldY*Math.sin(Math.toRadians(finalRotateSpeed));
			this.VecViewDirY = oldX*Math.sin(Math.toRadians(finalRotateSpeed)) + oldY*Math.cos(Math.toRadians(finalRotateSpeed));
		}
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void rendButtons(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}

	public double findAngleToView(BasicObjectClass Origin, BasicObjectClass Dir) {
		double vecToObjectX, vecToObjectY;
		
		vecToObjectX = Dir.PosGlobalX - Origin.PosGlobalX;
		vecToObjectY = Dir.PosGlobalY - Origin.PosGlobalY;
		
		return Math.toDegrees(Math.atan2(vecToObjectX*VecViewDirY - VecViewDirX*vecToObjectY, vecToObjectX*VecViewDirX + vecToObjectY*VecViewDirY));
	}
	
	public double findAngleToView(BasicObjectClass Origin, float DirX, float DirY) {
		double vecToObjectX, vecToObjectY;
		
		vecToObjectX = DirX - Origin.PosGlobalX;
		vecToObjectY = DirY - Origin.PosGlobalY;
		
		return Math.toDegrees(Math.atan2(vecToObjectX*VecViewDirY - VecViewDirX*vecToObjectY, vecToObjectX*VecViewDirX + vecToObjectY*VecViewDirY));
	}
}
