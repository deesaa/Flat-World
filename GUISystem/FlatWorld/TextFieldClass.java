package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class TextFieldClass {
	public static Map<Character, SymbolClass> Symbols = new Hashtable<Character, SymbolClass>(
			40, 0.9f);

	public static void initSymbols() {
		TextFieldClass.Symbols.put('0', new SymbolClass("data/symbols/0.png",
				'0'));
		TextFieldClass.Symbols.put('1', new SymbolClass("data/symbols/1.png",
				'1'));
		TextFieldClass.Symbols.put('2', new SymbolClass("data/symbols/2.png",
				'2'));
		TextFieldClass.Symbols.put('3', new SymbolClass("data/symbols/3.png",
				'3'));
		TextFieldClass.Symbols.put('4', new SymbolClass("data/symbols/4.png",
				'4'));
		TextFieldClass.Symbols.put('5', new SymbolClass("data/symbols/5.png",
				'5'));
		TextFieldClass.Symbols.put('6', new SymbolClass("data/symbols/6.png",
				'6'));
		TextFieldClass.Symbols.put('7', new SymbolClass("data/symbols/7.png",
				'7'));
		TextFieldClass.Symbols.put('8', new SymbolClass("data/symbols/8.png",
				'8'));
		TextFieldClass.Symbols.put('9', new SymbolClass("data/symbols/9.png",
				'9'));
		TextFieldClass.Symbols.put('A', new SymbolClass(
				"data/symbols/EngRusA.png", 'A'));
		TextFieldClass.Symbols.put('B', new SymbolClass(
				"data/symbols/EngRusB.png", 'B'));
		TextFieldClass.Symbols.put('C', new SymbolClass(
				"data/symbols/EngRusC.png", 'C'));
		TextFieldClass.Symbols.put('D', new SymbolClass(
				"data/symbols/EngD.png", 'D'));
		TextFieldClass.Symbols.put('E', new SymbolClass(
				"data/symbols/EngRusE.png", 'E'));
		TextFieldClass.Symbols.put('F', new SymbolClass(
				"data/symbols/EngF.png", 'F'));
		TextFieldClass.Symbols.put('G', new SymbolClass(
				"data/symbols/EngG.png", 'J'));
		TextFieldClass.Symbols.put('H', new SymbolClass(
				"data/symbols/EngRusH.png", 'H'));
		TextFieldClass.Symbols.put('I', new SymbolClass(
				"data/symbols/EngI.png", 'I'));
		TextFieldClass.Symbols.put('J', new SymbolClass(
				"data/symbols/EngJ.png", 'J'));
		TextFieldClass.Symbols.put('K', new SymbolClass(
				"data/symbols/EngRusK.png", 'K'));
		TextFieldClass.Symbols.put('L', new SymbolClass(
				"data/symbols/EngL.png", 'L'));
		TextFieldClass.Symbols.put('M', new SymbolClass(
				"data/symbols/EngRusM.png", 'M'));
		TextFieldClass.Symbols.put('N', new SymbolClass(
				"data/symbols/EngN.png", 'N'));
		TextFieldClass.Symbols.put('O', new SymbolClass(
				"data/symbols/EngRusO.png", 'O'));
		TextFieldClass.Symbols.put('P', new SymbolClass(
				"data/symbols/EngRusP.png", 'P'));
		TextFieldClass.Symbols.put('Q', new SymbolClass(
				"data/symbols/EngQ.png", 'Q'));
		TextFieldClass.Symbols.put('R', new SymbolClass(
				"data/symbols/EngR.png", 'R'));
		TextFieldClass.Symbols.put('S', new SymbolClass(
				"data/symbols/EngS.png", 'S'));
		TextFieldClass.Symbols.put('T', new SymbolClass(
				"data/symbols/EngRusT.png", 'T'));
		TextFieldClass.Symbols.put('U', new SymbolClass(
				"data/symbols/EngU.png", 'U'));
		TextFieldClass.Symbols.put('V', new SymbolClass(
				"data/symbols/EngV.png", 'V'));
		TextFieldClass.Symbols.put('W', new SymbolClass(
				"data/symbols/EngW.png", 'W'));
		TextFieldClass.Symbols.put('X', new SymbolClass(
				"data/symbols/EngRusX.png", 'X'));
		TextFieldClass.Symbols.put('Y', new SymbolClass(
				"data/symbols/EngY.png", 'Y'));
		TextFieldClass.Symbols.put('Z', new SymbolClass(
				"data/symbols/EngZ.png", 'Z'));

		TextFieldClass.Symbols.put('a', new SymbolClass(
				"data/symbols/LEngRusA.png", 'a'));
		TextFieldClass.Symbols.put('b', new SymbolClass(
				"data/symbols/LEngB.png", 'b'));
		TextFieldClass.Symbols.put('c', new SymbolClass(
				"data/symbols/LEngRusC.png", 'c'));
		TextFieldClass.Symbols.put('d', new SymbolClass(
				"data/symbols/LEngD.png", 'd'));
		TextFieldClass.Symbols.put('e', new SymbolClass(
				"data/symbols/LEngRusE.png", 'e'));
		TextFieldClass.Symbols.put('f', new SymbolClass(
				"data/symbols/LEngF.png", 'f'));
		TextFieldClass.Symbols.put('g', new SymbolClass(
				"data/symbols/LEngG.png", 'j'));
		TextFieldClass.Symbols.put('h', new SymbolClass(
				"data/symbols/LEngH.png", 'h'));
		TextFieldClass.Symbols.put('i', new SymbolClass(
				"data/symbols/LEngI.png", 'i'));
		TextFieldClass.Symbols.put('j', new SymbolClass(
				"data/symbols/LEngJ.png", 'j'));
		TextFieldClass.Symbols.put('k', new SymbolClass(
				"data/symbols/LEngRusK.png", 'k'));
		TextFieldClass.Symbols.put('l', new SymbolClass(
				"data/symbols/LEngL.png", 'l'));
		TextFieldClass.Symbols.put('m', new SymbolClass(
				"data/symbols/LEngRusM.png", 'm'));
		TextFieldClass.Symbols.put('n', new SymbolClass(
				"data/symbols/LEngN.png", 'n'));
		TextFieldClass.Symbols.put('o', new SymbolClass(
				"data/symbols/LEngRusO.png", 'o'));
		TextFieldClass.Symbols.put('p', new SymbolClass(
				"data/symbols/LEngRusP.png", 'p'));
		TextFieldClass.Symbols.put('q', new SymbolClass(
				"data/symbols/LEngQ.png", 'q'));
		TextFieldClass.Symbols.put('r', new SymbolClass(
				"data/symbols/LEngR.png", 'r'));
		TextFieldClass.Symbols.put('s', new SymbolClass(
				"data/symbols/LEngS.png", 's'));
		TextFieldClass.Symbols.put('t', new SymbolClass(
				"data/symbols/LEngT.png", 't'));
		TextFieldClass.Symbols.put('u', new SymbolClass(
				"data/symbols/LEngU.png", 'u'));
		TextFieldClass.Symbols.put('v', new SymbolClass(
				"data/symbols/LEngV.png", 'v'));
		TextFieldClass.Symbols.put('w', new SymbolClass(
				"data/symbols/LEngW.png", 'w'));
		TextFieldClass.Symbols.put('x', new SymbolClass(
				"data/symbols/LEngRusX.png", 'x'));
		TextFieldClass.Symbols.put('y', new SymbolClass(
				"data/symbols/LEngY.png", 'y'));
		TextFieldClass.Symbols.put('z', new SymbolClass(
				"data/symbols/LEngZ.png", 'z'));

		TextFieldClass.Symbols.put('!', new SymbolClass("data/symbols/!.png",
				'!'));
		TextFieldClass.Symbols.put('?', new SymbolClass(
				"data/symbols/Question.png", '?'));
		TextFieldClass.Symbols.put('(', new SymbolClass("data/symbols/(.png",
				'('));
		TextFieldClass.Symbols.put(')', new SymbolClass("data/symbols/).png",
				')'));
		TextFieldClass.Symbols.put('.', new SymbolClass(
				"data/symbols/Point.png", '.'));
	}

	public static void rendText(String string, float posGlobalX, float posGlobalY, float posGlobalZ, QuadClass quad, float bIndent) {
		float indent = bIndent;
		GL11.glTranslatef(posGlobalX, posGlobalY, posGlobalZ);
		for (int i = 0; i != string.length(); i++) {
			try 
			{
				GL11.glTranslatef(indent, 0, 0);
				if (string.charAt(i) != ' ') // ≈сли это пробел, то просто ничеого не выводим
					Symbols.get(string.charAt(i)).rendSymbol(quad, null);
			} catch (NullPointerException exception) {
				System.out.println("Symbol " + string.charAt(i) + " not found");
			}
		}
		GL11.glLoadIdentity();
	}
}
