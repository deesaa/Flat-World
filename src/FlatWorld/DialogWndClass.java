package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

public class DialogWndClass {
	float PosGlobalX, PosGlobalY, PosGlobalZ;
	public TexturesClass Textures;
	boolean isAlphaBlend = false;

	public ArrayList<DialogWndFieldClass> DlgFieldsArray = new ArrayList<DialogWndFieldClass>();
	boolean isInventoryVisible = false;
	int rightClickedCell = -1;
	int cellIDWithContur = -1;

	public DialogWndClass(String[] textsForFields) {
		for (int i = 0; i != textsForFields.length; i++) {
			DlgFieldsArray.add(new DialogWndFieldClass(textsForFields[i]));
		}
	}

	public void initDialogWnd(String[] textsForFields) {
		for (int i = 0; i != textsForFields.length; i++) {
			DlgFieldsArray.add(new DialogWndFieldClass(textsForFields[i]));
		}
	}

	public void rendDialogWnd(float posGlobalX, float posGlobalY, float posGlobalZ) {
		float indent = 0.0f;
		for (int i = 0; i != DlgFieldsArray.size(); i++) {
			DlgFieldsArray.get(i).rendField(posGlobalX, posGlobalY + indent, posGlobalZ, false);
			indent += 0.7f;
		}
	}

	public void rendButtons(float posGlobalX, float posGlobalY, float posGlobalZ) {
		float indent = 0.0f;
		for (int i = 0; i != DlgFieldsArray.size(); i++) {
			DlgFieldsArray.get(i).rendField(posGlobalX, posGlobalY + indent, posGlobalZ, true);
			indent += 0.7f;
		}
	}

	public int checkMouseController() {
		for (int i = 0; i < DlgFieldsArray.size(); i++) {
			if (DlgFieldsArray.get(i).buttonColorB == FlatWorld.colorUnderArrow .get(2) &&
				DlgFieldsArray.get(i).buttonColorG == FlatWorld.colorUnderArrow.get(1) &&
				DlgFieldsArray.get(i).buttonColorR == FlatWorld.colorUnderArrow.get(0)) 
			{
				return i;
			}
		}
		return -1;
	}
}
