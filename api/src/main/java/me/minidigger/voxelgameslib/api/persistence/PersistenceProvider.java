package me.minidigger.voxelgameslib.api.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import me.minidigger.voxelgameslib.api.handler.Handler;
import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserData;

/**
 * Created by Martin on 29.01.2017.
 */
public interface PersistenceProvider extends Handler {

    void saveUserData(User user);

    Optional<UserData> loadUserData(UUID id);

    void saveLocale(Locale locale);

    List<Locale> loadLocales();
}
