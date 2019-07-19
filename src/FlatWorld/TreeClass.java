package FlatWorld;

import java.util.ArrayList;

public class TreeClass extends BasicObjectClass{
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static SpriteSheet spriteSheet = new SpriteSheet("data/Objects/Tree.png", 16, 32);
	public static ArrayList<ImageClass> ImageArray = new ArrayList<ImageClass>();
	
	{
		super.Animation = new AnimationClass(0, "Tree");
		super.Animation.addFrame(ImageArray.get(0), 300);
		super.Animation.pickAnimation();
		
		super.Animation.createTagAnimation(0, "TreeCutting");
		super.Animation.getTagAnimation(0).addFrame(ImageArray.get(0), 500);
		super.Animation.getTagAnimation(0).addFrame(ImageArray.get(3), 100);
		super.Animation.getTagAnimation(0).addFrame(ImageArray.get(4), 100);
		super.Animation.getTagAnimation(0).addFrame(ImageArray.get(5), 100);
	}
	
	TreeClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, ObjectID, TreeClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new ShadowsSystem(this));
		super.ActionsArray.add(new DestructionSystem(this, 100));
		super.ActionsArray.add(new CollisionSystem(this, 0.085f, 0.0f, 0.0f));
		super.ActionsArray.add(new MaterialSystem(this, MaterialClass.Wood));
		super.ActionsArray.add(new DropSystem(this));
	}
	
	public static void initObject() {
		ImageArray.add(new ImageClass(spriteSheet.getSprite(0, 0)));
		
		ImageArray.add(new ImageClass(spriteSheet.getSprite(1, 0)));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(2, 0)));
		
		ImageArray.add(new ImageClass(spriteSheet.getSprite(0, 1)));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(1, 1)));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(2, 1)));
	}
	
	public void rendObject(QuadClass Quad, ImageClass image) {
		super.Animation.updateFrame();
		super.rendObject(QuadClass.highQuad, super.Animation.getCurrentImage());
	}
}	
