package me.minidigger.voxelgameslib.bukkit.block;

import com.google.inject.Injector;

import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.event.events.block.BlockBreakEvent;
import me.minidigger.voxelgameslib.api.event.events.block.BlockPlaceEvent;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Singleton
@SuppressWarnings("JavaDoc")
public class BlockListener implements Listener {

    @Inject
    private VGLEventHandler eventHandler;
    @Inject
    private UserHandler userHandler;
    @Inject
    private Injector injector;

    @EventHandler
    @SuppressWarnings("JavaDoc")
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Optional<User> user = userHandler.getUser(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            Block block = injector.getInstance(Block.class);
            //noinspection unchecked
            block.setImplementationType(event.getBlock());
            BlockPlaceEvent e = new BlockPlaceEvent(user.get(), block);
            eventHandler.callEvent(e);
            event.setCancelled(e.isCanceled());
        }
    }

    @EventHandler
    @SuppressWarnings("JavaDoc")
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
        Optional<User> user = userHandler.getUser(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            Block block = injector.getInstance(Block.class);
            //noinspection unchecked
            block.setImplementationType(event.getBlock());
            BlockBreakEvent e = new BlockBreakEvent(block, user.get());
            eventHandler.callEvent(e);
            event.setCancelled(e.isCanceled());
        }
    }
}
