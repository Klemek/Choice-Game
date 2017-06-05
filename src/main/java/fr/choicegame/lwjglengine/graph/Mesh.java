package fr.choicegame.lwjglengine.graph;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import fr.choicegame.lwjglengine.Utils;

public class Mesh {

	private final int vaoId;
	
	//private final int vaoId, posVboId, idxVboId, colourVboId;

	private final List<Integer> vboIdList;
	
	private final int vertexCount;
	
	private final Material material;

	public Mesh(List<Float> positions, List<Float> textCoords, List<Integer> indices, Material material) {
		this(Utils.arrayToListFloat(positions),Utils.arrayToListFloat(textCoords),Utils.arrayToListInt(indices),material);
	}
	
	public Mesh(float[] positions, int[] indices, Material material) {
		this(positions,new float[2*positions.length/3],indices,material);
	}
	
	public Mesh(float[] positions, float[] textCoords, int[] indices, Material material) {
		FloatBuffer verticesBuffer = null;
		IntBuffer indicesBuffer = null;
		//FloatBuffer colourBuffer = null;
		FloatBuffer textCoordsBuffer = null;
		
		vboIdList = new ArrayList<>();
		
		this.material = material;
		
		try {
			
			vaoId = glGenVertexArrays();
			glBindVertexArray(vaoId);
			
			verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
			vertexCount = indices.length;
			verticesBuffer.put(positions).flip();
			
			//position vbo
			int vboId = glGenBuffers();
			vboIdList.add(vboId);
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			//texture vbo
			vboId = glGenBuffers();
			vboIdList.add(vboId);
			textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
			textCoordsBuffer.put(textCoords).flip();
			glBindBuffer(GL_ARRAY_BUFFER, vboId);
			glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			
			//index vbo
			vboId = glGenBuffers();
			indicesBuffer = MemoryUtil.memAllocInt(indices.length);
			indicesBuffer.put(indices).flip();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		} finally {
			if (verticesBuffer != null) 
				MemoryUtil.memFree(verticesBuffer);
			/*if (colourBuffer != null)
			MemoryUtil.memFree(colourBuffer);*/
			if (textCoordsBuffer != null)
				MemoryUtil.memFree(textCoordsBuffer);
			if (indicesBuffer != null)
				MemoryUtil.memFree(indicesBuffer);
		}
	}

	public int getVaoId() {
		return vaoId;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void cleanUp() {
		glDisableVertexAttribArray(0);

		// Delete the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		for(int vboId:vboIdList)
			glDeleteBuffers(vboId);
		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);
	}
	
	public void render(){
		// Draw the mesh
		if(material.getTexture() != null){
		
			// Activate first texture unit
			glActiveTexture(GL_TEXTURE0);
			// Bind the texture
			glBindTexture(GL_TEXTURE_2D, material.getTexture().getId());
		
		}else{
			//TODO not working
			glDisable(GL_TEXTURE_2D);
		}
		
	    glBindVertexArray(getVaoId());
	    glEnableVertexAttribArray(0);
	    glEnableVertexAttribArray(1);

	    glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

	    // Restore state
	    glDisableVertexAttribArray(0);
	    glDisableVertexAttribArray(1);
	    glBindVertexArray(0);
	    
	    if(material.getTexture() == null){
	    	glEnable(GL_TEXTURE_2D);
	    }
	}

	public Material getMaterial() {
		return material;
	}
	
	public boolean isTextured(){
		return material.getTexture() != null;
	}

	public Vector4f getColour() {
		return material.getColor();
	}
}
