package me.freelance.activities.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import me.freelance.R;
import me.freelance.activities.FailActivity;
import me.freelance.activities.LoadingActivity;
import me.freelance.activities.MainActivity;
import me.freelance.activities.SettingsActivity;
import me.freelance.activities.game.animation.ButtonsAnimator;
import me.freelance.activities.game.item.GameItem;
import me.freelance.activities.game.engine.GameEngine;
import me.freelance.other.layout.Layoutable;
import me.freelance.other.util.UiUtil;
import me.freelance.other.view.PanelButtonView;

public class GameActivity extends AppCompatActivity implements Layoutable {
    public static boolean startNewGame = false;
    private static final int ROWS = 4;
    private static final int COLS = 4;
    private GridLayout gameGrid;
    public static GameEngine gameEngine;
    private ImageView[][] imageViews = new ImageView[ROWS][COLS];
    PanelButtonView gameTimer;
    ImageButton settingsButton, pauseButton;
    ButtonsAnimator animator;
    Handler timerHandler = new Handler(Looper.getMainLooper());
    int size = 110; //getResources().getDisplayMetrics().widthPixels / COLS - 115
    int horizontalMargin = 0; // px, или dp переведённый в px
    int verticalMargin = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyLayout(this);

        init();
        input();

        gameEngine = new GameEngine(this);
        size = Layoutable.super.getCellSizeDp(this);
        // Инициализация UI сетки
        setupGrid();
        updateGrid();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        String text = gameTimer.getTextView().getText().toString().trim();
//        int currentTime = Integer.parseInt(text);
//
//        runTimer(currentTime);

        int[] savedGrid = GameStateHolder.getInstance().getSerializedGrid();
        int savedTimer = GameStateHolder.getInstance().getTimerValue();

        if(!startNewGame) {
            if (savedGrid != null) {
                gameEngine.deserializeGrid(savedGrid);
                updateGrid();
                gameTimer.setText(String.valueOf(savedTimer));
                // Можно запустить таймер с сохранённого времени
                runTimer(savedTimer);
            } else {
                // стартовая логика, если нет сохраненного состояния
                runTimer(Integer.parseInt(gameTimer.getTextView().getText().toString()));
            }
        }else {
            runTimer(Integer.parseInt(gameTimer.getTextView().getText().toString()));
        }
    }

    private void runTimer(int startTime) {
        final int[] time = {startTime};
        final AppCompatActivity activity = this; // захватываем ссылку на Activity

        timerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (time[0] > 0) {
                    time[0]--;
                    gameTimer.setText(String.valueOf(time[0]));
                    timerHandler.postDelayed(this, 1000);
                } else {
                    UiUtil.loadActivityFinishCurrent(activity, FailActivity.class);
                    startNewGame = true;
                }
            }
        }, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerHandler.removeCallbacksAndMessages(null); // Удаляет все отложенные задачи
        //startNewGame = true;
    }

    public void input() {
        settingsButton.setOnClickListener(v ->
                animator.setForSettings(
                        R.drawable.settings_pressed,
                        R.drawable.ic_settings,
                        200L,
                        () -> {
                            SettingsActivity.prevActivty = this.getClass();
                            int[] serializedGrid = gameEngine.serializeGrid();
                            Intent intent = new Intent(this, SettingsActivity.class);
                            intent.putExtra("serializedGrid", serializedGrid);
                            intent.putExtra("timerValue", Integer.parseInt(gameTimer.getTextView().getText().toString()));
                            startActivity(intent);
                        }
                )
        );

        pauseButton.setOnClickListener(v ->
                animator.setForPause(
                        R.drawable.pause_pressed,
                        R.drawable.ic_pause,
                        200L,
                        () -> {
                            MainActivity.prevActivty = this.getClass();
                            int[] serializedGrid = gameEngine.serializeGrid();
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.putExtra("serializedGrid", serializedGrid);
                            intent.putExtra("timerValue", Integer.parseInt(gameTimer.getTextView().getText().toString()));
                            startActivity(intent);
                        }
                )
        );
    }

    public void init() {
        gameTimer = findViewById(R.id.game_timer);
        settingsButton = findViewById(R.id.settingsButton);
        pauseButton = findViewById(R.id.pauseButton);
        animator = new ButtonsAnimator(settingsButton, pauseButton);
        gameGrid = findViewById(R.id.gameGrid);
    }

    private void setupGrid() {
        gameGrid.setRowCount(ROWS);
        gameGrid.setColumnCount(COLS);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                ImageView iv = new ImageView(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = size;
                params.height = size;
                params.columnSpec = GridLayout.spec(c);
                params.rowSpec = GridLayout.spec(r);

                params.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);

                iv.setLayoutParams(params);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                final int row = r;
                final int col = c;
                iv.setOnClickListener(v -> {
                    gameEngine.onItemClick(row, col);
                    updateGrid();

                    if (checkWin()) {
                        // Обработка победы произхоид уже в Engine
                    }
                });
                gameGrid.addView(iv);
                imageViews[r][c] = iv;
            }
        }
    }

    private void updateGrid() {
        GameItem[][] grid = gameEngine.getGrid();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                GameItem item = grid[r][c];
                imageViews[r][c].setImageResource(item.resId);
                // Можно добавить подсветку выбранных
                if (item.isSelected) {
                    imageViews[r][c].setBackgroundColor(0x33FF0000); // полупрозрачный красный
                } else {
                    imageViews[r][c].setBackgroundColor(0x00000000); // прозрачный
                }
            }
        }
    }

    private boolean checkWin() {
        if(gameEngine.isWin())
            startNewGame = true;

        return gameEngine.isWin();
    }
}