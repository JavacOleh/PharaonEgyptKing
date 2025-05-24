package me.freelance.other.music;

import android.content.Context;
import android.media.MediaPlayer;

public class Mp3Player {
    private MediaPlayer mediaPlayer;
    private Context context;
    private int currentResId = -1;

    // Текущий уровень громкости (от 0.0 до 1.0)
    private float currentVolume = 1.0f; // по умолчанию максимальный

    public Mp3Player(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Проигрывать указанный ресурс
     * @param resId ресурс из R.raw.*
     */
    public void play(int resId) {
        // Остановить и освободить текущий плеер, если он есть
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context, resId);
        currentResId = resId;
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(currentVolume, currentVolume); // установить текущий уровень громкости
        mediaPlayer.start();
    }

    /**
     * Поставить на паузу
     */
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * Продолжить воспроизведение
     */
    public void resume() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    /**
     * Остановить воспроизведение и освободить ресурсы
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            currentResId = -1;
        }
    }

    /**
     * Проверить, проигрывается ли музыка
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    /**
     * Получить текущий проигрываемый ресурс
     */
    public int getCurrentResId() {
        return currentResId;
    }

    /**
     * Увеличить громкость на 10%
     */
    public void increaseZvuk10() {
        setVolumeByDelta(0.1f);
    }

    /**
     * Уменьшить громкость на 10%
     */
    public void decreaseZvuk10() {
        setVolumeByDelta(-0.1f);
    }

    /**
     * Общий метод для изменения громкости на delta (от -1.0 до 1.0)
     */
    public void setVolumeByDelta(float delta) {
        currentVolume += delta;
        if (currentVolume > 1.0f) {
            currentVolume = 1.0f;
        } else if (currentVolume < 0.0f) {
            currentVolume = 0.0f;
        }
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(currentVolume, currentVolume);
        }
    }


    public void setCurrentVolume(float currentVolume) {
        this.currentVolume = currentVolume;
    }

    public float getCurrentVolume() {
        return currentVolume;
    }
}