package FlatWorld;

import java.util.Random;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;

/*public class ColorClass {
	private static Random rand = new Random();
	public static final int L_COMBINE = 1, L_FALSE = 2, L_TRUE = 3;



	public static ColorClass randomColor(ColorClass seed, int minVal, int maxVal) {
		rand.setSeed(System.currentTimeMillis());
		
		int number = (minVal + rand.nextInt(Math.abs(minVal)+maxVal));
		byte newColorModifR = FlatMath.toByte(seed.colorModifR+number);
		
		number = (minVal + rand.nextInt(Math.abs(minVal)+maxVal));
		byte newColorModifG =  FlatMath.toByte(seed.colorModifG+number);
		
		number = (minVal + rand.nextInt(Math.abs(minVal)+maxVal));
		byte newColorModifB =  FlatMath.toByte(seed.colorModifB+number);
		
		return new ColorClass(newColorModifR, newColorModifG, newColorModifB, (byte)127);
	}
}*/
