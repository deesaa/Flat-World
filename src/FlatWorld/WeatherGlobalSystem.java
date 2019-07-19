package FlatWorld;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

public class WeatherGlobalSystem {
	Vector4f SunLightMax = new Vector4f(5.0f, 5.0f, 5.0f, 1.0f);
	Vector4f SunLightMin = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
	Vector4f SunLight    = new Vector4f();
	int milisecsPerMilisec = 100;
	int day = 86400000;
	
	int morningHourStart = 21600000, morningHourEnd = 28800000;
	int	eveningHourStart = 68400000, eveningHourEnd = 72000000;
	int dayStart         = 28800000, dayEnd         = 68400000;
	int nightStart 		 = 72000000, nightEnd 		= 21600000;
	
	int currentTime = dayStart;	//58400000
	long lastTime = 0;
	float minForAll, minRemaining, minPassed, finalSunLight;
	
	public WeatherGlobalSystem(int milisecsPerMilisec) {
		this.milisecsPerMilisec = milisecsPerMilisec;
		lastTime = System.currentTimeMillis();
	}
	
	public void update(){
		this.updateTime();
		this.updateSunLight();
		this.time();
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, FlatWorld.floatBuffer(SunLight.x, SunLight.y, SunLight.z, SunLight.w));
	}
	
	public void time(){
		int Hours = currentTime/3600000; 					
		int Minuts = (currentTime-3600000*Hours)/(60000);  
		int Seconds = (currentTime - ((Hours*3600000)+(Minuts*60000)))/1000;
	//	System.out.println(Hours + "  " + Minuts + "  " + Seconds);
	}
	
	public void updateTime(){
		long Time = System.currentTimeMillis();
		this.currentTime += (Time-lastTime)*milisecsPerMilisec;
		if(this.currentTime >= day){
			this.currentTime = 0;
		}
		lastTime = Time;
	}
	
	private void updateSunLight() {
		if(currentTime > dayStart && currentTime < dayEnd){
			SunLight.set(SunLightMax);
		}

		if(currentTime > nightStart && currentTime <= day || currentTime < nightEnd){
			SunLight.set(SunLightMin);
		}

		if(currentTime > morningHourStart && currentTime < morningHourEnd){
			minForAll = morningHourEnd-morningHourStart;
			float c = SunLightMax.x/minForAll;
			minRemaining = morningHourEnd-currentTime;
			minPassed = minForAll-minRemaining;
			finalSunLight = (float) (minPassed*c);
			SunLight.set(finalSunLight+SunLightMin.x, finalSunLight+SunLightMin.x, finalSunLight+SunLightMin.x);	
		}	
		if(currentTime > eveningHourStart && currentTime < eveningHourEnd){
			minForAll = eveningHourEnd-eveningHourStart;
			float c = SunLightMax.x/minForAll;
			minRemaining = eveningHourEnd-currentTime;
			minPassed = minForAll-minRemaining;
			finalSunLight = (float) SunLightMax.x-(minPassed*c);
			SunLight.set(finalSunLight+SunLightMin.x, finalSunLight+SunLightMin.x, finalSunLight+SunLightMin.x);	
		}
	}
}
