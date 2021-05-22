/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author kevinlawrence
 */
public abstract class Environment extends JPanel {

    //    <editor-fold defaultstate="collapsed" desc="Constructor & Initialization">
    public Environment() {
        initialize();
    }

    public Environment(Image background) {
        this.background = background;
        initialize();
    }

    public final void initialize() {
        // ---- Timer ----
        initializeEnvironment();
        initializeTimer();

        // ---- Mouse Event Handler ----
        addMouseListener(new EnvironmentMouseListener());
        // ---- Mouse Handlers ----
        addMouseListener(new EnvironmentMouseListener());
        addMouseMotionListener(new EnvironmentMouseMotionListener());

        // ---- Key Handler ----
        addKeyListener(new EnvironmentActionListener());
        setFocusable(true);
    }

    public abstract void initializeEnvironment();
//    </editor-fold>

    //    <editor-fold defaultstate="collapsed" desc="Methods">
    public void act() {
//        for (Actor actor : actors) {
//            actor.move();
//        }
        repaint();
    }
//    </editor-fold>

    //   <editor-fold defaultstate="collapsed" desc="Timer">
    public static final long DEFAULT_TIMER_DELAY_MS = 100;
    public static final long DEFAULT_TIMER_PERIOD_MS = 20;
    public static final long MINIMUM_TIMER_PERIOD_MS = 5;

    private long timerDelayMS = DEFAULT_TIMER_DELAY_MS;
    private long timerPeriodMS = DEFAULT_TIMER_PERIOD_MS;

    private Timer environmentTimer = new Timer();

    public void startTimer() {
        startTimer(timerDelayMS, getTimerPeriodMS());
    }

    public void startTimer(long timerDelayMS, long timerPeriodMS) {
        this.timerDelayMS = timerDelayMS;
        this.timerPeriodMS = timerPeriodMS;

        initializeTimer();
    }

    public void initializeTimer() {
        cancelTimerEvents();

        environmentTimer = new Timer();
        environmentTimer.schedule(new EnvironmentTimerTask(), timerDelayMS, getTimerPeriodMS());
    }

    /**
     * @param timerPeriodMS the timerPeriodMS to set
     */
    public void setTimerPeriodMS(long timerPeriodMS) {
        this.timerPeriodMS = Math.max(timerPeriodMS, MINIMUM_TIMER_PERIOD_MS);
        initializeTimer();
    }

    public void cancelTimerEvents() {
        try {
            environmentTimer.cancel();
        } catch (Exception e) {
            //just bury the exception...
        }
    }

    /**
     * @return the environmentTimer
     */
    public Timer getEnvironmentTimer() {
        return environmentTimer;
    }

    /**
     * @return the timerPeriodMS
     */
    public long getTimerPeriodMS() {
        return timerPeriodMS;
    }

    private class EnvironmentTimerTask extends TimerTask {
        @Override
        public void run() {
            timerTaskHandler();
            act();
        }
    }

    public abstract void timerTaskHandler();
//  </editor-fold>

    //   <editor-fold defaultstate="collapsed" desc="Key Handler">
    private class EnvironmentActionListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            keyPressedHandler(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keyReleasedHandler(e);
        }
    }

    public abstract void keyPressedHandler(KeyEvent e);

    public abstract void keyReleasedHandler(KeyEvent e);
//  </editor-fold>

    //   <editor-fold defaultstate="collapsed" desc="Mouse Event Handlers">
    private class EnvironmentMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            environmentMouseClicked(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            environmentMousePressed(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            environmentMouseReleased(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            environmentMouseEntered(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            environmentMouseExited(e);
        }
    }

    private class EnvironmentMouseMotionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            environmentMouseDragged(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            environmentMouseMoved(e);
        }
    }

    protected abstract void environmentMouseExited(MouseEvent e);
    protected abstract void environmentMouseEntered(MouseEvent e);
    protected abstract void environmentMouseReleased(MouseEvent e);
    protected abstract void environmentMousePressed(MouseEvent e);
    public abstract void environmentMouseDragged(MouseEvent e);
    public abstract void environmentMouseMoved(MouseEvent e);
    public abstract void environmentMouseClicked(MouseEvent e);
//  </editor-fold>

    //   <editor-fold defaultstate="collapsed" desc="Painting">
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        paintBackground(graphics2D);
        paintEnvironment(graphics);
        paintActors(graphics);
    }

    public void paintBackground(Graphics2D graphics2D) {
        graphics2D.drawImage(background, backgroundImagePosition.x, backgroundImagePosition.y, this);
    }

    public abstract void paintEnvironment(Graphics graphics);

    public void paintActors(Graphics graphics) {
//        for (Actor actor : getActors()) {
//            actor.paint(graphics);
//        }
    }
//  </editor-fold>

    //   <editor-fold defaultstate="collapsed" desc="Properties">
//    private ArrayList<Actor> actors = new ArrayList<Actor>();
    private Image background = null;
    private Point backgroundImagePosition = new Point(0, 0);

    /**
     * @param background the background image to set
     */
    public void setBackground(Image background) {
        this.background = background;
    }

    /**
     * @return the background image
     */
    public Image getBackgroundImage() {
        return background;
    }

//    /**
//     * @return the actors
//     */
//    public ArrayList<Actor> getActors() {
//        return actors;
//    }

    /**
     * @return the backgroundImagePosition
     */
    public Point getBackgroundImagePosition() {
        return backgroundImagePosition;
    }

    /**
     * @param backgroundImagePosition the backgroundImagePosition to set
     */
    public void setBackgroundImagePosition(Point backgroundImagePosition) {
        this.backgroundImagePosition = backgroundImagePosition;
    }
//  </editor-fold>

}
