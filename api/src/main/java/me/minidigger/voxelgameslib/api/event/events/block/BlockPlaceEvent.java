package me.minidigger.voxelgameslib.api.event.events.block;

import me.minidigger.voxelgameslib.api.user.User;

/**
 * Called when a user places a block
 */
public class BlockPlaceEvent extends BlockEvent {
    
    private User user;
    
    /**
     * @return the user that placed the block
     */
    public User getUser() {
        return user;
    }
}
