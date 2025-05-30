package me.freelance.other.user.service;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.util.Arrays;

import lombok.Getter;
import me.freelance.other.user.UserLocal;

public class UserLocalService {
    public static final String FILE_NAME = "user.txt";
    @Getter
    private static UserLocal userLocal;
    private Context context;
    private static UserLocalService userLocalService;
    public boolean userExist = false;

    public static UserLocalService getInstance(Context context) {
        if (userLocalService == null)
            userLocalService = new UserLocalService(context);
        return userLocalService;
    }

    private UserLocalService(Context context) {
        this.context = context;
        if (!isFileExist()) {
            userLocal = new UserLocal(
                    1.0f,
                    1.0f,
                    10,
                    10
            );
            userExist = false;
            saveUser();
        } else {
            userExist = true;
            loadUser();
        }
    }

    public void saveUser() {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fos);

            dos.writeFloat(userLocal.getMusicVolume());
            dos.writeFloat(userLocal.getSoundVolume());
            dos.writeInt(userLocal.getCurrentCountSoundVolume());
            dos.writeInt(userLocal.getCurrentCountMusicVolume());

            dos.flush();
            dos.close();
        } catch (IOException e) {
            Log.e("UserService", "Error saving user: " + Arrays.toString(e.getStackTrace()));
        }
    }
    public void loadUser() {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            DataInputStream dis = new DataInputStream(fis);

            var musicVolume = dis.readFloat();
            var soundVolume = dis.readFloat();
            var currentCountSoundVolume = dis.readInt();
            var currentCountMusicVolume = dis.readInt();

            userLocal = new UserLocal(soundVolume, musicVolume, currentCountSoundVolume, currentCountMusicVolume);

            dis.close();
        } catch (IOException | IndexOutOfBoundsException e) {
            Log.e("UserService", "Error loading user: " + Arrays.toString(e.getStackTrace()));
            userLocal = new UserLocal(
                    1.0f,
                    1.0f,
                    10,
                    10
            );
        }
    }

    public float getMusicVolume() {
        if(userLocal == null)
            loadUser();

        return userLocal.getMusicVolume();
    }

    public float getSoundVolume() {
        if(userLocal == null)
            loadUser();

        return userLocal.getSoundVolume();
    }

    public void setMusicVolume(float musicVolume) {
        if(userLocal == null)
            loadUser();

        userLocal.setMusicVolume(musicVolume);
        saveUser();
    }

    public void setSoundVolume(float soundVolume) {
        if(userLocal == null)
            loadUser();

        userLocal.setSoundVolume(soundVolume);
        saveUser();
    }

    public int getCurrentCountMusicVolume() {
        if(userLocal == null)
            loadUser();

        return userLocal.getCurrentCountMusicVolume();
    }

    public int getCurrentCountSoundVolume() {
        if(userLocal == null)
            loadUser();

        return userLocal.getCurrentCountSoundVolume();
    }

    public void setCurrentCountMusicVolume(int musicVolume) {
        if(userLocal == null)
            loadUser();

        userLocal.setCurrentCountMusicVolume(musicVolume);
        saveUser();
    }

    public void setCurrentCountSoundVolume(int soundVolume) {
        if(userLocal == null)
            loadUser();

        userLocal.setCurrentCountSoundVolume(soundVolume);
        saveUser();
    }

    private boolean isFileExist() {
        try {
            context.openFileInput(FILE_NAME);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
