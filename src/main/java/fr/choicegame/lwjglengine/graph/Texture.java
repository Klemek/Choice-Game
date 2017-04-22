package fr.choicegame.lwjglengine.graph;

import java.io.IOException;
import java.nio.ByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture {

	private final int textureId;
	private int width, height;
	
	public Texture(String fileName) throws IOException {
		
		//Load the texture from file
		PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(fileName));
		
		width = decoder.getWidth();
		height = decoder.getHeight();
		
		ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
		buf.flip();
		
		// Create a new OpenGL texture 
		textureId = glGenTextures();
		// Bind the texture
		glBindTexture(GL_TEXTURE_2D, textureId);
		//unpack rgba
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		//load texture
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(),
			    decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
		
		//generate set of lower-definitions
		glGenerateMipmap(GL_TEXTURE_2D);
	}
	
	public int getId(){
		return textureId;
	}
	
	public float[] getTextCoords(int id, int texSize){
		
		float wcut = (float)texSize/(float)width;
		float hcut = (float)texSize/(float)height;
		
		int wsize = Math.round(width/texSize);
		
		int x = id%wsize;
		int y = id/wsize;
		
		return new float[]{
    	        x*wcut, y*hcut,
    	        x*wcut, (y+1)*hcut,
    	        (x+1)*wcut, (y+1)*hcut,
    	        (x+1)*wcut, y*hcut,
    	    };
	}

}
