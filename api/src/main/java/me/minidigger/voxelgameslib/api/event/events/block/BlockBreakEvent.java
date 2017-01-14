package me.minidigger.voxelgameslib.api.event.events.block;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.event.Cancelable;
import me.minidigger.voxelgameslib.api.user.User;

/**
 * Called when a user breaks a block
 */
public class BlockBreakEvent extends BlockEvent implements Cancelable {

    private User user;
    private boolean canceled;

    /**
     * Constructs a new block break event
     *
     * @param block the block that has been broken
     * @param user  the user that broke the block
     */
    public BlockBreakEvent(Block block, User user) {
        super(block);
        this.user = user;
    }

    /**
     * @return the user that broke the block
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
