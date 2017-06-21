package fr.choicegame.lwjglengine;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import static org.lwjgl.BufferUtils.*;

public class Utils {

    public static String loadResource(String fileName, Object o) throws Exception {
        String result;
        try (InputStream in = o.getClass().getResourceAsStream(fileName);
                Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }

    public static String loadResource(String fileName) throws Exception {
        String result;
        try (InputStream in = Utils.class.getClass().getResourceAsStream(fileName);
                Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
    
    public static float[] arrayToListFloat(List<Float> array){
    	float[] list= new float[array.size()];
    	for(int i = 0; i < array.size(); i++){
    		list[i] = array.get(i);
    	}
    	return list;
    }
    
    public static int[] arrayToListInt(List<Integer> array){
    	int[] list= new int[array.size()];
    	for(int i = 0; i < array.size(); i++){
    		list[i] = array.get(i);
    	}
    	return list;
    }
    
    public static float getTextWidth(Font font, String text){
    	String[] text2 = text.split("\n");
    	BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2D = img.createGraphics();
	    g2D.setFont(font);
	    FontMetrics fontMetrics = g2D.getFontMetrics();
	    int max = 0;
	    for(String text3:text2){
	    	int w = fontMetrics.stringWidth(text3);
	    	if(w>max)
	    		max=w;
	    }
	    return max;
    }

	public static int countLines(String text) {
		
		int nlines = 1;
		
		for(char c:text.toCharArray()){
			if(c == '\n')
				nlines++;
		}
		
		return nlines;
	}

	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) ;
            }
        } else {
            try (
                    InputStream source = Utils.class.getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
	}
	
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
	}
}