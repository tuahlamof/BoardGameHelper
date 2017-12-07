package com.android.crystal.boardgamehelper;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huiyu on 12/5/17.
 */

public class SoundPlayer {
    private static MediaPlayer music;
    private static Context context;
    private static Map<String, Integer> musicMap = new HashMap<>();
    private static SoundPlayer instance;
    public static SoundPlayer getInstance() {
        if (instance == null) {
            instance = new SoundPlayer();
        }
        return instance;
    }

    public SoundPlayer() {
        //musicMap.put("close_eyes", R.raw.close_eyes);
        musicMap.put("guard", R.raw.guard);
        musicMap.put("open_eyes", R.raw.open_eyes);
        musicMap.put("seer", R.raw.seer);
        musicMap.put("werewolf", R.raw.werewolf);
        musicMap.put("witch_poison", R.raw.witch_poison);
        musicMap.put("witch_save", R.raw.witch_save);
    }
    public static void init(Context c) {
        context = c;
    }
    public static void playMusic(String musicName) {
        if (music != null) {
            music.stop();
        }
        int musicId = musicMap.get(musicName);
        music = MediaPlayer.create(context, musicId);
        music.setLooping(false);
        music.start();
    }
}
