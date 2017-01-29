package me.minidigger.voxelgameslib.api.event.events.user;

import me.minidigger.voxelgameslib.api.user.User;

/**
 * Called when a user died
 */
public class UserDeathEvent extends UserEvent {

    /**
     * Called when a user died
     *
     * @param user the user who died
     */
    public UserDeathEvent(User user) {
        super(user);
    }
}
