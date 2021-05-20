package engine.io;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private int width, height;
    private String title;
    private long window; // to create a window, glfw is c++ based, java store as long for classes
    private int frames;
    private  long time;
    private  Input input;
    private float backgroundR,backgroundG,backgroundB;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullscreen;


    public  Window(int width,int height,String title){
        this.height = height;
        this.title = title;
        this.width = width;
    }
    public void create(){
        if(!GLFW.glfwInit()){
           System.err.println("not init!!");
           return;
        }
        input = new Input();
        window = GLFW.glfwCreateWindow(width,height,title,isFullscreen ? GLFW.glfwGetPrimaryMonitor():0/*GLFW.glfwGetPrimaryMonitor()/*for full screen*/ ,0);
        //The native OpenGL context itself is created by GLFW's native code when calling glfwCreateWindow
        if(window ==0){
            System.err.println("no window created!!");
            return;
        }
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        // glfwvidmode to set the position
        GLFW.glfwSetWindowPos(window,(vidMode.width()-width)/2,(vidMode.height()-height)/2);
        // these position put the window in the center of the screen
        GLFW.glfwShowWindow(window);
        // to show the window

        time = System.currentTimeMillis();
        // time to count the frames
        GLFW.glfwMakeContextCurrent(window); // to change on the window
        //made current in the calling thread when calling glfwMakeContextCurrent

        // after making the window is the current one we need to make it renderable to make it opengl so we use this
        GL.createCapabilities();
        // GL.createCapabilities() or more precisely the GLCapabilities class
        // query the underlying function pointer addresses for all known OpenGL functions
        // that are declared in any LWJGL OpenGL class, such as GL11..GL46.
        //In addition, GL.createCapabilities() also loads the names of all available OpenGL extensions
        // and parses the effective OpenGL version
        GLFW.glfwSwapInterval(1); // make 60 frame in second (it locks it based on your monitors refresh rate)

        createCallBacks();

    }
    private void  createCallBacks(){

        sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };
        GLFW.glfwSetKeyCallback(window,input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window,input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window,input.getMouseButtonCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);

    }
    public void update(){
        if(isResized){
        GL11.glViewport(0,0,width,height); // in each fram it set the view port the screen size
        isResized = false;
        }
        //befor updating the screen for render we need to set the background color and clean the window
        GL11.glClearColor(backgroundR,backgroundG,backgroundB,1.0f); // GL11: OPEN GL VERSION 1.1
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GLFW.glfwPollEvents(); // to interact with the window
        frames++;
        while (System.currentTimeMillis() > time+1000){
            // when a second is passed
            GLFW.glfwSetWindowTitle(window,"Game |FPS: "+frames); // FPS (frame per second)
            System.out.println(frames);
            time = System.currentTimeMillis();
            frames=0;
        }

    }
    public void swapBuffers(){
        GLFW.glfwSwapBuffers(window);
        // to render right
    }
    public boolean closeWindow(){
       return GLFW.glfwWindowShouldClose(window);
       // to be able to close the window using x button
    }
    public  void destroy(){
        input.destroy();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();

    }
    public void setBackgroundColor(float r,float g,float b){
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }


    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
        isResized =true;
    }
}
