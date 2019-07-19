package FlatWorld;

import org.lwjgl.opengl.GL11;


public class AxeClass extends BasicObjectClass{
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static FlaggedImage CellTexture;
	public static StringVars EqipmentPlaces = new StringVars("EPPl=Hand,;");
	{
		super.Animations = new AnimationsList("axe");
		super.Animations.addAnimationImage(CellTexture, 300);
	}
	
	public static void initObject() {
		CellTexture = new FlaggedImage("data/Objects/Axe.png", GL11.GL_NEAREST);
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.0f, 0.0f, 0.0f, 0.0f, 0, 0, 1).linkTo("EP=Hand;"));
	}

	AxeClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, 0.0f, ObjectID, AxeClass.ObjectTypeID, false, true);
		new PickableModif(this);
		super.ActionsArray.add(new EquipmentSystem(this, EqipmentPlaces));
		super.ActionsArray.add(new BattleObjectAct(this, BattleObjectClass.standardAxe));
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, Quad);
	}

	public BasicObjectClass randomize() {
		if(RandomizeTool.setColor(this, ColorClass.Red, -40, 50, 0.03f)){
			RandomizeTool.setBattleObject(this, BattleObjectClass.standardAxe, BattleObjectClass.rareRedAxeMin, BattleObjectClass.rareRedAxeMax, 0.6f);
		} else {
		if(RandomizeTool.setColor(this, ColorClass.Blue, -40, 60, 0.07f)){
			RandomizeTool.setBattleObject(this, BattleObjectClass.standardAxe, BattleObjectClass.commonBlueAxeMin, BattleObjectClass.commonBlueAxeMax, 0.7f);
		} else {
		if(RandomizeTool.setColor(this, ColorClass.Green, -40, 60, 0.13f)){
			RandomizeTool.setBattleObject(this, BattleObjectClass.standardAxe, BattleObjectClass.commonGreenAxeMin, BattleObjectClass.commonGreenAxeMax, 0.9f);
		}}}
		return this;
	}
}
