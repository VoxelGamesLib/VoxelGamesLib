package me.minidigger.voxelgameslib.api.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import me.minidigger.voxelgameslib.api.handler.Handler;
import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserData;

/**
 * A persistence provider handles loading and saving of all kinds of data
 */
public interface PersistenceProvider extends Handler {

    /**
     * Persists a user
     *
     * @param user the user to persist
     */
    void saveUserData(User user);

    /**
     * Tries to load the data for a user based on a uuid
     *
     * @param id the uuid of the user data
     * @return the userdata, if present
     */
    Optional<UserData> loadUserData(UUID id);

    /**
     * Persists a locale
     *
     * @param locale the locale to persist
     */
    void saveLocale(Locale locale);

    /**
     * @return all persisted locales
     */
    List<Locale> loadLocales();
}
