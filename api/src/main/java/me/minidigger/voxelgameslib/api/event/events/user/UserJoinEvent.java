package me.minidigger.voxelgameslib.api.event.events.user;

import me.minidigger.voxelgameslib.api.user.User;

/**
 * Called when a user joins the server
 */
public class UserJoinEvent extends UserEvent {
    
    /**
     * @param user the user who did just join the game
     */
    public UserJoinEvent(User user) {
        super(user);
    }
}
