package FlatWorld;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.lwjgl.opengl.GL20;


public class ShaderClass {
	private int shaderProgramID;
	
	public ShaderClass(String vertexShaderName, String fragShaderName) {
		shaderProgramID = GL20.glCreateProgram();
		
		int ShaderObj = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
	    int FragsObj = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	     
	    String vertexShader = null;
	    String fragsShader = null;
	    try {
	    	vertexShader = ShaderClass.readFile(vertexShaderName, StandardCharsets.UTF_8);
	    	fragsShader = ShaderClass.readFile(fragShaderName, StandardCharsets.UTF_8);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
	    GL20.glShaderSource(ShaderObj, vertexShader);
	    GL20.glShaderSource(FragsObj, fragsShader);
	     
	     
	    GL20.glCompileShader(ShaderObj);
	    System.out.println(GL20.glGetShaderInfoLog(ShaderObj, 512)+"****************");
	     
	    GL20.glCompileShader(FragsObj);
	    System.out.println(GL20.glGetShaderInfoLog(FragsObj, 512)+"****************");
	     
	    GL20.glAttachShader(shaderProgramID, ShaderObj);
	    GL20.glAttachShader(shaderProgramID, FragsObj);
	     
	    GL20.glLinkProgram(shaderProgramID);
	    System.out.println(GL20.glGetProgramInfoLog(ShaderObj, 512)+"****************");
	     
	    GL20.glValidateProgram(shaderProgramID);
	}
	
	public void useShader(){
		GL20.glUseProgram(shaderProgramID);
	}
	
	private static String readFile(String path, Charset encoding) 
			  throws IOException 
	{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	public void unbind() {
		GL20.glUseProgram(0);
	}

	public ShaderClass setUniformVector3f(String name, float x, float y, float z) {
		this.useShader();
		int var = GL20.glGetUniformLocation(shaderProgramID, name);
		GL20.glUniform3f(var, x, y, z);
		this.unbind();
		return this;
	}

	public ShaderClass setUniformVector4f(String name, float x, float y, float z, float w) {
		this.useShader();
		int var = GL20.glGetUniformLocation(shaderProgramID, name);
		GL20.glUniform4f(var, x, y, z, w);
		this.unbind();
		return this;
	} 
}
