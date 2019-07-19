package FlatWorld;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.stream.ImageInputStream;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TexturesClass {
	Texture TexturesArray;
	
	public TexturesClass(String Format, String TexturePath) {
		try {
			TexturesArray = TextureLoader.getTexture(Format, ResourceLoader.getResourceAsStream(TexturePath), GL11.GL_NEAREST);
		} catch (IOException e) {
			System.err.println("Can't load " + TexturePath);
		} finally {
		}
	}

	public void setTexture() {
		TexturesArray.bind();
	}
}
