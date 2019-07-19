package FlatWorld;

import java.util.ArrayList;

public class StringVars {
	private String mainString;
	//private String bindedArray, ;
	
	public StringVars() {
		this.mainString = new String();
	}
	public StringVars(String mainString) {
		this.mainString = mainString;
	}
	
	public void setString(String string){
		this.mainString = string;
	}
	
	public void addVar(String name, String value){
		mainString += (name + "=" + value + ";");
	}
	
	public void addVarsArray(String name, String[] value){
		mainString += (name + "=");
		for(int i = 0; i != value.length; i++){
			if(i+1 != value.length)
				mainString += (value[i] + ",");
			else
				mainString += (value[i]);
		}
		mainString += ";";
	}
	
	public String getVal(String name){
		int start = mainString.indexOf(name);
		if(start == -1){
			System.out.println("getVal(): Value \'" + name + "\' not found");
			return null;
		}
		start = mainString.indexOf("=", start);
		int end   = mainString.indexOf(";", start);
		return mainString.substring(start+1, end);
	}

	public String[] getArrayVals(String name) {
		ArrayList<String> tempArray = new ArrayList<String>();
		int start = mainString.indexOf(name+"=");
		if(start == -1){
			System.out.println("getArrayVals(): Array \'" + name + "\' not found");
			return null;
		}
		start += name.length();
		int finalEnd = mainString.indexOf(";", start), end;
		for(;;){
			end = mainString.indexOf(",", start+1);
			tempArray.add(mainString.substring(start+1, end));
			start = end;
			if(start+1 == finalEnd)
				break;
		}
		String[] newString = new String[tempArray.size()];
		tempArray.toArray(newString);
		return newString;
	}
	
	public int getStructNumElements(String name) {
		int start = mainString.indexOf(name + "[");
		if(start == -1){
			System.out.println("getStructNumElements(): Struct \'" + name + "\' not found");
			return -1;
		}
		int end   = mainString.indexOf("]", start);
		
		int numElements = 0; 
		for(;;){
			int tStart = mainString.indexOf("{", start+1);
			int tEnd = mainString.indexOf("}", tStart+1);
			numElements++;
			if(end <= tEnd+1){
				break;
			}
			start = tStart;
		}
		return numElements;
	}
	
	public StringVars getStructElement(String name, int index) {
		int start = mainString.indexOf(name + "[");
		if(start == -1){
			System.out.println("getStructElement(): Struct \'" + name + "\' not found");
			return null;
		}
		
		int end   = mainString.indexOf("]", start);
		int tStart = 0, tEnd = 0; 
		
		for(int i = 0; i != index; i++){
			tStart = mainString.indexOf("{", start+1);
			tEnd = mainString.indexOf("}", tStart+1);
			start = tStart;
		}
		return new StringVars(mainString.substring(tStart+1, tEnd));
	}
	
}
