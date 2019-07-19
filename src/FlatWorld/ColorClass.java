package FlatWorld;

import java.util.Random;

import org.lwjgl.opengl.GL11;

public class ColorClass {
	private static Random rand = new Random();
	public static final ColorClass Red        = new ColorClass((byte) 127, (byte) 0,   (byte) 0,   (byte) 127);
	public static final ColorClass Green      = new ColorClass((byte) 0,   (byte) 127, (byte) 0,   (byte) 127);
	public static final ColorClass Blue       = new ColorClass((byte) 0,   (byte) 0,   (byte) 127, (byte) 127);
	public static final ColorClass Yellow     = new ColorClass((byte) 127, (byte) 127, (byte) 0,   (byte) 127);
	public static final ColorClass Standard   = new ColorClass((byte) 127, (byte) 127, (byte) 127, (byte) 127);
	public static final ColorClass RedOrange  = new ColorClass((byte) 120, (byte) 43,  (byte) 10,   (byte) 127);
	
	public final byte colorModifR, colorModifG, colorModifB, colorModifA;
	
	public ColorClass(byte R, byte G, byte B, byte A) {
		this.colorModifR = R;
		this.colorModifG = G;
		this.colorModifB = B;
		this.colorModifA = A;
	}
	
	public void setColorFilter(){
		GL11.glColor4b((byte) this.colorModifR, (byte) this.colorModifG, (byte) this.colorModifB, (byte) this.colorModifA);
	}
	
	public static ColorClass genColor(){
		return null;
	}

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
}
