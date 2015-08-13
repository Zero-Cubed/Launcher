package com.zero3.launcher;

import java.util.Random;
import java.util.Scanner;

public class LauncherMain {

    private String username = "";
    private String password = "";
    protected String getVer() { return "0.0.1"; }
    private Random rand;

    public static void main(String[] args) {

        LauncherWindow win = new LauncherWindow();
        win.createTaskIcon();
        win.setupComponents();
        win.setupFramePre();
        win.addComponents();
        win.setupFramePost();

        /*
        LauncherMain m = new LauncherMain();
        m.rand = new Random(System.currentTimeMillis());
        System.out.println("Enter your username.");
        String user = new Scanner(System.in).nextLine().trim();
        String eU = m.e(user);
        String dU = m.d(eU);
        System.out.println("Enter your password.");
        String pass = new Scanner(System.in).nextLine().trim();
        String eP = m.e(pass);
        String dP = m.d(eP);
        System.out.println("User: "         + user);
        System.out.println("Encrypted: "    + eU);
        System.out.println("Decrypted: " + dU);
        System.out.println("Pass: "         + pass);
        System.out.println("Encrypted: " + eP);
        System.out.println("Decrypted: " + dP);
        */
    }

    private String e(String str) {
        sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
        byte[] saltPrefix = new byte[8];
        byte[] saltSuffix = new byte[4];
        rand.nextBytes(saltPrefix);
        rand.nextBytes(saltSuffix);
        /*
        System.out.println("Salt prefix: "      + enc.encode(saltPrefix));
        System.out.println("Encrypted word: "   + enc.encode(str.getBytes()));
        System.out.println("Salt suffix: "      + enc.encode(saltSuffix));
        */
        return (enc.encode(saltPrefix) + enc.encode(str.getBytes()) + enc.encode(saltSuffix));
    }

    private String d(String str) {
        if (str.length() > 16) {
            str = str.substring(12, str.length() - 7);
            try { return new String(new sun.misc.BASE64Decoder().decodeBuffer(str)); } catch (Exception ignored) {}
        }
        return "";
    }
}