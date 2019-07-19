package FlatWorld;

import java.util.ArrayList;


public class PlayerClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static ArrayList<Integer> PickableObjectsArray = new ArrayList<Integer>();
	public static ArrayList<Integer> EnemiesArray = new ArrayList<Integer>();
	PlayerGUIAct PlayerGUI = new PlayerGUIAct(this);
	
	public static SpriteSheet spriteSheet = new SpriteSheet("data/players/Player.png", 16, 16);
	public static ArrayList<ImageClass> ImageArray = new ArrayList<ImageClass>();
	
	public static AnatomyStructEl[][] Anatomy = new AnatomyStructEl[][]{
		{new AnatomyStructEl("Nothing","Nothing")}};
	
	{
		/*super.Animation =  new AnimationClass(0, "Player");
		Animation.createAnimation(0, "Back");
		AnimationClass Back = Animation.getAnimation(0);
		Back.addFrame(ImageArray.get(0), 200);
		Back.addFrame(ImageArray.get(1), 200);
		Back.addFrame(ImageArray.get(2), 200);
		Back.addFrame(ImageArray.get(1), 200);
		Back.addFrame(ImageArray.get(0), 200);
		Back.addFrame(ImageArray.get(3), 200);
		Back.addFrame(ImageArray.get(4), 200);
		Back.addFrame(ImageArray.get(3), 200);
		
		Animation.createAnimation(1, "Forward");
		AnimationClass Forward = Animation.getAnimation(1);
		Forward.addFrame(ImageArray.get(5), 200);
		Forward.addFrame(ImageArray.get(6), 200);
		Forward.addFrame(ImageArray.get(7), 200);
		Forward.addFrame(ImageArray.get(6), 200);
		Forward.addFrame(ImageArray.get(5), 200);
		Forward.addFrame(ImageArray.get(8), 200);
		Forward.addFrame(ImageArray.get(9), 200);
		Forward.addFrame(ImageArray.get(8), 200);
		
		Animation.createAnimation(2, "Left");
		AnimationClass Left = Animation.getAnimation(2);
		Left.addFrame(ImageArray.get(10), 250);
		Left.addFrame(ImageArray.get(11), 250);
		Left.addFrame(ImageArray.get(13), 250);
		Left.addFrame(ImageArray.get(10), 250);
		Left.addFrame(ImageArray.get(12), 250);
		
		Animation.createAnimation(3, "Right");
		AnimationClass Right = Animation.getAnimation(3);
		Right.addFrame(ImageArray.get(14), 250);
		Right.addFrame(ImageArray.get(15), 250);
		Right.addFrame(ImageArray.get(17), 250);
		Right.addFrame(ImageArray.get(14), 250);
		Right.addFrame(ImageArray.get(16), 250);
		Animation.pickAnimation(3);
		
		Animation.createTagAnimation(0, "Attack");
		TagAnimation Attack = Animation.getTagAnimation(0);
		Attack.addFrame(ImageArray.get(18), 250);
		Attack.addFrame(ImageArray.get(19), 250);
		Attack.addFrame(ImageArray.get(20), 250);*/
	}
	
	public static void initObject() {
		/*String sIM1   = new String("{s=0.35f, 0.34f, 0.0f,; a=180.0f; r=0, 1, 0,; d=-1,0,; e=Hand; em=Right;}");
		String sIM2   = new String("{s=0.63f, 0.34f, 0.0f,; a=0.0f;   r=0, 1, 0,; d=1,0,;  e=Hand; em=Left;}");
		String sIM2S2 = new String("{s=0.35f, 0.38f, 0.0f,; a=180.0f; r=0, 1, 0,; d=-1,0,; e=Hand; em=Right;}");
		String sIM1S2 = new String("{s=0.63f, 0.38f, 0.0f,; a=0.0f;   r=0, 1, 0,; d=1,0,;  e=Hand; em=Left;}");
		ImageArray.add(new ImageClass(spriteSheet.getSprite(1, 0)).setTags(new StringVars("t["+sIM1   + sIM2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(2, 0)).setTags(new StringVars("t["+sIM1   + sIM1S2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(3, 0)).setTags(new StringVars("t["+sIM1   + sIM1S2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(4, 0)).setTags(new StringVars("t["+sIM2S2 + sIM2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(5, 0)).setTags(new StringVars("t["+sIM2S2 + sIM2+"]")));
		
		String s2IM1   = new String("{s=0.63f, 0.34f, 0.0f,; a=0.0f;   r=0, 0, 1,; d=1,0,;  e=Hand; em=Right;}");
		String s2IM2   = new String("{s=0.35f, 0.34f, 0.0f,; a=180.0f; r=0, 1, 0,; d=-1,0,; e=Hand; em=Left;}");
		String s2IM2S2 = new String("{s=0.63f, 0.38f, 0.0f,; a=0.0f;   r=0, 0, 1,; d=1,0,;  e=Hand; em=Right;}");
		String s2IM1S2 = new String("{s=0.35f, 0.38f, 0.0f,; a=180.0f; r=0, 1, 0,; d=-1,0,; e=Hand; em=Left;}");
		ImageArray.add(new ImageClass(spriteSheet.getSprite(1, 1)).setTags(new StringVars("t["+s2IM1   + s2IM2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(2, 1)).setTags(new StringVars("t["+s2IM1   + s2IM1S2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(3, 1)).setTags(new StringVars("t["+s2IM1   + s2IM1S2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(4, 1)).setTags(new StringVars("t["+s2IM2S2 + s2IM2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(5, 1)).setTags(new StringVars("t["+s2IM2S2 + s2IM2+"]")));
		
		String s3IM1   = new String("{s=0.46f, 0.4f, 0.0f,;  a=180.0f; r=0, 1, 0,; d=-1,0,; e=Hand; em=Right;}");
		String s3IM2   = new String("{s=0.45f, 0.32f, 0.0f,; a=180.0f; r=0, 1, 0,; d=-1,0,; e=Hand; em=Left;}");
		String s3IM2S2 = new String("{s=0.46f, 0.45f, 0.0f,; a=180.0f; r=0, 1, 0,; d=-1,0,; e=Hand; em=Right;}");
		String s3IM1S2 = new String("{s=0.45f, 0.37f, 0.0f,; a=180.0f; r=0, 1, 0,; d=-1,0,; e=Hand; em=Left;}");
		ImageArray.add(new ImageClass(spriteSheet.getSprite(1, 2)).setTags(new StringVars("t["+s3IM1   + s3IM2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(2, 2)).setTags(new StringVars("t["+s3IM1S2 + s3IM2S2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(3, 2)).setTags(new StringVars("t["+s3IM1   + s3IM2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(4, 2)).setTags(new StringVars("t["+s3IM1S2 + s3IM2S2+"]")));
		
		String s4IM1   = new String("{s=0.51f, 0.32f, 0.0f,; a=0.0f; r=0, 0, 1,; d=1,0,; e=Hand; em=Right;}");
		String s4IM2   = new String("{s=0.5f,  0.4f,  0.0f,; a=0.0f; r=0, 0, 1,; d=1,0,; e=Hand; em=Left;}");
		String s4IM2S2 = new String("{s=0.51f, 0.37f, 0.0f,; a=0.0f; r=0, 0, 1,; d=1,0,; e=Hand; em=Right;}");
		String s4IM1S2 = new String("{s=0.5f,  0.45f, 0.0f,; a=0.0f; r=0, 0, 1,; d=1,0,; e=Hand; em=Left;}");
		ImageArray.add(new ImageClass(spriteSheet.getSprite(1, 3)).setTags(new StringVars("t["+s4IM1   + s4IM2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(2, 3)).setTags(new StringVars("t["+s4IM1S2 + s4IM2S2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(3, 3)).setTags(new StringVars("t["+s4IM1   + s4IM2+"]")));
		ImageArray.add(new ImageClass(spriteSheet.getSprite(4, 3)).setTags(new StringVars("t["+s4IM1S2 + s4IM2S2+"]")));
		
		String s5IM1   = new String("{s=0.0f, 0.0f, 0.0f,; a=5.0f;   r=0, 0, 1,; d=1,0,; e=Hand; em=Right;}");
		String s5IM1S1 = new String("{s=0.0f, 0.0f, 0.0f,; a=5.0f;   r=0, 0, 1,; d=1,0,; e=Hand; em=Left;}");
		String s5IM2   = new String("{s=0.0f, 0.1f, 0.0f,; a=18.0f;  r=0, 0, 1,; d=1,0,; e=Hand; em=Right;}");
		String s5IM2S2 = new String("{s=0.0f, 0.1f, 0.0f,; a=18.0f;  r=0, 0, 1,; d=1,0,; e=Hand; em=Left;}");
		String s5IM3   = new String("{s=0.0f, 0.1f, 0.0f,; a=-45.0f; r=0, 0, 1,; d=1,0,; e=Hand; em=Right;}");
		String s5IM3S2 = new String("{s=0.0f, 0.1f, 0.0f,; a=-45.0f; r=0, 0, 1,; d=1,0,; e=Hand; em=Left;}");
		ImageArray.add(new ImageClass().setTags(new StringVars("t["+s5IM1 + s5IM1S1+"]")));
		ImageArray.add(new ImageClass().setTags(new StringVars("t["+s5IM2 + s5IM2S2+"]")));
		ImageArray.add(new ImageClass().setTags(new StringVars("t["+s5IM3 + s5IM3S2+"]"))); */
		
		EnemiesArray.add(ZombieClass.ObjectTypeID);
	}

	PlayerClass(float PosGlobalX, float PosGlobalY, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, OwnedChunkID, OwnedMapID, ObjectTypes.Player, ObjectID, PlayerClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new PlayerControllerAct(this));
		super.ActionsArray.add(new PickingSystem(this, null, true));
		super.ActionsArray.add(new AnatomySystem(this, Anatomy, null));
		super.ActionsArray.add(new InventorySystem(this, 3, 5, 2.0f, 2.0f, null));
		super.ActionsArray.add(new EquipmentSystem(this, this.Modifiers.pAnatomySystem, 6, 6, this.Modifiers.pInventorySystem.Invntory));
		super.ActionsArray.add(new LookingSystem(this, 0.0f, 1.0f, 45.0f, 7.5f, 0.1f));
		super.ActionsArray.add(new ShadowsSystem(this));
		super.ActionsArray.add(new BattleSystem(this, null, null, 100, 100, EnemiesArray).linkPlayerGUI(PlayerGUI));
		super.ActionsArray.add(new CollisionSystem(this, 0.25f, 0.0f, 0.0f));
	}

	public void updateObject() {
		super.Animation.updateFrame();
		super.updateObject();
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad, super.Animation.getCurrentImage());
	}

	public void rendObject(float GlobalPosX, float GlobalPosY, QuadClass Quad) {
		super.rendObject(GlobalPosX, GlobalPosY, Quad, super.Animation.getCurrentImage());
	}
}
