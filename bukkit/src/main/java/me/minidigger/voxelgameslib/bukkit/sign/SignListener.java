package me.minidigger.voxelgameslib.bukkit.sign;

import java.util.Optional;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.event.events.sign.SignUpdateEvent;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserHandler;
import me.minidigger.voxelgameslib.bukkit.converter.VectorConverter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import lombok.extern.java.Log;

/**
 * Created by Martin on 04.02.2017.
 */
@Log
public class SignListener implements Listener {

    @Inject
    private VGLEventHandler eventHandler;
    @Inject
    private VectorConverter vectorConverter;
    @Inject
    private UserHandler userHandler;

    @EventHandler
    public void onUpdate(SignChangeEvent event) {
        Optional<User> user = userHandler.getUser(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            SignUpdateEvent e = new SignUpdateEvent(event.getBlock().getWorld().getName(),
                    vectorConverter.toVGL(event.getBlock().getLocation().toVector()), event.getLines(), user.get());
            eventHandler.callEvent(e);
            for (int i = 0; i < event.getLines().length; i++) {
                event.setLine(i, e.getText()[i]);
            }
        } else {
            log.warning(event.getPlayer().getDisplayName() + " tired to change a sign without having a user");
        }
    }
}
