package me.freelance.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import me.freelance.R;
import me.freelance.activities.game.GameActivity;
import me.freelance.other.layout.Layoutable;
import me.freelance.other.util.UiUtil;
import me.freelance.other.view.PanelButtonView;

public class WinActivity extends AppCompatActivity implements Layoutable {
    private PanelButtonView buttonPlay, buttonSettings, buttonRules;
    private ImageButton buttonExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);

        init();
        input();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GameActivity.startNewGame = true;
    }

    public void input() {
        buttonExit.setOnClickListener(v -> {
            UiUtil.loadActivityFinishCurrent(this, MainActivity.class);
            MainActivity.prevActivty = null;
        });

        buttonSettings.setOnClickListener(v -> {
            SettingsActivity.prevActivty = MainActivity.class;
            UiUtil.loadActivityFinishCurrent(this, SettingsActivity.class);
        });

        buttonRules.setOnClickListener(v -> {
            RulesActivity.prevActivty = MainActivity.class;
            UiUtil.loadActivityFinishCurrent(this, RulesActivity.class);
        });

        buttonPlay.setOnClickListener(v -> UiUtil.loadActivityFinishCurrent(this, GameActivity.class));
    }

    public void init() {
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonSettings = findViewById(R.id.buttonSettings);
        buttonRules = findViewById(R.id.buttonRules);
        buttonExit = findViewById(R.id.buttonExit);
    }
}