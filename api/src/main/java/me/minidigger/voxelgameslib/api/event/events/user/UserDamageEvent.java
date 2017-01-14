package me.minidigger.voxelgameslib.api.event.events.user;

import me.minidigger.voxelgameslib.api.event.Cancelable;
import me.minidigger.voxelgameslib.api.user.User;

/**
 * Called when a user is damaged
 */
public class UserDamageEvent extends UserEvent implements Cancelable {

    private boolean canceled;

    /**
     * Constructs a user damage event
     *
     * @param user the user that has been damaged
     */
    public UserDamageEvent(User user) {
        super(user);
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }
}
