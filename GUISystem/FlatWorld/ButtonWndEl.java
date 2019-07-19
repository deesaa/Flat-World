package FlatWorld;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;

enum ButtonState{Pressed, Unpressed};

public class ButtonWndEl extends WindowElement{
	public static int clickedButtonID = -1;
	ButtonState bs = ButtonState.Unpressed;
	Image currentImage;
	Image unpressed, pressed, aimed;
	String text;
	WinPos texLoc;
	QuadClass textQuad = QuadClass.menuSymbols;
	
	ButtonWndEl(int leftTopVertexX, int leftTopVertexY, int rightBottomVertexX, int rightBottomVertexY, int elID, String text){
		super(leftTopVertexX, leftTopVertexY, rightBottomVertexX, rightBottomVertexY, elID);
		this.text = text;
	}
	
	public void rend(int LTXShift, int LTYShift, int RBXShift, int RBYShift){
		if(unpressed != null && pressed != null){
			currentImage.bind();
			super.rend(LTXShift, LTYShift, RBXShift, RBYShift);
		} else {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			super.rend(LTXShift, LTYShift, RBXShift, RBYShift);
		}

		float textWidth = TextRenderModule.getTextWidth(text, 3.1f);
		float texHeight = Math.abs(super.finalLTY-super.finalRBY)-textQuad.height;
		if(bs == ButtonState.Pressed){
			texHeight += Math.abs((finalLTY-finalRBY)/8);
			textWidth -= Math.abs((finalRBX-finalLTX)/32);
		}
		TextRenderModule.rendText(text, finalLTX+(((finalRBX-finalLTX)-textWidth)*0.5f), finalLTY+texHeight*0.5f, -99, this.textQuad, 3.1f);
	}
	
	public ButtonWndEl setTextures(Image unpressed, Image pressed){
		this.unpressed = unpressed;
		this.pressed   = pressed;
		return this;
	}
	
	public void update(MessagesHandler messagesHandler){
		if (super.finalLTX	< MouseArrowClass.ArrowWorldCoordX &&
			super.finalRBX  > MouseArrowClass.ArrowWorldCoordX &&
			super.finalLTY	< MouseArrowClass.ArrowWorldCoordY &&
			super.finalRBY  > MouseArrowClass.ArrowWorldCoordY)
		{
			if(this.elID != clickedButtonID)
				clickedButtonID = -1;
			
			//if(aimed != null)
			//	currentImage = aimed;
			//else
				//super.modifColor = ColorClass.Green;
			
			if(!Mouse.isButtonDown(0) && clickedButtonID != -1){
				messagesHandler.messagesHandler(this, "Clicked");
			}
			if(Mouse.isButtonDown(0)){
				ButtonWndEl.clickedButtonID = elID;
				if(pressed != null){
					currentImage = pressed;
					bs = ButtonState.Pressed;
				}
				else
					super.modifColor = ColorClass.Red;
			}
		} else {
			if(this.elID == clickedButtonID)
				clickedButtonID = -1;
			
			if(unpressed != null){
				currentImage = unpressed;
				bs = ButtonState.Unpressed;
			}
			else
				super.modifColor = ColorClass.Standard;
		}
	}

	public WindowElement setTextSetts(QuadClass quadClass, WinPos texLoc) {
		this.texLoc = texLoc;
		this.textQuad = quadClass;
		return this;
	}
}
