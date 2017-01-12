package me.minidigger.voxelgameslib.api.event.events.block;

import me.minidigger.voxelgameslib.api.user.User;

/**
 * Called when a user breaks a block
 */
public class BlockBreakEvent extends BlockEvent {
    
    private User user;
    
    /**
     * @return the user that broke the block
     */
    public User getUser() {
        return user;
    }
}
