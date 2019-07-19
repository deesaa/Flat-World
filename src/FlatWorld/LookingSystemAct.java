package FlatWorld;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class LookingSystemAct implements Action{
	public ArrayList<BasicObjectClass> VisibleObjectsArray = new ArrayList<BasicObjectClass>();
	
	double VecViewDirX, VecViewDirY;
	double initialVecViewDirX, initialVecViewDirY;
	float viewAngle;
	float viewSphereRadius;
	float rotateSpeed;
	
	LookingSystemAct(BasicObjectClass Object, float VecViewDirX, float VecViewDirY, float viewAngle, float viewSphereRadius, float rotateSpeed){
		this.VecViewDirX 	  = initialVecViewDirX = VecViewDirX;
		this.VecViewDirY	  = initialVecViewDirY = VecViewDirY;
		this.viewAngle 	 	  = viewAngle;
		this.viewSphereRadius = viewSphereRadius;
		this.rotateSpeed 	  = rotateSpeed;
		
		Object.Modifiers.pointerToLookingSystem = this;
	}
	
	public void updateAction(BasicObjectClass Object) {
		if(Object.ObjectType != ObjectTypes.Player){
			VisibleObjectsArray.clear();
			MapClass tempMap = MapsManager.MapsArray.get(Object.OwnedMapID);
			
			float tempDistToCutChunksGlobalX 	  =  tempMap.distToCutChunksGlobalX * 0.5f;
			float tempDistToCutChunksGlobalY	  =  tempMap.distToCutChunksGlobalY * 0.5f;
			float tempHightDistToCutChunksGlobalX = -tempMap.distToCutChunksGlobalX * 0.5f + ChunkClass.numObjectsInLine;
			float tempHightDistToCutChunksGlobalY = -tempMap.distToCutChunksGlobalY * 0.5f + ChunkClass.numLines;
			float tempChunkGlobalPosX, tempChunkGlobalPosY;
			
			double lenViewVec = Math.sqrt((VecViewDirX * VecViewDirX) + (VecViewDirY * VecViewDirY));
			double lenVecToObj, cos, angle;
			double vecToObjectX, vecToObjectY, scal;
			
			for(int i = 0; i < tempMap.ChunksArray.size(); i++){
				ChunkClass tempChunk = tempMap.ChunksArray.get(i);
				tempChunkGlobalPosX = tempChunk.ChunkGlobalPosX;
				tempChunkGlobalPosY = tempChunk.ChunkGlobalPosY;

				if (tempChunkGlobalPosX + tempHightDistToCutChunksGlobalX < tempMap.PlayerGlobalPosX &&
					tempChunkGlobalPosX + tempDistToCutChunksGlobalX	  > tempMap.PlayerGlobalPosX &&
					tempChunkGlobalPosY + tempHightDistToCutChunksGlobalY < tempMap.PlayerGlobalPosY &&
					tempChunkGlobalPosY + tempDistToCutChunksGlobalY	  > tempMap.PlayerGlobalPosY) 
				{
					for(int i2 = 0; i2 < tempChunk.ObjectsArray.size(); i2++){
						vecToObjectX = tempChunk.ObjectsArray.get(i2).PosGlobalX - Object.PosGlobalX;
						vecToObjectY = tempChunk.ObjectsArray.get(i2).PosGlobalY - Object.PosGlobalY;
						
						scal = (vecToObjectX) * (VecViewDirX) + (vecToObjectY) * (VecViewDirY);
						lenVecToObj = Math.sqrt((vecToObjectX * vecToObjectX) + (vecToObjectY * vecToObjectY));
						
						cos = scal / (lenVecToObj * lenViewVec);
						angle = Math.toDegrees(Math.acos(cos));
						
						if (angle <= viewAngle){
							float fObjPosX = Object.PosGlobalX;
							float fObjPosY = Object.PosGlobalY;
							float sObjPosX = tempChunk.ObjectsArray.get(i2).PosGlobalX;
							float sObjPosY = tempChunk.ObjectsArray.get(i2).PosGlobalY;
							
							double distX = (fObjPosX - sObjPosX) * (fObjPosX - sObjPosX);
							double distY = (fObjPosY - sObjPosY) * (fObjPosY - sObjPosY);
							double finalDist = Math.sqrt((distX + distY));
							
							if(finalDist <= viewSphereRadius){
								VisibleObjectsArray.add(tempChunk.ObjectsArray.get(i2));
							}
						}
					}
				}
			}
		} else {
			float vecX = MouseArrowClass.ArrowWorldCoordX - Object.PosGlobalX;
			float vecY = MouseArrowClass.ArrowWorldCoordY - Object.PosGlobalY;
			float vecLen = (float) (1.0f / Math.sqrt(vecX*vecX + vecY*vecY));
			vecX *= vecLen;
			vecY *= vecLen;
		}
	}
	
	public void rotateViewAt(StructOfOffer Offer){
		//double vecToObjectX, vecToObjectY;
		double angle = Offer.angle;
		//vecToObjectX = Dir.PosGlobalX - Origin.PosGlobalX;
		//vecToObjectY = Dir.PosGlobalY - Origin.PosGlobalY;
		//angle = Math.toDegrees(Math.atan2(vecToObjectX*VecViewDirY - VecViewDirX*vecToObjectY, vecToObjectX*VecViewDirX + vecToObjectY*VecViewDirY));
		
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

	public double findAngle(BasicObjectClass Origin, BasicObjectClass Dir) {
		double vecToObjectX, vecToObjectY;
		
		vecToObjectX = Dir.PosGlobalX - Origin.PosGlobalX;
		vecToObjectY = Dir.PosGlobalY - Origin.PosGlobalY;
		
		return Math.toDegrees(Math.atan2(vecToObjectX*VecViewDirY - VecViewDirX*vecToObjectY, vecToObjectX*VecViewDirX + vecToObjectY*VecViewDirY));
	}

}
