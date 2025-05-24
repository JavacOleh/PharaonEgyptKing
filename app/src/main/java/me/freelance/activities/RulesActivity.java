package me.freelance.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import me.freelance.R;
import me.freelance.other.layout.Layoutable;
import me.freelance.other.util.UiUtil;

public class RulesActivity extends AppCompatActivity implements Layoutable {
    private ImageButton exitButton;
    public static Class<? extends AppCompatActivity> prevActivty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);

        exitButton = findViewById(R.id.buttonExit);

        exitButton.setOnClickListener(v -> {
            UiUtil.loadActivityPauseCurrent(this, prevActivty);

            if(prevActivty.equals(MainActivity.class))
                MainActivity.prevActivty = null;
        });
    }
}