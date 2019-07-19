package FlatWorld;

public class StringVars {
	String mainString = new String();
	
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
}
