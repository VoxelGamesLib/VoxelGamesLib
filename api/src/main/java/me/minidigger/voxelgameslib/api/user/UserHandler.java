package me.minidigger.voxelgameslib.api.user;

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
import me.minidigger.voxelgameslib.api.utils.ChatUtil;

import lombok.extern.java.Log;

@Log
@Singleton
public class UserHandler implements Handler {
    
    @Inject
    private GameHandler gameHandler;
    
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
    public void join(@Nonnull User user) {
        if (!hasLoggedIn(user.getUuid())) {
            throw new UserException("User " + user.getUuid() + "(" + ChatUtil.toPlainText(user.getDisplayName()) + ") tried to join without being logged in!");
        }
        
        if (!users.containsKey(user.getUuid())) {
            users.put(user.getUuid(), user);
        }
        
        //TODO apply loaded data
        Object temp = tempData.remove(user.getUuid());
        log.info("Applied data for user " + user.getUuid() + "(" + ChatUtil.toPlainText(user.getDisplayName()) + ")");
    }
    
    /**
     * Handles logout. All other handlers should try to handle logout here!
     *
     * @param id the uuid of the user that logged out
     */
    public void logout(@Nonnull UUID id) {
        users.remove(id);
        tempData.remove(id);
        
        Optional<User> user = getUser(id);
        user.ifPresent(user1 -> gameHandler.getGames(user1, true).forEach(game -> game.leave(user1)));
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
        // TODO load roles and stuff from somewhere
        tempData.put(uniqueId, "HEYHO");
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
