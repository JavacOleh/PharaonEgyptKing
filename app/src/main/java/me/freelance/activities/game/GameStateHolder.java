package me.freelance.activities.game;

public class GameStateHolder {
    private static GameStateHolder instance;
    private int[] serializedGrid;
    private int timerValue;

    private GameStateHolder() {}

    public static synchronized GameStateHolder getInstance() {
        if (instance == null) {
            instance = new GameStateHolder();
        }
        return instance;
    }

    public int[] getSerializedGrid() {
        return serializedGrid;
    }

    public void setSerializedGrid(int[] serializedGrid) {
        this.serializedGrid = serializedGrid;
    }

    public int getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }
}
