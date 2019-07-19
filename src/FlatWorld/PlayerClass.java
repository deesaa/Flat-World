package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;

public class PlayerClass extends BasicObjectClass{
	public static Map<Integer, Texture> TexturesArray = new Hashtable<Integer, Texture>(4, (float)0.8);
	{
		super.Textures = new TexturesClass(TexturesArray);
		super.Textures.addTexture("png", "data/players/Player_a1.png");
		super.Textures.addTexture("png", "data/players/Player_a2.png");
		super.Textures.createAnimationList(0, 1);
		super.Textures.addTexture("png", "data/players/Player_a3.png");
		super.Textures.addTexture("png", "data/players/Player_a4.png");
		super.Textures.createAnimationList(2, 3);
		super.Textures.addTexture("png", "data/players/Player_a5.png");
		super.Textures.addTexture("png", "data/players/Player_a6.png");
		super.Textures.createAnimationList(4, 5);
		super.Textures.addTexture("png", "data/players/Player_a7.png");
		super.Textures.addTexture("png", "data/players/Player_a8.png");
		super.Textures.createAnimationList(6, 7);
		super.Textures.setMillsecsToUpdate(150);
	}
	
	PlayerClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID){
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Player, 0.005f, true, ObjectID);
		super.ActionsArray.add(new PlayerControllerAct());
	}
	
	public void updateObject(){
		//System.out.println( "Player :  Map "+super.OwnedMapID + " Chunk " + super.OwnedChunkID +  " ID " + super.ObjectID);
		super.updateObject();
	}
	
	public void rendObject(){
		super.Textures.setTextureByAnimation();
		super.rendObject();
	}
}
