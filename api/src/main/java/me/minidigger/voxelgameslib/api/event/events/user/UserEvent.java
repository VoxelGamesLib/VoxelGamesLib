package me.minidigger.voxelgameslib.api.event.events.user;

import me.minidigger.voxelgameslib.api.event.Event;
import me.minidigger.voxelgameslib.api.user.User;

/**
 * Parent for all user related events
 */
public class UserEvent implements Event {
    
    private User user;
    
    UserEvent(User user) {
        this.user = user;
    }
    
    /**
     * @return the user involved in this event
     */
    public User getUser() {
        return user;
    }
}
