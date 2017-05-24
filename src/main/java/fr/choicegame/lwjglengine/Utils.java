package fr.choicegame.lwjglengine;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

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
}