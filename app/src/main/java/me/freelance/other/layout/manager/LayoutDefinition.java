package me.freelance.other.layout.manager;

import java.util.ArrayList;
import java.util.List;

import me.freelance.activities.FailActivity;
import me.freelance.activities.RulesActivity;
import me.freelance.activities.SettingsActivity;
import me.freelance.activities.LoadingActivity;
import me.freelance.R;
import me.freelance.activities.MainActivity;
import me.freelance.activities.WinActivity;
import me.freelance.activities.game.GameActivity;
import me.freelance.other.layout.data.Layout;
import me.freelance.other.layout.size.LayoutSize;
import me.freelance.other.layout.size.enums.LayoutHeight;
import me.freelance.other.layout.size.enums.LayoutWidth;


public enum LayoutDefinition {
    /*
    // === Loading ===
    Load(LoadActivity.class, R.layout.activity_load, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 1 ===
    Step1(Step1Activity.class, R.layout.activity_step1, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 2 ===
    Step2_SmallPhone(Step2Activity.class, R.layout.activity_step2_smallphone, "SmallPhone", LayoutHeight.Small, LayoutWidth.Small),
    Step2(Step2Activity.class, R.layout.activity_step2, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 3 ===
    Step3(Step3Activity.class, R.layout.activity_step3, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 4 ===
    Step4(Step4Activity.class, R.layout.activity_step4, "All Screens", LayoutHeight.High, LayoutWidth.Middle),

    // === Step 5 ===
    Step5(Step5Activity.class, R.layout.activity_step5, "All Screens",LayoutHeight.High, LayoutWidth.Middle),

    // === Step 6 ===
    Step6(Step6Activity.class, R.layout.activity_step6, "All Screens",LayoutHeight.High, LayoutWidth.Middle),

    // === Step 7 ===
    Step7(Step7Activity .class, R.layout.activity_step7, "All Screens",LayoutHeight.High, LayoutWidth.Middle),

    // === Step 8 ===
    Step8(Step8Activity .class, R.layout.activity_step8, "All Screens",LayoutHeight.High, LayoutWidth.Middle),

    // === Home Screen ===
    Home_screen(HomeScreenActivity.class, R.layout.activity_homescreen, "All Screens",LayoutHeight.High, LayoutWidth.Middle);
     */
    Loading(LoadingActivity.class, R.layout.activity_loading, "basic", LayoutHeight.High, LayoutWidth.Middle),
    Settings(SettingsActivity.class, R.layout.activity_settings, "basic", LayoutHeight.High, LayoutWidth.Middle),
    Settings_SmallScreen(SettingsActivity.class, R.layout.activity_settings_smallscreen, "smallScreen", LayoutHeight.Small, LayoutWidth.Small),
    Rules(RulesActivity.class, R.layout.activity_rules, "basic", LayoutHeight.High, LayoutWidth.Middle),
    Win(WinActivity.class, R.layout.activity_win, "basic", LayoutHeight.High, LayoutWidth.Middle),
    Fail(FailActivity.class, R.layout.activity_fail, "basic", LayoutHeight.High, LayoutWidth.Middle),
    Game(GameActivity.class, R.layout.activity_game, "basic", LayoutHeight.High, LayoutWidth.Middle),
    Game_SmallScreen(GameActivity.class, R.layout.activity_game_smallscreen, "basic", LayoutHeight.Small, LayoutWidth.Small),
    Main(MainActivity.class, R.layout.activity_main, "basic", LayoutHeight.High, LayoutWidth.Middle);
    public final Class<?> activityClass;
    public final Layout layout;
    public final String deviceName;

    LayoutDefinition(Class<?> activityClass, int layoutId, String deviceName, LayoutHeight h, LayoutWidth w) {
        this.activityClass = activityClass;
        this.layout = new Layout(layoutId, new LayoutSize(h, w));
        this.deviceName = deviceName;
    }

    public static List<LayoutDefinition> filterByDeviceName(String name) {
        List<LayoutDefinition> result = new ArrayList<>();
        for (LayoutDefinition def : values()) {
            if (def.deviceName.toLowerCase().contains(name.toLowerCase())) {
                result.add(def);
            }
        }
        return result;
    }
    public static LayoutDefinition getByClassAndSize(Class<?> clazz, LayoutSize size) {
        for (LayoutDefinition def : values()) {
            if (def.activityClass.equals(clazz) && def.layout.layoutSize.equals(size)) {
                return def;
            }
        }
        return null;
    }

}
