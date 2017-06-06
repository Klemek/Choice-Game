package fr.choicegame.lwjglengine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

public class Window {

	// The window handle
	private long windowHandle;
	private int width, height;
	private boolean resized = true, vsync;
	private String title;
	private KeyEventListener listener;
	
	public Window(String windowTitle, int width, int height, boolean vsSync,KeyEventListener listener) {
		this.setWidth(width);
		this.setHeight(height);
		this.vsync = vsSync;
		this.resized = false;
		this.title = windowTitle;
		this.listener = listener;
	}

	public void init() {
		
		//OSX trick to use fonts
		if(System.getProperty("os.name").contains("OS X"))
			System.setProperty("java.awt.headless", "true");
		
		System.out.println("Loading LWJGL 3 ...");
		
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		
		// Create the window
		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		if ( windowHandle == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup resize callback
		glfwSetWindowSizeCallback(windowHandle, (window, width, height) -> {
		    Window.this.setWidth(width);
		    Window.this.setHeight(height);
		    Window.this.setResized(true);
		});

		glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
			
			if(listener != null)
				listener.keyEvent(key, action, mods);
			/*if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop*/
		});
				
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		/*glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});*/

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(windowHandle, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
					windowHandle,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically
		
		// Make the OpenGL context current
		glfwMakeContextCurrent(windowHandle);
		// Enable v-sync
		if(vsync)
			glfwSwapInterval(1);

		GL.createCapabilities();
		
		//render 3D meshes in the right order
		glfwShowWindow(windowHandle);

		
		
		glClearColor(0,0,0,0);
	}
	
	public void show(){
		// Make the window visible
		glfwShowWindow(windowHandle);
	}
	

	public void setClearColor(float color, float color2, float color3, float f) {
		glClearColor(color, color2, color3, f);
	}

	public boolean windowShouldClose() {
		return glfwWindowShouldClose(windowHandle);
	}
	
	public boolean isKeyPressed(int keyCode) {
	    return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}

	public void update() {
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}

	public boolean isResized() {
		return resized;
	}

	public void setResized(boolean resized) {
		this.resized = resized;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isVsync() {
		return vsync;
	}
	
	public static interface KeyEventListener{
		
		void keyEvent(int key,int action,int mods);
		
	}

	
	
	
}
