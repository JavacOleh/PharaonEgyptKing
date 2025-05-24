package me.freelance.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import me.freelance.R;
import me.freelance.other.layout.Layoutable;
import me.freelance.other.music.Mp3Player;
import me.freelance.other.user.UserLocal;
import me.freelance.other.user.service.UserLocalService;
import me.freelance.other.util.UiUtil;

public class LoadingActivity extends AppCompatActivity implements Layoutable {
    public static Mp3Player musicPlayer;
    public static Mp3Player soundPlayer;
    public static UserLocalService userLocalService;
    public static UserLocal userLocal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);
        //setContentView(R.layout.activity_loading);

        new Handler().postDelayed(() -> {
            UiUtil.loadActivityFinishCurrent(this, MainActivity.class);
        }, 1000L);

        initServices();
        startServices();
    }

    public void initServices() {
        musicPlayer = new Mp3Player(this);
        soundPlayer = new Mp3Player(this);
        userLocalService = UserLocalService.getInstance(this);
        userLocal = UserLocalService.getUserLocal();
    }

    public void startServices() {
        //Log.i("music", String.valueOf(userLocal.getMusicVolume()));
        musicPlayer.setCurrentVolume(userLocal.getMusicVolume());
        musicPlayer.play(R.raw.music);

        soundPlayer.setCurrentVolume(userLocal.getSoundVolume());
        //soundPlayer.play(R.raw.music);
    }
}