package me.freelance.other.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import me.freelance.R;

public class ImageProgressView extends View {
    private Drawable[] images;
    private int maxCount = 10;          // по умолчанию
    private int currentCount = maxCount;
    private int backgroundResId = 0;    // по умолчанию
    private int itemImageResId = 0;     // по умолчанию
    private int itemMarginHorizontalPx = 5;
    private int itemMarginVerticalPx = 25;
    private int itemPaddingHorizontalPx = 25;

    public ImageProgressView(Context context) {
        super(context);
        init(null);
    }

    public ImageProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ImageProgressView);
            maxCount = a.getInt(R.styleable.ImageProgressView_maxItems, 10);
            backgroundResId = a.getResourceId(R.styleable.ImageProgressView_backgroundRes, 0);
            itemImageResId = a.getResourceId(R.styleable.ImageProgressView_itemImageRes, 0);
            a.recycle();
        }

        if (itemImageResId != 0) {
            // создаем массив из одного ресурса
            Drawable drawable = ContextCompat.getDrawable(getContext(), itemImageResId);
            images = new Drawable[maxCount];
            for (int i = 0; i < maxCount; i++) {
                images[i] = drawable;
            }
        }
    }

    /**
     * Можно дополнительно вызывать для установки ресурсов программно
     */
    public void setImageResource(@DrawableRes int imageResId, int maxItems, int backgroundRes) {
        this.maxCount = maxItems;
        this.backgroundResId = backgroundRes;
        this.itemImageResId = imageResId;
        if (imageResId != 0) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), imageResId);
            images = new Drawable[maxCount];
            for (int i = 0; i < maxCount; i++) {
                images[i] = drawable;
            }
        }
        invalidate();
    }

    public void setItemMarginDp(int horizontalDp, int verticalDp) {
        float density = getResources().getDisplayMetrics().density;
        this.itemMarginHorizontalPx = (int) (horizontalDp * density + 0.5f);
        this.itemMarginVerticalPx = (int) (verticalDp * density + 0.5f);
        invalidate();
    }

    public void setItemPaddingHorizontalPx(int paddingHorizontalPx) {
        float density = getResources().getDisplayMetrics().density;
        this.itemPaddingHorizontalPx = (int) (paddingHorizontalPx * density + 0.5f); // фиксированный padding 5dp
        invalidate();
    }

    public void increase() {
        if (currentCount < maxCount) {
            currentCount++;
            invalidate();
        }
    }

    public void decrease() {
        if (currentCount > 0) {
            currentCount--;
            invalidate();
        }
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (backgroundResId != 0) {
            Drawable bg = getResources().getDrawable(backgroundResId);
            bg.setBounds(0, 0, getWidth(), getHeight());
            bg.draw(canvas);
        }

        if (images == null || images.length == 0 || currentCount == 0) return;

        int width = getWidth();
        int height = getHeight();

        // Учитываем отступы в начале и конце
        int totalHorizontalMargin = itemMarginHorizontalPx * (maxCount - 1);
        int totalPadding = itemPaddingHorizontalPx * 2;

        float itemWidth = (float) (width - totalHorizontalMargin - totalPadding) / maxCount;

        int top = itemMarginVerticalPx;
        int bottom = height - itemMarginVerticalPx;

        for (int i = 0; i < currentCount; i++) {
            Drawable drawable = images[i];

            int left = (int) (itemPaddingHorizontalPx + i * (itemWidth + itemMarginHorizontalPx));
            int right = (int) (left + itemWidth);

            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }
}