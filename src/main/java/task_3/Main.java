package task_3;

import task_3.window.Window;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(650, 600);
    }
}
