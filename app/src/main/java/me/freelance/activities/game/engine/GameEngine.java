package me.freelance.activities.game.engine;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import me.freelance.R;
import me.freelance.activities.LoadingActivity;
import me.freelance.activities.WinActivity;
import me.freelance.activities.game.GameActivity;
import me.freelance.activities.game.item.GameItem;
import me.freelance.other.util.UiUtil;

public class GameEngine {
    private static final int ROWS = 4;
    private static final int COLS = 4; // допустим, 4x4 сетка
    @Getter
    private GameItem[][] grid = new GameItem[ROWS][COLS];
    private GameItem selectedItem = null;
    private GameActivity gameActivity;


    public GameEngine(GameActivity gameActivity) {
        initGameItems();
        this.gameActivity = gameActivity;
    }

    private void initGameItems() {
        List<Integer> itemResIds = new ArrayList<>(Arrays.asList(
                R.drawable.play_item_1,
                R.drawable.play_item_2,
                R.drawable.play_item_3,
                R.drawable.play_item_4,
                R.drawable.play_item_5,
                R.drawable.play_item_6
        ));

        grid = GridGenerator.generateGrid(new ArrayList<>(itemResIds), ROWS, COLS);
    }

    // Обработка клика по элементу
    public void onItemClick(int row, int col) {
        GameItem clickedItem = grid[row][col];
        if (selectedItem == null) {
            // Выбираем первый элемент
            selectedItem = clickedItem;
            selectedItem.isSelected = true;
        } else {
            // Есть выбранный элемент, пытаемся его переместить
            if (canMove(selectedItem, clickedItem)) {
                swapItems(selectedItem, clickedItem);
                selectedItem.isSelected = false;
                selectedItem = null;
                checkWinCondition();
            } else {
                // Нельзя переместить
                // Можно сбросить выбор или оставить
                selectedItem.isSelected = false;
                selectedItem = null;
            }
        }
    }

    // Проверка, можно ли переместить выбранный элемент на место другого
    private boolean canMove(GameItem from, GameItem to) {
        // В рамках границ сетки и только на соседние ячейки
        int dr = Math.abs(from.row - to.row);
        int dc = Math.abs(from.col - to.col);
        return (dr + dc == 1); // соседняя ячейка
    }

    // Меняем местами элементы
    private void swapItems(GameItem a, GameItem b) {
        // Обмен значениями
        int tempResId = a.resId;
        a.resId = b.resId;
        b.resId = tempResId;
        var soundPlayer = LoadingActivity.soundPlayer;
        if(soundPlayer != null) {
            LoadingActivity.soundPlayer.play(R.raw.swap_sound);
            new Handler().postDelayed(soundPlayer::stop, 500L);
        }
    }

    // Проверка выигрышных условий
    private void checkWinCondition() {
        // Можно проверять каждую строку
        boolean allRowsComplete = true;

        for (int r = 0; r < ROWS; r++) {
            int firstResId = grid[r][0].resId;
            for (int c = 1; c < COLS; c++) {
                if (grid[r][c].resId != firstResId) {
                    allRowsComplete = false;
                    break;
                }
            }
            if (!allRowsComplete) break;
        }

        if (allRowsComplete) {
            // Все ряды собраны правильно
            onWin();
        }
    }
    public boolean isWin() {
        // Проверка для каждого ряда
        for (int r = 0; r < ROWS; r++) {
            int firstResId = grid[r][0].resId;
            for (int c = 1; c < COLS; c++) {
                if (grid[r][c].resId != firstResId) {
                    return false;
                }
            }
        }
        return true;
    }
    private void onWin() {
        UiUtil.loadActivityFinishCurrent(gameActivity, WinActivity.class);
    }

    public int[] serializeGrid() {
        int[] serialized = new int[ROWS * COLS];
        int index = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                serialized[index++] = grid[r][c].resId;
            }
        }
        return serialized;
    }

    public void deserializeGrid(int[] serialized) {
        GameItem[][] newGrid = new GameItem[ROWS][COLS];
        int index = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int resId = serialized[index++];
                newGrid[r][c] = new GameItem(resId, r, c);
            }
        }
        this.grid = newGrid;
    }

}