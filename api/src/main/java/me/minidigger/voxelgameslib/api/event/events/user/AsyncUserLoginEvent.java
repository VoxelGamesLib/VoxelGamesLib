package me.minidigger.voxelgameslib.api.event.events.user;

import java.util.UUID;

import me.minidigger.voxelgameslib.api.event.Cancelable;
import me.minidigger.voxelgameslib.api.event.Event;

/**
 * Called when the login process for a user begins
 */
public class AsyncUserLoginEvent implements Event, Cancelable {
    
    private UUID id;
    private String name;
    
    private String kickMessage = "Something went wrong, please reconnect!";
    
    private boolean canceled;
    
    /**
     * @param id   the uuid of the player who is attempting to join
     * @param name the of the user
     */
    public AsyncUserLoginEvent(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /**
     * @return the uuid of the player who is attempting to join
     */
    public UUID getUuid() {
        return id;
    }
    
    /**
     * @return the uuid of the player who is attempting to join. Should be used with caution!
     */
    public String getName() {
        return name;
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
     * @return the kick message that should be displayed when this event is canceled.
     */
    public String getKickMessage() {
        return kickMessage;
    }
    
    /**
     * Sets the kick messages that should be displayed when this event is canceled.
     *
     * @param kickMessage the message to set
     */
    public void setKickMessage(String kickMessage) {
        this.kickMessage = kickMessage;
    }
}
