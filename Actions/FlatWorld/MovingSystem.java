package FlatWorld;

import org.luaj.vm2.LuaValue;



public class MovingSystem extends Action{
	BasicObjectClass randomPosition = null;
	float moveSpeed;
	double finalDist, angle;
	
	boolean movingOnRandomPositionsMode = true;
	
	public MovingSystem(BasicObjectClass Object) {
		super(Object, "MOVE");
		Object.Modifiers.pMovingSystem = this;
	}
	
	public MovingSystem(BasicObjectClass Object, LuaValue tempLuaValue) {
		super(Object, "MOVE");
		Object.Modifiers.pMovingSystem = this;
		
		moveSpeed = tempLuaValue.get("MoveSpeed").tofloat();
		movingOnRandomPositionsMode = tempLuaValue.get("StupidUnstoppableMovingOnRandomPositions").toboolean();
	}
	
	public void directMovingTo(BasicObjectClass Origin, BasicObjectClass Dir){
		float x, y;
		float normalX, normalY;
		x = Dir.PosGlobalX - Origin.PosGlobalX;
		y = Dir.PosGlobalY - Origin.PosGlobalY;
		double vecLenght 	= Math.sqrt(x*x + y*y);
		double vecInvLenght = 1/vecLenght;
		normalX = (float)(x*vecInvLenght);
		normalY = (float)(y*vecInvLenght);
		
		Origin.move(normalX * this.moveSpeed, normalY * this.moveSpeed, 0.0f);
		Origin.Animation.updateFrame();
	}
	
	public void directMovingTo(BasicObjectClass Origin, float DirX, float DirY){
		float x, y;
		float normalX, normalY;
		x = DirX - Origin.PosGlobalX;
		y = DirY - Origin.PosGlobalY;
		double vecLenght 	= Math.sqrt(x*x + y*y);
		double vecInvLenght = 1/vecLenght;
		normalX = (float)(x*vecInvLenght);
		normalY = (float)(y*vecInvLenght);
		
		Origin.move(normalX * this.moveSpeed, normalY * this.moveSpeed, 0.0f);
		Origin.Animation.updateFrame();
	}
	
	public void updateAction(BasicObjectClass Object) {
		if(movingOnRandomPositionsMode){
			if(randomPosition == null){
				float randomPositionToMovingX = (float) (Object.PosGlobalX + (Math.random()*20) - 10);
				float randomPositionToMovingY = (float) (Object.PosGlobalY + (Math.random()*20) - 10);
				randomPosition = new ContourClass(randomPositionToMovingX, randomPositionToMovingY, -25.0f, -1, -1, -1);   //Пустой, никому не нужный, невидимый объект-указатель
			}
		
			if(randomPosition != null){
				float fObjPosX = Object.PosGlobalX;         float fObjPosY = Object.PosGlobalY;
				float sObjPosX = randomPosition.PosGlobalX; float sObjPosY = randomPosition.PosGlobalY;
		
				double distX = (fObjPosX - sObjPosX) * (fObjPosX - sObjPosX);
				double distY = (fObjPosY - sObjPosY) * (fObjPosY - sObjPosY);
				finalDist = Math.sqrt((distX + distY));
				angle = Object.Modifiers.pLookingSystem.findAngleToView(Object, randomPosition);
			
				Object.Modifiers.pOffersList.addOffer(new ArrayOffersElement(randomPosition, finalDist, angle), OffersMessages.RandomMoving, this, 1);
			}
		}
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void rendButtons(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		if(Offer.OfferElement.distance < 0.7d)
			randomPosition = null;
	}
}
