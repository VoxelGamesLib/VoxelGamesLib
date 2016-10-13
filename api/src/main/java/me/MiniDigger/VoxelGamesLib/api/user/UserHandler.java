package me.MiniDigger.VoxelGamesLib.api.user;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.java.Log;
import me.MiniDigger.VoxelGamesLib.api.exception.UserException;
import me.MiniDigger.VoxelGamesLib.api.handler.Handler;
import me.MiniDigger.VoxelGamesLib.api.utils.ChatUtil;

@Log
@Singleton
public class UserHandler implements Handler {

    private Map<UUID, User> users;
    private Map<UUID, Object> tempData;

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
     * Adds a users, if not already added
     *
     * @param user the user to add
     * @throws UserException when the player was not logged in
     */
    public void join(User user) {
        if (!hasLoggedIn(user.getUUID())) {
            throw new UserException("User " + user.getUUID() + "(" + ChatUtil.toPlainText(user.getDisplayName()) + ") tried to join without beeing logged in!");
        }

        if (!users.containsKey(user.getUUID())) {
            users.put(user.getUUID(), user);
        }

        //TODO apply loaded data
        Object temp = tempData.remove(user.getUUID());
        log.info("Applied data for user " + user.getUUID() + "(" + ChatUtil.toPlainText(user.getDisplayName()) + ")");
    }

    /**
     * Handles logout
     *
     * @param id the uuid of the user that logged out
     */
    public void logout(UUID id) {
        users.remove(id);
        tempData.remove(id);
    }

    /**
     * searches for a user with that uuid
     *
     * @param id the uuid of the user
     * @return the user with that uuid, if present
     */
    public Optional<User> getUser(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    /**
     * Called when a user logs in. used to load all kind of stuff
     */
    public void login(UUID uniqueId) {
        Thread t = new Thread() {
            @Override
            public void run() {
                log.info("Loading data for user " + uniqueId);
                // TODO load roles and stuff from somewhere
                tempData.put(uniqueId, "HEYHO");
            }
        };
        t.setName("LoginTask: " + uniqueId);
        t.start();
    }

    /**
     * Checks if a user has logged in (if the data was loaded successfully)
     *
     * @return true if everything is good, false if the data was not loaded
     */
    public boolean hasLoggedIn(UUID uniqueId) {
        return tempData.containsKey(uniqueId);
    }
}
