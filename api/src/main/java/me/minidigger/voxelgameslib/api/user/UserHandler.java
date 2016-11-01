package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
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
    public void join(User user) {
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
    public void logout(UUID id) {
        users.remove(id);
        tempData.remove(id);
        
        Optional<User> user = getUser(id);
        if (user.isPresent()) {
            gameHandler.getGames(user.get(), true).forEach(game -> game.leave(user.get()));
        }
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
     * Called when a user logs in. used to load all kind of stuff. Should only be called async!
     *
     * @param uniqueId the id of the user that logged in
     */
    public void login(UUID uniqueId) {
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
    public boolean hasLoggedIn(UUID uniqueId) {
        return tempData.containsKey(uniqueId);
    }
}
