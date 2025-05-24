package me.freelance.other.layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import me.freelance.other.layout.service.LayoutService;
import me.freelance.other.util.UiUtil;

public interface Layoutable {
    LayoutService layoutService = LayoutService.getInstance();
    default void applyLayout(AppCompatActivity activity) {
        //Log.i("Enum:", LayoutSize.detectWidthAndHeight(activity).toString());
        //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int layoutId = layoutService.getLayoutForCurrent(activity, activity.getClass()); // ← это ключ!

        activity.setContentView(layoutId);
    }

    default void applyLayout(AppCompatActivity activity, Fragment fragment) {
        //activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int layoutId = layoutService.getLayoutForCurrent(activity, activity.getClass());
        UiUtil.loadFragment(activity, fragment, layoutId);
        //activity.setContentView(layoutId);
    }
}
