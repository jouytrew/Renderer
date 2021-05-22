/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package environment;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author kevinlawrence
 */
public class ApplicationStarter {
    public static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);

    /**
     *
     * @param args arguments to be parsed by application
     * @param appName application name; will appear in the title bar of the main
     * window
     * @param appSize initial size for main window
     * @param environment the main JFrame descendant for the main window
     * @return a JFrame class that is the main window of the application
     */
    public static JFrame run(String[] args, String appName, Dimension appSize, Environment environment) {
        JFrame frame = new JFrame(appName);

        frame.add(environment);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(appSize);
        frame.setVisible(true);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH); // Maximizes the screen
        return frame;
    }

    /**
     *
     * @param appName application name; will appear in the title bar of the main
     * window
     * @param environment the main JFrame descendant for the main window
     * @return a JFrame class that is the main window of the application
     */
    public static JFrame run(String appName, Environment environment) {
        return run(new String[0], appName, DEFAULT_WINDOW_SIZE, environment);
    }

    /**
     *
     * @param appName application name; will appear in the title bar of the main
     * window
     * @param environment the main JFrame descendant for the main window
     * @param applicationIcon an icon for the application
     * @return a JFrame class that is the main window of the application
     */
    public static JFrame run(String appName, Environment environment, Image applicationIcon) {
        JFrame frame = run(new String[0], appName, DEFAULT_WINDOW_SIZE, environment);
        frame.setIconImage(applicationIcon);
        return frame;
    }
}