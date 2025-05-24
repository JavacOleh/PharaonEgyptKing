package me.freelance.other.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import me.freelance.R;

public class PanelButtonView extends FrameLayout {

    private ImageView imageView;
    private TextView textView;

    public PanelButtonView(Context context) {
        super(context);
        init(context, null);
    }

    public PanelButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PanelButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_panel_button, this, true);

        imageView = findViewById(R.id.panel_image);
        textView = findViewById(R.id.panel_text);

        setClickable(true);
        setFocusable(true);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextButton);

            int imageResId = a.getResourceId(R.styleable.ImageTextButton_imageSrc, -1);
            if (imageResId != -1) {
                imageView.setImageResource(imageResId);
            }

            String text = a.getString(R.styleable.ImageTextButton_buttonText);
            if (text != null) {
                textView.setText(text);
            }

            float textSize = a.getDimension(R.styleable.ImageTextButton_buttonTextSize, -1f);
            if (textSize > 0) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize); // Важно!
            }

            int textColor = a.getColor(R.styleable.ImageTextButton_buttonTextColor, -1);
            if (textColor != -1) {
                textView.setTextColor(textColor);
            }


            a.recycle();
        }
    }

    // Опционально: методы для доступа к элементам
    public void setText(String text) {
        textView.setText(text);
        invalidate();
    }

    public void setImageResource(int resId) {
        imageView.setImageResource(resId);
        invalidate();
    }

    public TextView getTextView() {
        return textView;
    }

    public ImageView getImageView() {
        return imageView;
    }
}


