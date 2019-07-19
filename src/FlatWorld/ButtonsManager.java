package FlatWorld;

import java.nio.ByteBuffer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ButtonsManager {
	static ByteBuffer colorUnderArrow = ByteBuffer.allocateDirect(4);
	static byte nextButtonColorR = 1, nextButtonColorG = 0, nextButtonColorB = 0;

	public static void genNextButtonColor() {
		ButtonsManager.incrementColor();
	}

	public static void getColorUnderArrow() {
		GL11.glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL11.GL_RGB,
				GL11.GL_BYTE, colorUnderArrow);
		System.out.println(colorUnderArrow.get());
		System.out.println(colorUnderArrow.get());
		System.out.println(colorUnderArrow.get());
		System.out.println(colorUnderArrow.get());
		colorUnderArrow.clear();
	}

	public static void incrementColor() {
		nextButtonColorR += 1;
		if (nextButtonColorR >= Byte.MAX_VALUE) {
			nextButtonColorR = 0;
			nextButtonColorG += 1;
			if (nextButtonColorG >= Byte.MAX_VALUE) {
				nextButtonColorG = 0;
				nextButtonColorB += 1;
				if (nextButtonColorB >= Byte.MAX_VALUE) {
					nextButtonColorB = 0;
				}
			}
		}
	}
}
