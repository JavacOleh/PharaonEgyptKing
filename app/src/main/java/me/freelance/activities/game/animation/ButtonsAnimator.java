package me.freelance.activities.game.animation;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ButtonsAnimator {
    ImageButton settingsButton, pauseButton;

    private final Handler handler = new Handler(Looper.getMainLooper());

    /**
     * Анимация замены изображения для settingsButton
     */
    public void setForSettings(int temporaryResId, int originalResId, long delayMs, Runnable action) {
        animateButtonImage(settingsButton, temporaryResId, originalResId, delayMs, action);
    }

    /**
     * Анимация замены изображения для pauseButton
     */
    public void setForPause(int temporaryResId, int originalResId, long delayMs, Runnable action) {
        animateButtonImage(pauseButton, temporaryResId, originalResId, delayMs, action);
    }

    /**
     * Общий метод анимации: сменить картинку и вернуть обратно
     */
    private void animateButtonImage(ImageButton button, int tempResId, int originalResId, long delayMs, Runnable action) {
        if (button == null) return;

        button.setImageResource(tempResId);
        handler.postDelayed(() -> {
            button.setImageResource(originalResId);

            if (action != null)
                handler.postDelayed(action, 100L);
        }, delayMs);
    }
}
