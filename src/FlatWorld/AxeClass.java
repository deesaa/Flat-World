package FlatWorld;

import java.util.ArrayList;


public class AxeClass extends BasicObjectClass{
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static ImageClass CellTexture;
	public static ArrayList<DestructMaterialRatio> DesRatMats = new ArrayList<DestructMaterialRatio>();
//	public static StringVars EqipmentPlaces = new StringVars("EPPl=Hand,;");
	{
		super.Animation = new AnimationClass(0, "Axe");
		super.Animation.addFrame(CellTexture, 300);
		super.Animation.pickAnimation();
	}
	
	public static void initObject() {
		//String sIM1   = new String("{s=0.0f, 0.0f, 0.0f,; a=0.0f; r=0, 0, 1,; d=0,0,; e=Hand; em=NULL;}");
		//CellTexture = new ImageClass("data/Objects/Axe.png").setTags(new StringVars("t["+ sIM1 +"]"));
		DesRatMats.add(new DestructMaterialRatio(MaterialClass.Wood, 10));
	}

	AxeClass(float PosGlobalX, float PosGlobalY, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, OwnedChunkID, OwnedMapID, ObjectTypes.Object, ObjectID, AxeClass.ObjectTypeID, false, true);
		new PickableModif(this);
	//	super.ActionsArray.add(new EquipmentSystem(this, EqipmentPlaces));
		super.ActionsArray.add(new BattleObjectAct(this, BattleObjectClass.standardAxe));
		super.ActionsArray.add(new DestructionSystem(this, DesRatMats));
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad, CellTexture);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, QuadClass Quad) {
		super.rendObject(tPosGlobalX, tPosGlobalY, Quad, CellTexture);
	}

	/*public BasicObjectClass randomize() {
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
	}*/
}
