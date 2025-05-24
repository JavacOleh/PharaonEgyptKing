package me.freelance.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import me.freelance.R;
import me.freelance.activities.game.GameActivity;
import me.freelance.activities.game.GameStateHolder;
import me.freelance.other.layout.Layoutable;
import me.freelance.other.music.Mp3Player;
import me.freelance.other.util.UiUtil;
import me.freelance.other.view.ImageProgressView;

public class SettingsActivity extends AppCompatActivity implements Layoutable {
    private ImageProgressView customProgressMusic, customProgressSound;
    private ImageButton increaseMusicButton, decreaseMusicButton;
    private ImageButton increaseSoundButton, decreaseSoundButton;
    private ImageButton exitButton;
    private Mp3Player musicPlayer;
    private Mp3Player soundPlayer;
    public static Class<? extends AppCompatActivity> prevActivty;

    @Override
    protected void onStart() {
        super.onStart();

        if(prevActivty.equals(GameActivity.class)) {
            GameActivity.startNewGame = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);

        musicPlayer = LoadingActivity.musicPlayer;
        soundPlayer = LoadingActivity.soundPlayer;
        //setContentView(R.layout.activity_settings);

        init();
        input();

        int[] serializedGrid = getIntent().getIntArrayExtra("serializedGrid");
        int timerValue = getIntent().getIntExtra("timerValue", 60);

        if (serializedGrid != null) {
            // Сохрани состояние в каком-нибудь статическом поле, например:
            GameStateHolder.getInstance().setSerializedGrid(serializedGrid);
            GameStateHolder.getInstance().setTimerValue(timerValue);
        }
    }

    public void init() {
        exitButton = findViewById(R.id.buttonExit);
        customProgressMusic = findViewById(R.id.customProgressMusic);
        increaseMusicButton = findViewById(R.id.increaseMusicButton);
        decreaseMusicButton = findViewById(R.id.decreaseMusicButton);

        customProgressSound = findViewById(R.id.customProgressSound);
        increaseSoundButton = findViewById(R.id.increaseSoundButton);
        decreaseSoundButton = findViewById(R.id.decreaseSoundButton);


        if(musicPlayer != null) {
            int currentMusicCount = (int) (musicPlayer.getCurrentVolume() * 10);
            customProgressMusic.setCurrentCount(currentMusicCount);
        }

        if(soundPlayer != null) {
            int currentSoundCount = (int) (soundPlayer.getCurrentVolume() * 10);
            customProgressSound.setCurrentCount(currentSoundCount);
        }
    }

    public void input() {
        exitButton.setOnClickListener(v -> {
            UiUtil.loadActivityPauseCurrent(this, prevActivty);

            if(prevActivty.equals(MainActivity.class))
                MainActivity.prevActivty = null;
        });

        increaseMusicButton.setOnClickListener(v -> {
            customProgressMusic.increase();
            musicPlayer.increaseZvuk10();

            LoadingActivity.userLocalService.setMusicVolume(musicPlayer.getCurrentVolume());
        });
        decreaseMusicButton.setOnClickListener(v -> {
            customProgressMusic.decrease();
            musicPlayer.decreaseZvuk10();

            LoadingActivity.userLocalService.setMusicVolume(musicPlayer.getCurrentVolume());
        });

        increaseSoundButton.setOnClickListener(v -> {
            customProgressSound.increase();
            soundPlayer.increaseZvuk10();

            LoadingActivity.userLocalService.setSoundVolume(soundPlayer.getCurrentVolume());
        });

        decreaseSoundButton.setOnClickListener(v -> {
            customProgressSound.decrease();
            soundPlayer.decreaseZvuk10();

            LoadingActivity.userLocalService.setSoundVolume(soundPlayer.getCurrentVolume());
        });
    }


}