package FlatWorld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TexturesClass {
	public Map<Integer, Texture> TexturesArray;
	public ArrayList<int[]> AnimationLists = new ArrayList<int[]>();
	private int NumTextures = 0;
	private int installedTexture = 0;
	private int installedAnimation = 0;
	int millsecsToUpdate = 1000;
	int millsecsPassed = 0;
	public boolean wasItUpdateBeforeSetting;
	
	public TexturesClass(String Format, String TexturePath){
		TexturesArray = new Hashtable<Integer, Texture>(2, (float)0.8);
		try { 
			TexturesArray.put(NumTextures, TextureLoader.getTexture(Format, 
					ResourceLoader.getResourceAsStream(TexturePath)));
			NumTextures++;
		} catch (IOException e) { 
			System.err.println("Can't load " +  TexturePath); 
		} finally {
        }
		TexturesArray.get(NumTextures-1).setTextureFilter(GL11.GL_NEAREST);
	}
	
	public TexturesClass(Map<Integer, Texture> TexturesArray){
		this.TexturesArray = TexturesArray;
	}
	
	public void addTexture(String Format, String TexturePath){
		try { 
			TexturesArray.put(NumTextures, TextureLoader.getTexture(Format, 
					ResourceLoader.getResourceAsStream(TexturePath)));
			NumTextures++;
		} catch (IOException e) { 
			System.err.println("Can't load " +  TexturePath); 
		} finally {
        }
		TexturesArray.get(NumTextures-1).setTextureFilter(GL11.GL_NEAREST);
	}
	
	public void setMillsecsToUpdate(int millsecs){
		this.millsecsToUpdate = millsecs;
	}
	
	public boolean createAnimationList(int firstTexID, int finalTexID) {
		if(firstTexID <= finalTexID && finalTexID < TexturesArray.size()){
			int[] interval = {firstTexID, finalTexID};
			AnimationLists.add(interval);
		} else {
			int[] interval = {0, 0};
			AnimationLists.add(interval);
		}
		return true;
	}
	
	public void setAnimation(int AnimationID){
		if(AnimationID < AnimationLists.size() && AnimationID != installedAnimation){
			installedAnimation = AnimationID;
			installedTexture = AnimationLists.get(installedAnimation)[0];
		}
	}
	
	public void updateAnimation(){
		millsecsPassed += FlatWorld.delta;				//Обновляем прошедшее время
		if(millsecsPassed >= millsecsToUpdate){
			installedTexture++;							//Переключаем на следующую текстуру
			if(installedTexture > AnimationLists.get(installedAnimation)[1]){		//Если это была последняя, то переключаем на первую
				installedTexture = AnimationLists.get(installedAnimation)[0];
			}
			millsecsPassed = 0; 							//Сбрасываем счетчик
		}
		wasItUpdateBeforeSetting = true;
	}
	
	public void restartAnimation(){
		millsecsPassed 	 = 0; 
		installedTexture = AnimationLists.get(installedAnimation)[0];
	}
	
	public void setTextureByAnimation() {
		TexturesArray.get(installedTexture).bind();
		wasItUpdateBeforeSetting = false;
	}
	
	public void setInitedTexture(int textureNumber){
		if(textureNumber < NumTextures){
			TexturesArray.get(textureNumber).bind();
		}
		wasItUpdateBeforeSetting = false;
	}
	
	public void setFirstTexture(){
		if(NumTextures != 0)
			TexturesArray.get(0).bind();
	}
	
	public int getNumTextures(){
		return NumTextures;
	}
}
