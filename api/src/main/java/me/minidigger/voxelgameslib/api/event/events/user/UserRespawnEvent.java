package me.minidigger.voxelgameslib.api.event.events.user;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.user.User;

/**
 * Called when a user respawns
 */
public class UserRespawnEvent extends UserEvent {

    private Vector3D respawnLocation;
    private String world;

    /**
     * @param user            the user that respawned
     * @param world           the world he should spawn at
     * @param respawnLocation the location he should spawn at
     */
    public UserRespawnEvent(@Nonnull User user, @Nonnull String world, @Nonnull Vector3D respawnLocation) {
        super(user);
        this.world = world;
        this.respawnLocation = respawnLocation;
    }

    /**
     * @return the location the user should spawn at
     */
    @Nonnull
    public Vector3D getRespawnLocation() {
        return respawnLocation;
    }

    /**
     * @param respawnLocation the location the user should spawn at
     */
    public void setRespawnLocation(@Nonnull Vector3D respawnLocation) {
        this.respawnLocation = respawnLocation;
    }

    /**
     * @return the world the user should spawn at
     */
    @Nonnull
    public String getWorld() {
        return world;
    }

    /**
     * @param world the world the user should spawn at
     */
    public void setWorld(@Nonnull String world) {
        this.world = world;
    }
}
