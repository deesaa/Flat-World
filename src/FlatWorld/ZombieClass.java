package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;

public class ZombieClass extends BasicObjectClass{
	public static Map<Integer, Texture> TexturesArray = new Hashtable<Integer, Texture>(4, (float)0.8);
	{
		super.Textures = new TexturesClass(TexturesArray);
		super.Textures.addTexture("png", "data/mobs/Zombie_a1.png");
		super.Textures.addTexture("png", "data/mobs/Zombie_a2.png");
		super.Textures.addTexture("png", "data/mobs/Zombie_a3.png");
		super.Textures.createAnimationList(0, 2);
		super.Textures.setMillsecsToUpdate(150);
	}
	
	ZombieClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID){
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Mob, 0.001f, true, ObjectID);
		super.ActionsArray.add(new ZombieControllerAct());
	}
	
	public void updateObject(){
		//System.out.println( "Zombie :  Map "+super.OwnedMapID + " Chunk " + super.OwnedChunkID +  " ID " + super.ObjectID);
		super.updateObject();
	}
	
	public void rendObject(){
		super.Textures.setTextureByAnimation();
		super.rendObject();
	}
}
