package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class ContainersArrayClass {
	public ArrayList<ContainerCell> InventoryCellsArray = new ArrayList<ContainerCell>();
	int numCellsInLine, numLines;
	float indentX, indentY;
	float lenght, height;
	
	TexturesClass backgroundTexture;
	float BGExpandUp, BGExpandDown, BGExpandRight, BGExpandLeft;
	
	public ContainersArrayClass(int numCellsInLine, int numLines, float indentX, float indentY, TexturesClass backgroundTexture,
			float BGExpandUp, float BGExpandDown, float BGExpandRight, float BGExpandLeft){
		this.numCellsInLine = numCellsInLine;
		this.numLines = numLines;
		this.indentX  = indentX;
		this.indentY  = indentY;
		
		this.backgroundTexture = backgroundTexture;
		this.BGExpandUp    = BGExpandUp;
		this.BGExpandDown  = -BGExpandDown;
		this.BGExpandRight = BGExpandRight;
		this.BGExpandLeft  = -BGExpandLeft;
		
		for (int i = 0; i != numCellsInLine; i++) {
			for (int i2 = 0; i2 != numLines; i2++) {
				InventoryCellsArray.add(new ContainerCell(0.0f+i+indentX-0.0001f, 0.0f+i2+indentY-0.0001f, -25.0f, 0, 0, InventoryCellsArray.size()));
			}
		}
		lenght = (numCellsInLine*FlatWorld.StandardQuadWidth)+(numCellsInLine*indentX);
		height = (numLines*FlatWorld.StandardQuadHeight)+(numLines*indentY);
	}
	
	public void addContainer(float CellPosX, float CellPosY){
		InventoryCellsArray.add(new ContainerCell(CellPosX, CellPosY, -25.0f, 0, 0, InventoryCellsArray.size()));
	}
	
	public ContainerCell getContainerUnderArrow(){
		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			ContainerCell tempCell = InventoryCellsArray.get(i);
			if (tempCell.PosGlobalX   								< MouseArrowClass.ArrowWorldCoordX &&
				tempCell.PosGlobalX + FlatWorld.StandardQuadHeight  > MouseArrowClass.ArrowWorldCoordX &&
				tempCell.PosGlobalY 						        < MouseArrowClass.ArrowWorldCoordY &&
				tempCell.PosGlobalY + FlatWorld.StandardQuadWidth   > MouseArrowClass.ArrowWorldCoordY)
			{
				return tempCell;
			}
		}
		return null;
	}
	
	public void mouseTransfer(int ContainerID){
		MouseArrowClass.addContainer(InventoryCellsArray.get(ContainerID));
				
		int size = InventoryCellsArray.get(ContainerID).ObjectsArray.size();
		if(size > 0){
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
				for (int i2 = 0; i2 < size; i2++) {
					MouseArrowClass.addObject(InventoryCellsArray.get(ContainerID).ObjectsArray.get(InventoryCellsArray.get(ContainerID).ObjectsArray.size() - 1));
					InventoryCellsArray.get(ContainerID).ObjectsArray.remove(InventoryCellsArray.get(ContainerID).ObjectsArray.size() - 1);
				}
			} else {
				MouseArrowClass.addObject(InventoryCellsArray.get(ContainerID).ObjectsArray.get(InventoryCellsArray.get(ContainerID).ObjectsArray.size() - 1));
				InventoryCellsArray.get(ContainerID).ObjectsArray.remove(InventoryCellsArray.get(ContainerID).ObjectsArray.size() - 1);
			}
		}
				
		if (InventoryCellsArray.get(ContainerID).ObjectsArray.size() == 0)
			InventoryCellsArray.get(ContainerID).pickedObjectTypeID = -1;
	}
	
	public boolean addObject(BasicObjectClass pickedObject) {
		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			if (InventoryCellsArray.get(i).pickedObjectTypeID == pickedObject.ObjectTypeID) {
				InventoryCellsArray.get(i).addObject(pickedObject);
				pickedObject.zeroObject();
				MapsManager.deleteObject(pickedObject.OwnedMapID, pickedObject.OwnedChunkID, pickedObject.ObjectID);
				return true;
			}
		}
		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			if (InventoryCellsArray.get(i).pickedObjectTypeID == -1) {
				InventoryCellsArray.get(i).addObject(pickedObject);
				pickedObject.zeroObject();
				MapsManager.deleteObject(pickedObject.OwnedMapID, pickedObject.OwnedChunkID, pickedObject.ObjectID);
				return true;
			}
		}
		return false;
	}
	
	public void rendObjectOver(int ObjectTypeID, int ContainerID, float indentX, float indentY, int quadType){
		ContainerCell tempContainer = InventoryCellsArray.get(ContainerID);
		FlatWorld.StaticObjectsBase.rendObject(ObjectTypeID,  
				tempContainer.PosGlobalX+indentX, tempContainer.PosGlobalY+indentY, InventoryCellsArray.get(ContainerID).PosGlobalZ, quadType);
	}
	
	public void rend(float PosX, float PosY, float PosZ){
		if(backgroundTexture != null){
			GL11.glTranslatef(PosX, PosY, PosZ);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor3b((byte) 127, (byte) 127, (byte) 127);
			
			backgroundTexture.setTextureByAnimation();

			GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex2f(0.0f+this.BGExpandLeft,  0.0f+this.BGExpandDown);
			
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex2f(lenght+this.BGExpandRight, 0.0f+this.BGExpandDown);
			
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex2f(lenght+this.BGExpandRight, height+this.BGExpandUp);
			
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex2f(0.0f+this.BGExpandLeft, height+this.BGExpandUp);
			GL11.glEnd();
			GL11.glLoadIdentity();
		}
		
		for (int i = 0; i != InventoryCellsArray.size(); i++) {
			InventoryCellsArray.get(i).rendObject(PosX, PosY, PosZ, FlatWorld.StandardQuad);
			InventoryCellsArray.get(i).rendCellContent();
			InventoryCellsArray.get(i).rendCellContentCounter();
		}
	}
}