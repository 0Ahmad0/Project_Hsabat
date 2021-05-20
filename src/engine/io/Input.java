package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

// callback to control inputs
public class Input {
    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseButton;
    private static boolean keys[] = new boolean[GLFW.GLFW_KEY_LAST]; // to store keys used and GLFW_KEY_LAST is the number of keys supported
    private static boolean buttons[] = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX,mouseY;

    public Input(){
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                // this function is every time you click on keyboard it runs
                // the arguments are : 1) the window that work on 2) key which pressing 3)scancode
                //4) action is pressed 5) release
                keys[key] = (action!=GLFW.GLFW_RELEASE);
            }
        };
        mouseMove = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;

            }
        };
        mouseButton = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action!=GLFW.GLFW_RELEASE);
            }

        };
    }

    // static because we dont want to make a new input class every single time
    public static boolean isKeyDown(int key){
        return keys[key];
    }
    public static boolean isButtonDown(int button){
        return buttons[button];
    }


    //get rid of the callbacks after we done
    public void destroy(){
        keyboard.free();
        mouseMove.free();
        mouseButton.free();
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMove;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButton;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }
}
