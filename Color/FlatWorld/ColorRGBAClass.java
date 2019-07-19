package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;

public class ColorRGBAClass extends UniteColorClass{
	public static final ColorRGBAClass Red        = new ColorRGBAClass((byte) 127, (byte) 0,   (byte) 0,   (byte) 127, true);
	public static final ColorRGBAClass Green      = new ColorRGBAClass((byte) 0,   (byte) 127, (byte) 0,   (byte) 127, true);
	public static final ColorRGBAClass Blue       = new ColorRGBAClass((byte) 0,   (byte) 0,   (byte) 127, (byte) 127, true);
	public static final ColorRGBAClass Yellow     = new ColorRGBAClass((byte) 127, (byte) 127, (byte) 0,   (byte) 127, true);
	public static final ColorRGBAClass Standard   = new ColorRGBAClass((byte) 127, (byte) 127, (byte) 127, (byte) 127, true);
	public static final ColorRGBAClass Black   	  = new ColorRGBAClass((byte) 0,   (byte) 0,   (byte) 0,   (byte) 127, true);
	public static final ColorRGBAClass RedOrange  = new ColorRGBAClass((byte) 120, (byte) 43,  (byte) 10,  (byte) 127, true);
	
	public byte colorR, colorG, colorB, colorA;
	
	public ColorRGBAClass(byte R, byte G, byte B, byte A, boolean lighting) {
		super(lighting);
		this.colorR = R;
		this.colorG = G;
		this.colorB = B;
		this.colorA = A;
	}
	
	public ColorRGBAClass(LuaValue color){
		super(color);
		
		if(color.get(1).isnumber()){
			colorR = color.get(1).tobyte();
			colorG = color.get(2).tobyte();
			colorB = color.get(3).tobyte();
			colorA = color.get(4).tobyte();
		} else {
			switch (color.get(1).tojstring()) {
			case "Red":
				ColorRGBAClass.Red.copyIn(this);
				break;
			case "ST":
				ColorRGBAClass.Standard.copyIn(this);
				break;
			case "Black":
				ColorRGBAClass.Black.copyIn(this);
				break;
			default:
				ColorRGBAClass.Standard.copyIn(this);
				break;
			}
		}
	}
	
	public void setColor(){
		super.setColor();
		GL11.glColor4b((byte) this.colorR, (byte) this.colorG, (byte) this.colorB, (byte) this.colorA);
	}
	
	public void copyIn(ColorRGBAClass newColor){
		newColor.colorR = this.colorR;
		newColor.colorG = this.colorG;
		newColor.colorB = this.colorB;
		newColor.colorA = this.colorA;
	}
}
