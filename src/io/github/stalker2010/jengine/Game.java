package io.github.stalker2010.jengine;

import io.github.stalker2010.illusion.IllusionRender;
import io.github.stalker2010.jengine.modules.Resources;
import io.github.stalker2010.jengine.modules.SoundEngine;
import io.github.stalker2010.jengine.render.BaseRender;
import io.github.stalker2010.jengine.render.Simple2DRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;

import static io.github.stalker2010.jengine.DB.db;

/**
 * @author STALKER_2010
 */
public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = -5445130295019858065L;
    private boolean running = false;
    public static Game instance;
    public JFrame frame;
    public String currentRoom = "";
    public Resources resources;
    public SoundEngine soundEngine;
    public boolean unstableState = true;
    public boolean sheduleReset = false;

    public Game() {
        instance = this;
    }

    public void start() {
        running = true;

        new Thread(this).start();
    }

    public static final int FPS = 25;
    private static final int FrameDuration = 1000 / FPS;
    private static final int MaxFrameSkip = 10;
    private long nextFrameTime = System.currentTimeMillis();

    public void run() {
        int loops;
        if (init()) {
            System.err.println("Failed to initialized Game");
            return;
        }
        if (!render.init()) {
            System.err.println("Game: Render not initialized correctly");
            return;
        }

        long start = System.currentTimeMillis();
        int fps = 0;
        while (running) {
            loops = 0;
            while (System.currentTimeMillis() > nextFrameTime
                    && loops < MaxFrameSkip) {
                DB.setLocked(true);
                if (unstableState) {
                    try {
                        update();
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                } else {
                    update();
                }

                nextFrameTime += FrameDuration;

                loops++;
                DB.setLocked(false);
            }
            render(db.rooms.get(currentRoom));
            fps++;
            if ((start + 1000) <= System.currentTimeMillis()) {
                frame.setTitle(NAME + " (" + fps + " FPS)");
                fps = 0;
                start = System.currentTimeMillis();
            }
            DB.setLocked(true);
            if (sheduleReset) {
                sheduleReset = false;
                System.out.println("Performing shedule reset");
                ((IllusionRender) render).resetAndFixEverything();
            }
            DB.setLocked(false);
        }
    }

    public boolean init() {
        DB.setLocked(true);
        resources = new Resources(this);
        soundEngine = new SoundEngine(this);
        render = new Simple2DRender(this);
        unstableState = false;
        addKeyListener(new Keyboard());
        addMouseListener(mouseListener);
        db.onGameLoaded(this);
        currentRoom = "";
        DB.setLocked(false);
        return false;
    }

    public BaseRender render;

    public void render(final Room room) {
        if (render != null) {
            if (unstableState) return;
            if (DB.db.locked) return;
            render.render(room);
        }
    }

    public void update() {
        final Room room = db.rooms.get(currentRoom);
        room.update();
        db.objects.values()
                .forEach(io.github.stalker2010.jengine.GameObject::update);
        resources.animations.internal.values()
                .forEach(io.github.stalker2010.jengine.Animation::update);
        resources.animations.generated.values()
                .forEach(io.github.stalker2010.jengine.Animation::update);
        render.objs.clear();
        render.objs.addAll(db.objects.values());
        Collections.sort(render.objs, GameObject.compareByDepth);
    }

    public static int WIDTH = 1094;
    public static int HEIGHT = 600;
    public static String NAME = "JEngine";

    public static Game launch(final Game game) {
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        game.setBounds(0, 0, WIDTH, HEIGHT);
        game.frame = new JFrame(Game.NAME);

        game.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.frame.setLayout(null);
        game.frame.add(game);
        game.frame.setResizable(false);
        game.frame.pack();
        game.frame.setVisible(true);
        game.frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        game.frame.setBounds(0, 0, WIDTH, HEIGHT);
        game.start();

        return game;
    }

    private final MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public void mouseClicked(MouseEvent e) {
            while (unstableState) {
            }
            while (DB.db.locked) {
            }
            PointerInfo a = MouseInfo.getPointerInfo();
            Point point = new Point(a.getLocation());
            SwingUtilities.convertPointFromScreen(point, e.getComponent());
            final boolean[] allowNext = {true};
            db.objects.values().stream()
                    .sorted(GameObject.compareByDepth)
                    .forEach(o -> {
                        if (!allowNext[0]) return;
                        while (unstableState) {
                        }
                        while (DB.db.locked) {
                        }
                        if (o.onGlobalMouseClick(point.getX(), point.getY())) {
                            allowNext[0] = false;
                        }
                    });
        }
    };

    class Keyboard extends KeyAdapter {
        @SuppressWarnings("StatementWithEmptyBody")
        public void keyReleased(KeyEvent w) {
            final int kCode = w.getKeyCode();
            while (unstableState) {
            }
            while (DB.db.locked) {
            }
            for (GameObject gameObject : db.objects.values()) {
                while (unstableState) {
                }
                while (DB.db.locked) {
                }
                gameObject.onKey(kCode);
            }
        }

        @SuppressWarnings("StatementWithEmptyBody")
        public void keyPressed(KeyEvent e) {
            final int kCode = e.getKeyCode();
            while (unstableState) {
            }
            while (DB.db.locked) {
            }
            for (GameObject o : db.objects.values()) {
                while (unstableState) {
                }
                while (DB.db.locked) {
                }
                o.onKeyPress(kCode);
            }
        }
    }

}
