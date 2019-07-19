package FlatWorld;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;

public class TextRenderModule {
	
	static Map<String, FontClass> fonts = new Hashtable<String, FontClass>();

	ArrayList<TextElement> etext = new ArrayList<TextElement>();
	
	public TextRenderModule(LuaValue luaValue) {
		for(int i = 1; i < luaValue.length()+1; i++){
			etext.add(new TextElement(luaValue.get(i)));
		}
	}
	
	public TextRenderModule() {}

	public void rend(float xPos, float yPos, float layerDepth){
		float sumShift = 0;
		for(int i = 0; i != etext.size(); i++){
			GL11.glTranslatef(xPos + sumShift, yPos, layerDepth);
			TextElement currenText = etext.get(i);
			QuadClass currentQuad = currenText.quad;
			for (int i2 = 0; i2 != currenText.text.length(); i2++) {
				try 
				{
					float shift = currenText.font.symbols.get(String.valueOf(currenText.text.charAt(i2))).symbolLenght + currenText.correction;
					if (currenText.text.charAt(i2) != ' ') // ≈сли это пробел, то просто ничеого не выводим
						currenText.font.symbols.get(String.valueOf(currenText.text.charAt(i2))).rendSymbol(currentQuad, currenText.color);
					GL11.glTranslatef(shift, 0, 0);
					sumShift += shift;
					
				} catch (NullPointerException exception) {
					//System.out.println("Symbol " + string.charAt(i) + " not found");
				}
			}
			GL11.glLoadIdentity();
		}
	}

	public static void initSymbols() {
		File files = new File("data/symbols/fonts");
		File[] gg = files.listFiles();
		for(int i = 0; i < gg.length; i++){
			LuaValue luaTempFile = ObjectsLoader.luaOf(gg[i]);
			if(luaTempFile != null){
				LuaValue luaFont = luaTempFile.get("Font");
				if(!luaFont.isnil()){
					fonts.put(luaFont.get("Name").tojstring(), new TextRenderModule().new FontClass(luaFont));
				}
			}
		}
	
		/*TextRenderModule.Symbols.put('A', new SymbolClass("data/symbols/A.png", 0.6f, 'A'));
		TextRenderModule.Symbols.put('B', new SymbolClass("data/symbols/B.png", 0.6f, 'B'));
		TextRenderModule.Symbols.put('C', new SymbolClass("data/symbols/C.png", 0.6f, 'C'));
		TextRenderModule.Symbols.put('D', new SymbolClass("data/symbols/D.png", 0.2f, 'D'));
		TextRenderModule.Symbols.put('E', new SymbolClass("data/symbols/E.png", 0.2f, 'E'));
		TextRenderModule.Symbols.put('F', new SymbolClass("data/symbols/F.png", 0.2f, 'F'));
		TextRenderModule.Symbols.put('G', new SymbolClass("data/symbols/G.png", 0.2f, 'G'));
		TextRenderModule.Symbols.put('H', new SymbolClass("data/symbols/H.png", 0.2f, 'H'));
		TextRenderModule.Symbols.put('I', new SymbolClass("data/symbols/I.png", 0.2f, 'I'));
		TextRenderModule.Symbols.put('J', new SymbolClass("data/symbols/J.png", 0.2f, 'J'));
		TextRenderModule.Symbols.put('K', new SymbolClass("data/symbols/K.png", 0.2f, 'K'));
		TextRenderModule.Symbols.put('L', new SymbolClass("data/symbols/L.png", 0.2f, 'L'));
		TextRenderModule.Symbols.put('M', new SymbolClass("data/symbols/M.png", 0.2f, 'M'));
		TextRenderModule.Symbols.put('N', new SymbolClass("data/symbols/N.png", 0.2f, 'N'));
		TextRenderModule.Symbols.put('O', new SymbolClass("data/symbols/O.png", 0.2f, 'O'));
		TextRenderModule.Symbols.put('P', new SymbolClass("data/symbols/P.png", 0.2f, 'P'));
		TextRenderModule.Symbols.put('Q', new SymbolClass("data/symbols/Q.png", 0.2f, 'Q'));
		TextRenderModule.Symbols.put('R', new SymbolClass("data/symbols/R.png", 0.2f, 'R'));
		TextRenderModule.Symbols.put('S', new SymbolClass("data/symbols/S.png", 0.2f, 'S'));
		TextRenderModule.Symbols.put('T', new SymbolClass("data/symbols/T.png", 0.2f, 'T'));
		TextRenderModule.Symbols.put('U', new SymbolClass("data/symbols/U.png", 0.2f, 'U'));
		TextRenderModule.Symbols.put('V', new SymbolClass("data/symbols/V.png", 0.2f, 'V'));
		TextRenderModule.Symbols.put('W', new SymbolClass("data/symbols/W.png", 0.2f, 'W'));
		TextRenderModule.Symbols.put('X', new SymbolClass("data/symbols/X.png", 0.2f, 'X'));
		TextRenderModule.Symbols.put('Y', new SymbolClass("data/symbols/Y.png", 0.2f, 'Y'));
		TextRenderModule.Symbols.put('Z', new SymbolClass("data/symbols/Z.png", 0.2f, 'Z'));
		
		TextRenderModule.Symbols.put('a', new SymbolClass("data/symbols/LA.png", 0.2f, 'a'));
		TextRenderModule.Symbols.put('b', new SymbolClass("data/symbols/LB.png", 0.2f, 'b'));
		TextRenderModule.Symbols.put('c', new SymbolClass("data/symbols/LC.png", 0.2f, 'c'));
		TextRenderModule.Symbols.put('d', new SymbolClass("data/symbols/LD.png", 0.2f, 'd'));
		TextRenderModule.Symbols.put('e', new SymbolClass("data/symbols/LE.png", 0.2f, 'e'));
		TextRenderModule.Symbols.put('f', new SymbolClass("data/symbols/LF.png", 0.2f, 'f'));
		TextRenderModule.Symbols.put('g', new SymbolClass("data/symbols/LG.png", 0.2f, 'g'));
		TextRenderModule.Symbols.put('h', new SymbolClass("data/symbols/LH.png", 0.2f, 'h'));
		TextRenderModule.Symbols.put('i', new SymbolClass("data/symbols/LI.png", 0.2f, 'i'));
		TextRenderModule.Symbols.put('j', new SymbolClass("data/symbols/LJ.png", 0.2f, 'j'));
		TextRenderModule.Symbols.put('k', new SymbolClass("data/symbols/LK.png", 0.2f, 'k'));
		TextRenderModule.Symbols.put('l', new SymbolClass("data/symbols/LL.png", 0.2f, 'l'));
		TextRenderModule.Symbols.put('m', new SymbolClass("data/symbols/LM.png", 0.2f, 'm'));
		TextRenderModule.Symbols.put('n', new SymbolClass("data/symbols/LN.png", 0.2f, 'n'));
		TextRenderModule.Symbols.put('o', new SymbolClass("data/symbols/LO.png", 0.2f, 'o'));
		TextRenderModule.Symbols.put('p', new SymbolClass("data/symbols/LP.png", 0.2f, 'p'));
		TextRenderModule.Symbols.put('q', new SymbolClass("data/symbols/LQ.png", 0.2f, 'q'));
		TextRenderModule.Symbols.put('r', new SymbolClass("data/symbols/LR.png", 0.2f, 'r'));
		TextRenderModule.Symbols.put('s', new SymbolClass("data/symbols/LS.png", 0.2f, 's'));
		TextRenderModule.Symbols.put('t', new SymbolClass("data/symbols/LT.png", 0.2f, 't'));
		TextRenderModule.Symbols.put('u', new SymbolClass("data/symbols/LU.png", 0.2f, 'u'));
		TextRenderModule.Symbols.put('v', new SymbolClass("data/symbols/LV.png", 0.2f, 'v'));
		TextRenderModule.Symbols.put('w', new SymbolClass("data/symbols/LW.png", 0.2f, 'w'));
		TextRenderModule.Symbols.put('x', new SymbolClass("data/symbols/LX.png", 0.2f, 'x'));
		TextRenderModule.Symbols.put('y', new SymbolClass("data/symbols/LY.png", 0.2f, 'y'));
		TextRenderModule.Symbols.put('z', new SymbolClass("data/symbols/LZ.png", 0.2f, 'z'));
		TextRenderModule.Symbols.put(' ', new SymbolClass(null, -0.2f, ' ')); */
	}
	
	public static void rendText(LuaValue text, float posGlobalX, float posGlobalY, float posGlobalZ, QuadClass Quad, float CorrectionIndent) {
		GL11.glTranslatef(posGlobalX, posGlobalY, posGlobalZ);
		
		GL11.glLoadIdentity();
	}
	
	public static float getTextWidth(String string, float CorrectionIndent){
		float width = 0;
		for (int i = 0; i != string.length(); i++) {
			try 
			{
				//width += Symbols.get(string.charAt(i)).symbolLenght+CorrectionIndent;
			} catch (NullPointerException exception) {
				System.out.println("Symbol " + string.charAt(i) + " not found");
			}
		}
		return Math.abs(width);
	}
	
	class FontClass{
		SpriteSheet symbolsSheet;
		Map<String, SymbolClass> symbols = new Hashtable<String, SymbolClass>();
		
		public FontClass(LuaValue luaFont) {
			symbolsSheet = new SpriteSheet(luaFont.get("SpriteSheet"));
			int currentX = 0, currentY = 0;
			for(int i = 1; i < luaFont.length()+1; i++){
				symbols.put(luaFont.get(i).get(1).tojstring(), new SymbolClass(symbolsSheet.getSprite(currentX, currentY), luaFont.get(i).get(2).tofloat()));
				currentX++;
				if(currentX == symbolsSheet.numTitlesInLine){
					currentX = 0;
					currentY++;
				}
			}
		}
	}
	
	class TextElement{
		FontClass font;
		UniteColorClass color;
		String text;
		QuadClass quad;
		float correction;

		public TextElement(LuaValue luaValue) {
			font = TextRenderModule.fonts.get(luaValue.get("Font").tojstring());
			color = UniteColorClass.createLuaColor(luaValue.get("Color"));
			text = luaValue.get("Text").tojstring();
			quad = new QuadClass(luaValue.get("Quad"));
			correction = luaValue.get("Correction").tofloat();
		}
	}
}
