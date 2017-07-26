package com.micutu.trafictube.Crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * This is used because of the voting security system from Trafictube.ro
 */
public class VoteNonceSingleton {
    private static VoteNonceSingleton instance = null;
    private static String nonce = null;

    private VoteNonceSingleton() {
    }

    private static VoteNonceSingleton getInstance() {
        if (instance == null) {
            instance = new VoteNonceSingleton();
        }

        return instance;
    }

    public static void saveNonceFromHtml(String html) {
        Pattern pattern = Pattern.compile("thumbs_rating_ajax.*nonce(.{5,15})\"\\};");
        Matcher matcher = pattern.matcher(html);

        try {
            matcher.find();
            VoteNonceSingleton.saveNonce(matcher.group(1));
        } catch (Exception e) {
        }

        return;
    }

    public static void saveNonce(String nonce) {
        getInstance().nonce = nonce;
    }

    public static String getNonce() {
        return getInstance().nonce;
    }
}
