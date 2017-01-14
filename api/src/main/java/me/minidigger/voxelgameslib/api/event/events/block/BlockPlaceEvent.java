package me.minidigger.voxelgameslib.api.event.events.block;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.event.Cancelable;
import me.minidigger.voxelgameslib.api.user.User;

/**
 * Called when a user places a block
 */
public class BlockPlaceEvent extends BlockEvent implements Cancelable {

    private User user;
    private boolean canceled;

    /**
     * Constructs a new block place event
     *
     * @param user  the user that placed the block
     * @param block the block that has been placed
     */
    public BlockPlaceEvent(User user, Block block) {
        super(block);
        this.user = user;
    }

    /**
     * @return the user that placed the block
     */
    public User getUser() {
        return user;
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
