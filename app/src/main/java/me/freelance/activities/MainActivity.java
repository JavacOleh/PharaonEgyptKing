package me.freelance.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import me.freelance.R;
import me.freelance.activities.game.GameActivity;
import me.freelance.activities.game.GameStateHolder;
import me.freelance.other.layout.Layoutable;
import me.freelance.other.util.UiUtil;
import me.freelance.other.view.PanelButtonView;

public class MainActivity extends AppCompatActivity implements Layoutable {
    private PanelButtonView buttonPlay, buttonSettings, buttonRules;
    private ImageButton buttonExit;
    public static Class<? extends AppCompatActivity> prevActivty = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);

        init();
        input();

        int[] serializedGrid = getIntent().getIntArrayExtra("serializedGrid");
        int timerValue = getIntent().getIntExtra("timerValue", 60);

        if (serializedGrid != null) {
            // Сохрани состояние в каком-нибудь статическом поле, например:
            GameStateHolder.getInstance().setSerializedGrid(serializedGrid);
            GameStateHolder.getInstance().setTimerValue(timerValue);
        }

        //setContentView(R.layout.activity_main);
    }
    public void input() {
        buttonExit.setOnClickListener(v -> {
            if(prevActivty != null)
                UiUtil.loadActivityFinishCurrent(this, prevActivty);
            else
                System.exit(0);
        });

        buttonSettings.setOnClickListener(v -> {
            SettingsActivity.prevActivty = this.getClass();
            UiUtil.loadActivityPauseCurrent(this, SettingsActivity.class);
        });

        buttonRules.setOnClickListener(v -> {
            RulesActivity.prevActivty = this.getClass();
            UiUtil.loadActivityPauseCurrent(this, RulesActivity.class);
        });

        buttonPlay.setOnClickListener(v -> {
            GameActivity.startNewGame = true;
            UiUtil.loadActivityFinishCurrent(this, GameActivity.class);

            new Handler().postDelayed(() -> GameActivity.startNewGame = false, 200L);
        });
    }

    public void init() {
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonSettings = findViewById(R.id.buttonSettings);
        buttonRules = findViewById(R.id.buttonRules);
        buttonExit = findViewById(R.id.buttonExit);
    }
}