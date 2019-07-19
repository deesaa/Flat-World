package FlatWorld;

import java.util.ArrayList;

public class StringVars {
	String mainString;
	
	public StringVars() {
		this.mainString = new String();
	}
	public StringVars(String mainString) {
		this.mainString = mainString;
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
		start = mainString.indexOf("=", start);
		int end   = mainString.indexOf(";", start);
		return mainString.substring(start+1, end);
	}

	public String[] getStringVals(String name) {
		ArrayList<String> tempArray = new ArrayList<String>();
		int start = mainString.indexOf(name);
		start = mainString.indexOf("=", start);
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
}
