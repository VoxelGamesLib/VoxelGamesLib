package me.minidigger.voxelgameslib.api.event.events.block;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.event.Event;

/**
 * Superclass for all block related events
 */
public class BlockEvent implements Event {

    private Block block;

    protected BlockEvent(Block block) {
        this.block = block;
    }

    /**
     * @return the block involved with this event
     */
    public Block getBlock() {
        return block;
    }
}
