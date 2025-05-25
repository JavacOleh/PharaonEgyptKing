package me.freelance.other.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLocal {
    private float soundVolume;
    private float musicVolume;
    private int currentCountSoundVolume;
    private int currentCountMusicVolume;
}
