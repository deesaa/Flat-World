package FlatWorld;

public class FlatMath {
	static double vecPointAngle(float coordObj1X, float coordObj1Y, float coordObj1X2, float coordObj1Y2,
			float coordObj2X, float coordObj2Y, float coordObj2X2, float coordObj2Y2){
		double vecX = coordObj1X - coordObj1X2;
		double vecY = coordObj1Y - coordObj1Y2;
		double vecToObjectX = coordObj2X - coordObj2X2;
		double vecToObjectY = coordObj2Y - coordObj2Y2;
		return Math.toDegrees(Math.atan2(vecToObjectX*vecY - vecX*vecToObjectY, vecToObjectX*vecX + vecToObjectY*vecY));
	}
	
	public static double objectDist(BasicObjectClass Obj1, BasicObjectClass Obj2){
		double distX = (Obj1.PosGlobalX - Obj2.PosGlobalX) * (Obj1.PosGlobalX - Obj2.PosGlobalX);
		double distY = (Obj1.PosGlobalY - Obj2.PosGlobalY) * (Obj1.PosGlobalY - Obj2.PosGlobalY);
		return Math.sqrt((distX + distY));
	}
	
	static byte toByte(int val){
		if(val > Byte.MAX_VALUE)
			return Byte.MAX_VALUE;
		if(val < Byte.MIN_VALUE)
			return Byte.MIN_VALUE;
		return (byte)val;
	}
}
