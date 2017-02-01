package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.exception.UserException;
import me.minidigger.voxelgameslib.api.game.GameHandler;
import me.minidigger.voxelgameslib.api.handler.Handler;
import me.minidigger.voxelgameslib.api.persistence.PersistenceHandler;
import me.minidigger.voxelgameslib.api.utils.ChatUtil;

import lombok.extern.java.Log;

@Log
@Singleton
public class UserHandler implements Handler {

    @Inject
    private GameHandler gameHandler;
    @Inject
    private PersistenceHandler persistenceHandler;
    @Inject
    private Injector injector;

    private Map<UUID, User> users;
    private Map<UUID, User> tempData;

    @Override
    public void start() {
        users = new HashMap<>();
        tempData = new ConcurrentHashMap<>();
    }

    @Override
    public void stop() {
        users.clear();
        tempData.clear();
    }

    /**
     * Creates the user object for a new user
     *
     * @param uuid         the uuid of the new user
     * @param playerObject the implementation type of the user
     * @throws UserException when the player was not logged in
     */
    public void join(@Nonnull UUID uuid, @Nonnull Object playerObject) {
        if (!hasLoggedIn(uuid)) {
            throw new UserException("User " + uuid + " tried to join without being logged in!");
        }

        User user = tempData.remove(uuid);
        //noinspection unchecked
        user.setImplementationType(playerObject);
        injector.injectMembers(user);
        users.put(user.getUuid(), user);
        log.info("Applied data for user " + user.getUuid() + "(" + ChatUtil.toPlainText(user.getDisplayName()) + ")");
    }

    /**
     * Handles logout.
     *
     * @param id the uuid of the user that logged out
     */
    public void logout(@Nonnull UUID id) {
        Optional<User> user = getUser(id);
        if (user.isPresent()) {
            //TODO go away, gamehandler, use the events!
            gameHandler.getGames(user.get(), true).forEach(game -> game.leave(user.get()));
            persistenceHandler.getProvider().saveUser(user.get());
        }

        users.remove(id);
        tempData.remove(id);
    }

    /**
     * searches for a user with that uuid
     *
     * @param id the uuid of the user
     * @return the user with that uuid, if present
     */
    @Nonnull
    public Optional<User> getUser(@Nonnull UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    /**
     * Called when a user logs in. used to load all kind of stuff. Should only be called async!
     *
     * @param uniqueId the id of the user that logged in
     */
    public void login(@Nonnull UUID uniqueId) {
        log.info("Loading data for user " + uniqueId);

        Optional<User> user = persistenceHandler.getProvider().loadUser(uniqueId);
        if (user.isPresent()) {
            tempData.put(uniqueId, user.get());
        } else {
            User newUser = injector.getInstance(User.class);
            tempData.put(uniqueId, newUser);
        }
    }

    /**
     * Checks if a user has logged in (if the data was loaded successfully)
     *
     * @param uniqueId the id of the user that should be checked
     * @return true if everything is good, false if the data was not loaded
     */
    public boolean hasLoggedIn(@Nonnull UUID uniqueId) {
        return tempData.containsKey(uniqueId);
    }

}
