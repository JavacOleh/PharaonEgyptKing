package me.freelance.other.layout;

import android.content.pm.ActivityInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import me.freelance.other.layout.service.LayoutService;
import me.freelance.other.layout.size.LayoutSize;
import me.freelance.other.layout.size.enums.LayoutHeight;
import me.freelance.other.layout.size.enums.LayoutWidth;
import me.freelance.other.util.UiUtil;

public interface Layoutable {
    LayoutService layoutService = LayoutService.getInstance();
    default void applyLayout(AppCompatActivity activity) {
        //Log.i("Enum:", LayoutSize.detectWidthAndHeight(activity).toString());
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int layoutId = layoutService.getLayoutForCurrent(activity, activity.getClass()); // ← это ключ!
        activity.setContentView(layoutId);
    }

    default void applyLayout(AppCompatActivity activity, Fragment fragment) {
        //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int layoutId = layoutService.getLayoutForCurrent(activity, activity.getClass());
        UiUtil.loadFragment(activity, fragment, layoutId);
        //activity.setContentView(layoutId);
    }

    default int getCellSizeDp(AppCompatActivity activity) {
        var layoutSize = LayoutSize.detect(activity);
        var h = layoutSize.height;
        var w = layoutSize.width;

        // Пример логики
        if (w == LayoutWidth.Middle && h == LayoutHeight.Big) return UiUtil.dpToPx(activity, 70); //пересмотреть в будущем
        if (w == LayoutWidth.High && h == LayoutHeight.High) return UiUtil.dpToPx(activity, 70); //пересмотреть в будущем
        if (w == LayoutWidth.Middle && h == LayoutHeight.High) return UiUtil.dpToPx(activity, 70); //пересмотреть в будущем
        if (w == LayoutWidth.Small || h == LayoutHeight.Small) return UiUtil.dpToPx(activity, 45);

        // Фоллбэк
        return UiUtil.dpToPx(activity, 70);
    }
}
