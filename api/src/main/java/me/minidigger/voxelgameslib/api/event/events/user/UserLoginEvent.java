package me.minidigger.voxelgameslib.api.event.events.user;

import java.util.UUID;

/**
 * Called after the async login event.
 */
public class UserLoginEvent extends AsyncUserLoginEvent {
    
    private Object playerObject;
    
    /**
     * @param id           the uuid of the player who is attempting to join
     * @param name         the of the user
     * @param playerObject the server implementation's player object
     */
    public UserLoginEvent(UUID id, String name, Object playerObject) {
        super(id, name);
        this.playerObject = playerObject;
    }
    
    /**
     * @return the server implementation's player object
     */
    public Object getPlayerObject() {
        return playerObject;
    }
}
