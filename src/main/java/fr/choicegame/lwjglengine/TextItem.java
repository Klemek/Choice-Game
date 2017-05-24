package fr.choicegame.lwjglengine;

import java.util.ArrayList;
import java.util.List;

import fr.choicegame.lwjglengine.graph.FontTexture;
import fr.choicegame.lwjglengine.graph.Material;
import fr.choicegame.lwjglengine.graph.Mesh;

public class TextItem extends GameItem {

	private static final float ZPOS = 0.0f;

	private static final int VERTICES_PER_QUAD = 4;

	private String text;

	private FontTexture fontTexture;

	public TextItem(String text, FontTexture fontTexture) throws Exception {
	    super();
	    this.text = text;
	    this.fontTexture = fontTexture;
	    setMesh(buildMesh());
	}
	
	private Mesh buildMesh() {
		
		char[] chars = text.toCharArray();
		
		List<Float> positions = new ArrayList<>();
		List<Float> textCoords = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();

		float tileHeight = fontTexture.getHeight();
		float medTileWidth = fontTexture.getCharInfo('A').getWidth();
		float x = 0;
		float y = 0;
		int n = 0;
		
		for (int i = 0; i < chars.length; i++) {
			switch(chars[i]){
			case '\n':
				y+=tileHeight;
				x = 0;
				break;
			case '\r':
				x = 0;
				break;
			case '\t':
				x = medTileWidth*8*(1+(int)((float)x/(8f*medTileWidth)));
				break;
			default:
				FontTexture.CharInfo charInfo = fontTexture.getCharInfo(chars[i]);
				// Build a character tile composed by two triangles

				// Left Top vertex
				positions.add(x); // x
				positions.add(y); // y
				positions.add(ZPOS); // z
				textCoords.add((float)charInfo.getStartX() / (float)fontTexture.getWidth());
				textCoords.add(0f);
				indices.add(n * VERTICES_PER_QUAD);

				// Left Bottom vertex
				positions.add(x); // x
				positions.add(y+tileHeight); // y
				positions.add(ZPOS); // z
				textCoords.add((float)charInfo.getStartX() / (float)fontTexture.getWidth());
				textCoords.add((float)tileHeight/ (float)fontTexture.getHeight());
				indices.add(n * VERTICES_PER_QUAD + 1);

				// Right Bottom vertex
				positions.add(x+(float)charInfo.getWidth()); // x
				positions.add(y+tileHeight); // y
				positions.add(ZPOS); // z
				textCoords.add((float)(charInfo.getStartX()+charInfo.getWidth()) / (float)fontTexture.getWidth());
				textCoords.add((float)tileHeight/ (float)fontTexture.getHeight());
				indices.add(n * VERTICES_PER_QUAD + 2);

				// Right Top vertex
				positions.add(x+(float)charInfo.getWidth()); // x
				positions.add(y); // y
				positions.add(ZPOS); // z
				textCoords.add((float)(charInfo.getStartX()+charInfo.getWidth()) / (float)fontTexture.getWidth());
				textCoords.add(0f);
				indices.add(n * VERTICES_PER_QUAD + 3);

				// Add indices por left top and bottom right vertices
				indices.add(n * VERTICES_PER_QUAD);
				indices.add(n * VERTICES_PER_QUAD + 2);
				x+=charInfo.getWidth();
				n++;
				break;
			}
		}

		return new Mesh(positions, textCoords, indices, new Material(fontTexture));
	}

	public float getWidth(){
		return Utils.getTextWidth(fontTexture.getFont(), text);
	}
	
	public float getHeight(){
		return fontTexture.getHeight();
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.getMesh().cleanUp();
		this.setMesh(buildMesh());
	}
}
