package me.minidigger.voxelgameslib.api.event.events.user;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.event.Cancelable;
import me.minidigger.voxelgameslib.api.user.User;

import lombok.Getter;
import lombok.Setter;

/**
 * Called when a user interact with something. see the type enum for more
 */
@Getter
public class UserInteractEvent extends UserEvent implements Cancelable {

    private boolean canceled = false;

    private Type type;
    private Block block;

    @Setter
    private boolean shouldUseBlock = true;
    @Setter
    private boolean shouldUseItem = true;

    /**
     * Creates a new event
     *
     * @param user  the user that interacted
     * @param type  the way they interacted
     * @param block the block that was interacted with
     */
    public UserInteractEvent(User user, Type type, Block block) {
        super(user);
        this.type = type;
        this.block = block;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }


    /**
     * The possible type of interactions supported
     */
    public enum Type {
        LEFT_CLICK_BLOCK,
        LEFT_CLICK_AIR,

        RIGHT_CLICK_BLOCK,
        RIGHT_CLICK_AIR,

        PRESSURE_PLATE
    }
}
