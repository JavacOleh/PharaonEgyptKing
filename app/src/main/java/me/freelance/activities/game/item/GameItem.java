package me.freelance.activities.game.item;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GameItem {
    public int resId; // ресурс картинки
    public int row, col; // текущие координаты в сетке
    public boolean isSelected = false; // выбран ли элемент

    public GameItem(int resId, int row, int col) {
        this.resId = resId;
        this.row = row;
        this.col = col;
    }
}