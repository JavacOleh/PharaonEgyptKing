package me.freelance.activities.game.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import me.freelance.activities.game.item.GameItem;

public class GridGenerator {

    public static GameItem[][] generateGrid(List<Integer> itemResIds, int rows, int cols) {
        List<List<GameItem>> positions = new ArrayList<>();
        Random random = new Random();

        for (int r = 0; r < rows; r++) {
            if (itemResIds.isEmpty()) {
                throw new IllegalArgumentException("Недостаточно ресурсов для заполнения сетки");
            }
            int ind = random.nextInt(itemResIds.size());
            int selectedRes = itemResIds.get(ind);
            itemResIds.remove(ind);

            List<GameItem> rowItems = new ArrayList<>();
            for (int c = 0; c < cols; c++) {
                rowItems.add(new GameItem(selectedRes, r, c));
            }
            positions.add(rowItems);
        }

        Collections.shuffle(positions);

        GameItem[][] grid = new GameItem[rows][cols];

        for (int r = 0; r < rows; r++) {
            List<GameItem> rowItems = positions.get(r);
            for (int c = 0; c < cols; c++) {
                grid[r][c] = rowItems.get(c);
            }
        }

        List<GameItem> allItems = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                allItems.add(grid[r][c]);
            }
        }

        Collections.shuffle(allItems);

        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                GameItem item = allItems.get(index++);
                item.row = r;
                item.col = c;
                grid[r][c] = item;
            }
        }

        return grid;
    }
}