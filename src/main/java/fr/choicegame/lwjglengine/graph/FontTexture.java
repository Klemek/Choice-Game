package fr.choicegame.lwjglengine.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class FontTexture extends Texture{

	private Font font;
	private String charSetName;
	private HashMap<Character, CharInfo> charMap;
	
	private Color color;
	
	private int width, height;
	
	public FontTexture(Font font, String charSetName, Color color) throws Exception {
	    this.font = font;
	    this.charSetName = charSetName;
	    this.color = color;
	    charMap = new HashMap<>();
	    buildTexture();
	}
	
	private String getAllAvailableChars(String charsetName) {
	    CharsetEncoder ce = Charset.forName(charsetName).newEncoder();
	    StringBuilder result = new StringBuilder();
	    for (char c = 0; c < Character.MAX_VALUE; c++) {
	        if (ce.canEncode(c)) {
	            result.append(c);
	            result.append(' '); //add space between
	        }
	    }
	    return result.toString();
	}
	
	private void buildTexture() throws Exception {
	    // Get the font metrics for each character for the selected font by using image
	    BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2D = img.createGraphics();
	    g2D.setFont(font);
	    FontMetrics fontMetrics = g2D.getFontMetrics();

	    String allChars = getAllAvailableChars(charSetName);
	    this.width = 0;
	    this.height = 0;
	    
	    
	    char[] allCharsArray = allChars.toCharArray();
	    
	    int spacewidth = fontMetrics.charWidth(' ');
	    
	    for (int i = 0; i < allCharsArray.length; i++) {
	        // Get the size for each character and update global image size
	    	char c = allCharsArray[i];
	    	if(i%2 == 0){
	    		 CharInfo charInfo = new CharInfo(width, fontMetrics.charWidth(c));
	    		 charMap.put(c, charInfo);
	    		 width += charInfo.getWidth();
	    		 height = Math.max(height, fontMetrics.getHeight());
	    	}else{
	    		width += spacewidth;
	    	}
	        
	    }
	    g2D.dispose();
	    
	    // Create the image associated to the charset
	    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    g2D = img.createGraphics();
	    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2D.setFont(font);
	    fontMetrics = g2D.getFontMetrics();
	    g2D.setColor(color);
	    g2D.drawString(allChars, 0, fontMetrics.getAscent());
	    g2D.dispose();

	    // Dump image to a byte buffer
	    InputStream is;
	    try (
	        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
	        ImageIO.write(img, "png", out);
	        out.flush();
	        is = new ByteArrayInputStream(out.toByteArray());
	        this.init(is);
	    }
	}
	
	public CharInfo getCharInfo(char c){
		return charMap.get(c);
	}
	
	public Font getFont() {
		return font;
	}
	
	public class CharInfo {

	    private final int startX;

	    private final int width;

	    public CharInfo(int startX, int width) {
	        this.startX = startX;
	        this.width = width;
	    }

	    public int getStartX() {
	        return startX;
	    }

	    public int getWidth() {
	        return width;
	    }
	}
}
