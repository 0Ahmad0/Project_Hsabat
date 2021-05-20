package com.company;

import engine.io.Input;
import engine.io.Window;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {

    public  Thread game;
    public  Window window;
    public final int WIDTH= 1000,HEIGHT =700;
    public void start(){
        game = new Thread(this,"game");
        game.start();
    }
    public static void main(String[] args) {
        new Main().start();
    }

    public void init(){
        window = new Window(WIDTH,HEIGHT,"game");
        window.setBackgroundColor(1.0f,0.8f,1.0f);
       // window.setFullscreen(true); // TO set full screen
        window.create();

    }
    @Override
    public void run() {
        System.out.println("starting game");
        init();
        while (!window.closeWindow()){
            update();
            render();
            if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) break;
        }
        window.destroy();

    }

    public void update(){
        window.update();
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) System.out.println("X : "+ Input.getMouseX()+", Y : "+Input.getMouseY());
    }
    public void render(){
        window.swapBuffers();
    }
}
