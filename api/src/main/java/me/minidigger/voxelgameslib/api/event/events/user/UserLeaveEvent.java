package me.minidigger.voxelgameslib.api.event.events.user;

import me.minidigger.voxelgameslib.api.user.User;

/**
 * Called when a user leaves the server
 */
public class UserLeaveEvent extends UserEvent {
    
    /**
     * @param user the user that just left the server
     */
    public UserLeaveEvent(User user) {
        super(user);
    }
}
