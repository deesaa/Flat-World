package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.opengl.GL11;

public class TextRenderModule {
	public static Map<Character, SymbolClass> Symbols = new Hashtable<Character, SymbolClass>(
			64, 0.95f);
	
	public static void initSymbols() {
		TextFieldClass.Symbols.put('A', new SymbolClass("data/symbols/A.png", -0.2f, 'A'));
		TextFieldClass.Symbols.put('B', new SymbolClass("data/symbols/B.png", -0.2f, 'B'));
		TextFieldClass.Symbols.put('C', new SymbolClass("data/symbols/C.png", -0.2f, 'C'));
		TextFieldClass.Symbols.put('D', new SymbolClass("data/symbols/D.png", -0.2f, 'D'));
		TextFieldClass.Symbols.put('E', new SymbolClass("data/symbols/E.png", -0.2f, 'E'));
		TextFieldClass.Symbols.put('F', new SymbolClass("data/symbols/F.png", -0.2f, 'F'));
		TextFieldClass.Symbols.put('G', new SymbolClass("data/symbols/G.png", -0.2f, 'G'));
		TextFieldClass.Symbols.put('H', new SymbolClass("data/symbols/H.png", -0.2f, 'H'));
		TextFieldClass.Symbols.put('I', new SymbolClass("data/symbols/I.png", -0.2f, 'I'));
		TextFieldClass.Symbols.put('J', new SymbolClass("data/symbols/J.png", -0.2f, 'J'));
		TextFieldClass.Symbols.put('K', new SymbolClass("data/symbols/K.png", -0.2f, 'K'));
		TextFieldClass.Symbols.put('L', new SymbolClass("data/symbols/L.png", -0.2f, 'L'));
		TextFieldClass.Symbols.put('M', new SymbolClass("data/symbols/M.png", -0.2f, 'M'));
		TextFieldClass.Symbols.put('N', new SymbolClass("data/symbols/N.png", -0.2f, 'N'));
		TextFieldClass.Symbols.put('O', new SymbolClass("data/symbols/O.png", -0.2f, 'O'));
		TextFieldClass.Symbols.put('P', new SymbolClass("data/symbols/P.png", -0.2f, 'P'));
		TextFieldClass.Symbols.put('Q', new SymbolClass("data/symbols/Q.png", -0.2f, 'Q'));
		TextFieldClass.Symbols.put('R', new SymbolClass("data/symbols/R.png", -0.2f, 'R'));
		TextFieldClass.Symbols.put('S', new SymbolClass("data/symbols/S.png", -0.2f, 'S'));
		TextFieldClass.Symbols.put('T', new SymbolClass("data/symbols/T.png", -0.2f, 'T'));
		TextFieldClass.Symbols.put('U', new SymbolClass("data/symbols/U.png", -0.2f, 'U'));
		TextFieldClass.Symbols.put('V', new SymbolClass("data/symbols/V.png", -0.2f, 'V'));
		TextFieldClass.Symbols.put('W', new SymbolClass("data/symbols/W.png", -0.2f, 'W'));
		TextFieldClass.Symbols.put('X', new SymbolClass("data/symbols/X.png", -0.2f, 'X'));
		TextFieldClass.Symbols.put('Y', new SymbolClass("data/symbols/Y.png", -0.2f, 'Y'));
		TextFieldClass.Symbols.put('Z', new SymbolClass("data/symbols/Z.png", -0.2f, 'Z'));
	}
	
	public static void rendText(String string, float posGlobalX, float posGlobalY, float posGlobalZ, QuadClass Quad, float CorrectionIndent) {
		GL11.glTranslatef(posGlobalX, posGlobalY, posGlobalZ);
		for (int i = 0; i != string.length(); i++) {
			try 
			{
				if (string.charAt(i) != ' ') // ≈сли это пробел, то просто ничеого не выводим
					Symbols.get(string.charAt(i)).rendSymbol(Quad);
				GL11.glTranslatef(Symbols.get(string.charAt(i)).symbolLenght+CorrectionIndent, 0, 0);
				
			} catch (NullPointerException exception) {
				System.out.println("Symbol " + string.charAt(i) + " not found");
			}
		}
		GL11.glLoadIdentity();
	}
}
