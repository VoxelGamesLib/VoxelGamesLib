package me.minidigger.voxelgameslib.api.signs;

import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.block.metadata.SignMetaData;
import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.event.events.sign.SignUpdateEvent;
import me.minidigger.voxelgameslib.api.exception.VoxelGameLibException;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.api.world.World;

import lombok.Data;

/**
 * Stores the location of a traced sign into the db
 */
@Data
@Entity
public class SignLocation {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Vector3D location;

    @Column
    private String world;

    @Transient
    private Block block;

    /**
     * Constructs a new sign location
     *
     * @param location the coordinates
     * @param world    the world
     */
    public SignLocation(Vector3D location, String world, Server server) {
        this.location = location;
        this.world = world;

        Optional<World> w = server.getWorld(world);
        if (!w.isPresent()) {
            throw new VoxelGameLibException("Unknown world " + world);
        }

        block = w.get().getBlockAt(location);
    }

    protected SignLocation() {
        //JPA
    }

    /**
     * Checks if the block on this location is still a sign
     *
     * @return if the block is still a sign
     */
    public boolean isStillValid() {
        return block.getMetaData() instanceof SignMetaData;
    }

    /**
     * Fires a SignUpdateEvent for the sign at this location
     *
     * @param eventHandler the event handler that should handle the event
     */
    public void fireUpdateEvent(VGLEventHandler eventHandler) {
        if (!isStillValid()) {
            return;
        }
        SignMetaData metaData = (SignMetaData) block.getMetaData();
        String[] text = metaData.getLines();
        SignUpdateEvent event = new SignUpdateEvent(world, location, text);
        eventHandler.callEvent(event);
        if (!event.isCanceled()) {
            metaData.setLines(event.getText());
        }
    }
}
