package fr.choicegame.lwjglengine.sound;

import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import fr.choicegame.Config;
import fr.choicegame.Loader;

public class SoundManager {

    private long device;

    private long context;
    
    private final Loader loader;

    private final Map<String, SoundBuffer> soundBufferMap;
    
    private final List<SoundSource> soundSourceList;

    public SoundManager(Loader l) {
		loader = l;
        soundBufferMap = new HashMap<>();
        soundSourceList = new ArrayList<>();
    }

    public void init() throws Exception {
        this.device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        this.context = alcCreateContext(device, (IntBuffer) null);
        if (context == NULL) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        alcMakeContextCurrent(context);
        AL.createCapabilities(deviceCaps);
        
        loader.loadSounds(this);
        
    }

    public void playSound(String name, boolean repeat){
    	String fileName = "/"+Config.getValue(Config.SOUNDS_FOLDER)+"/"+name+".ogg";
    	if(this.soundBufferMap.containsKey(fileName)){
    		SoundSource source = new SoundSource(repeat,true);
    		source.setBuffer(this.soundBufferMap.get(fileName).getBufferId());
    		this.soundSourceList.add(source);
    		source.play();
    	}else{
    		System.out.println("#Unknown sound file : "+fileName);
    	}
    }
    
    public void removeAllSoundSources() {
    	for (SoundSource soundSource : soundSourceList) {
            soundSource.cleanup();
        }
        soundSourceList.clear();
    }

    public void addSoundBuffer(String path) throws Exception {
    	SoundBuffer buffer = new SoundBuffer(path);
        this.soundBufferMap.put(path,buffer);
    }
    
    public void cleanup() {
        for (SoundSource soundSource : soundSourceList) {
            soundSource.cleanup();
        }
        soundSourceList.clear();
        for (SoundBuffer soundBuffer : soundBufferMap.values()) {
            soundBuffer.cleanup();
        }
        soundBufferMap.clear();
        if (context != NULL) {
            alcDestroyContext(context);
        }
        if (device != NULL) {
            alcCloseDevice(device);
        }
    }
}
