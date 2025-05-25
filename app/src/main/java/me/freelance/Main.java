package me.freelance;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import me.freelance.activities.LoadingActivity;

public class Main extends Application {

    private int activityReferences = 0;
    private boolean isChangingConfigurations = false;

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStarted(Activity activity) {
                if (++activityReferences == 1 && !isChangingConfigurations) {
                    if (LoadingActivity.musicPlayer != null && !LoadingActivity.musicPlayer.isPlaying()) {
                        LoadingActivity.musicPlayer.resume(); // Или stop(), если нужно
                    }
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
                isChangingConfigurations = activity.isChangingConfigurations();
                if (--activityReferences == 0 && !isChangingConfigurations) {

                    if (LoadingActivity.musicPlayer != null && LoadingActivity.musicPlayer.isPlaying()) {
                        LoadingActivity.musicPlayer.pause(); // Или stop(), если нужно
                    }
                }
            }

            // Остальные можно опустить
            @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}
            @Override public void onActivityResumed(Activity activity) {}
            @Override public void onActivityPaused(Activity activity) {}
            @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}
            @Override public void onActivityDestroyed(Activity activity) {}
        });
    }
}

