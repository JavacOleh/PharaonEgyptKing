package me.freelance.other.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import me.freelance.R;

public class LoaderDrawableView extends View {

    private Bitmap bitmap;
    private Paint paint;
    private float rotation = 0f;
    private ObjectAnimator rotationAnimator;
    private int imageResId = R.drawable.loading_ellipse;

    private int segmentCount = 12; // число сегментов
    private float[] segmentAlphas; // прозрачность сегментов
    private ValueAnimator pulseAnimator;

    public LoaderDrawableView(Context context) {
        super(context);
        init();
    }

    public LoaderDrawableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoaderDrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), imageResId);
        paint = new Paint();
        paint.setAntiAlias(true);
        segmentAlphas = new float[segmentCount];

        for (int i = 0; i < segmentCount; i++) {
            segmentAlphas[i] = 1f; // все сегменты видимы изначально
        }

        // Запуск вращения
        rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);
        rotationAnimator.setDuration(2000);
        rotationAnimator.setInterpolator(new LinearInterpolator());
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        rotationAnimator.start();

        // Анимация исчезания/появления сегментов
        startPulseAnimation();
    }

    private void startPulseAnimation() {
        pulseAnimator = ValueAnimator.ofFloat(0f, 1f);
        pulseAnimator.setDuration(1500);
        pulseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        pulseAnimator.setRepeatMode(ValueAnimator.REVERSE);
        pulseAnimator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            updateSegmentAlphas(progress);
            invalidate();
        });
        pulseAnimator.start();
    }

    private void updateSegmentAlphas(float progress) {
        // Для эффекта пропадания/появления делим сегменты на группы
        int activeSegment = (int) (progress * segmentCount);
        for (int i = 0; i < segmentCount; i++) {
            if (i == activeSegment) {
                segmentAlphas[i] = 1f; // видим
            } else {
                segmentAlphas[i] = 0f; // пропадаем
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Центр и радиус
        int w = getWidth();
        int h = getHeight();
        float centerX = w / 2f;
        float centerY = h / 2f;
        float radius = Math.min(w, h) / 2f * 0.8f;

        // Рисуем вращающееся изображение
        if (bitmap != null) {
            // Масштабируем изображение под размер View
            RectF destRect = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            // Устанавливаем прозрачность сегментов
            paint.setAlpha(255);
            // Можно рисовать изображение полностью
            canvas.save();
            // Вращение
            canvas.rotate(rotation, centerX, centerY);
            canvas.drawBitmap(bitmap, null, destRect, paint);
            canvas.restore();
        }

        // Можно добавить сегменты в виде оверлея или маски, если нужно
        // Или рисовать полукруги, маски с прозрачностью
        // В этом примере изображение полностью вращается
    }

    // Обновление вращения
    public void setRotation(float rotation) {
        this.rotation = rotation;
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (rotationAnimator != null) {
            rotationAnimator.cancel();
        }
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
        }
    }
}