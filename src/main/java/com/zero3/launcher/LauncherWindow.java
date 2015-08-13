package com.zero3.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class LauncherWindow {

    private static FlowLayout         layout;
    private static JFrame             frame;
    private static JPanel             panel;
    private static JLabel             text;
    private static ImageIcon          devImg;
    private static ImageIcon          gameImg;
    private static JLabel             devLogo;
    private static JLabel             gameLogo;
    private static JButton            play;
    private static JButton            libs;
    private static JButton            exit;
    private static JTextField         user;
    private static JPasswordField     pass;
    private static Container          pane;

    protected void setupFramePre() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        frame.setUndecorated(true);
        frame.setSize(800, 600);
        frame.setIconImage(new ImageIcon(LauncherMain.class.getResource("/img/favicon.png")).getImage());
    }

    protected void setupComponents() {
        layout      = new FlowLayout();
        frame       = new JFrame("Capacity Launcher");
        panel       = new JPanel(layout);
        text        = new JLabel("Run this before playing the game!");
        devImg      = new ImageIcon(Toolkit.getDefaultToolkit().getImage("/img/zerocubed.png"));
        gameImg     = new ImageIcon(Toolkit.getDefaultToolkit().getImage("/img/logo.png"));
        devLogo     = new JLabel(devImg);
        gameLogo    = new JLabel(gameImg);
        play        = new JButton("Play!");
        libs        = new JButton("Download libraries!");
        exit        = new JButton("Exit!");
        user        = new JTextField("Username");
        pass        = new JPasswordField("Password");
        pane        = frame.getContentPane();

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    protected void addComponents() {
        panel.add(text);
        panel.add(devLogo);
        panel.add(gameLogo);
        panel.add(play);
        panel.add(libs);
        panel.add(exit);
        panel.add(user);
        panel.add(pass);
    }

    protected void setupFramePost() {
        frame.setBackground(Color.black);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    protected void add(Component comp, String borderLayout) { pane.add(comp, borderLayout); }
    protected void size(Component comp, int width, int height) { comp.setPreferredSize(new Dimension(width, height)); }
    protected void location(Component comp, int x, int y) { comp.setLocation(x, y); }

    protected void createTaskIcon() {
        if (!SystemTray.isSupported()) { System.err.println("SystemTray is not supported"); return; }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon;
        if (System.getProperty("os.name").startsWith("Windows")) {      // Windows
            trayIcon = new TrayIcon(createImage("/img/winfav.png", "tray icon"));
        } else if (System.getProperty("os.name").startsWith("MacOS")) { // OS X
            trayIcon = new TrayIcon(createImage("/img/macfav.png", "tray icon"));
        } else {                                                        // Linux/Other OS
            trayIcon = new TrayIcon(createImage("/img/linfav.png", "tray icon"));
        }
        final SystemTray tray = SystemTray.getSystemTray();

        MenuItem aboutItem = new MenuItem("About");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");

        popup.add(aboutItem);
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { frame.toFront(); frame.setState(Frame.NORMAL); } });
        aboutItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Capacity Launcher v0.0.1 Alpha - Product of Zero³ - Do not distribute!", "About", JOptionPane.INFORMATION_MESSAGE); }
        });

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuItem item = (MenuItem) e.getSource();
                System.out.println(item.getLabel());
                switch (item.getLabel()) {
                    case "Error": {
                        trayIcon.displayMessage("Sun TrayIcon Demo", "This is an error message",    TrayIcon.MessageType.ERROR);
                        break;
                    }
                    case "Warning": {
                        trayIcon.displayMessage("Sun TrayIcon Demo", "This is a warning message",   TrayIcon.MessageType.WARNING);
                        break;
                    }
                    case "Info": {
                        trayIcon.displayMessage("Sun TrayIcon Demo", "This is an info message",     TrayIcon.MessageType.INFO);
                        break;
                    }
                    default: {
                        trayIcon.displayMessage("Sun TrayIcon Demo", "This is an ordinary message", TrayIcon.MessageType.NONE);
                    }
                }
            }
        };

        trayIcon.setToolTip("Capacity Launcher v" + new LauncherMain().getVer());

        errorItem.addActionListener(listener);
        warningItem.addActionListener(listener);
        infoItem.addActionListener(listener);
        noneItem.addActionListener(listener);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
    }
    protected static Image createImage(String path, String description) {
        if (LauncherMain.class.getResource(path) == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(LauncherMain.class.getResource(path))).getImage();
        }
    }

}
