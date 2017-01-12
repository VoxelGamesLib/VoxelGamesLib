package me.minidigger.voxelgameslib.bukkit.user;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.event.events.user.AsyncUserLoginEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserDamageEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserJoinEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserLeaveEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserLoginEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserRespawnEvent;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserHandler;
import me.minidigger.voxelgameslib.bukkit.world.VectorConverter;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import lombok.extern.java.Log;

@Log
@Singleton
@SuppressWarnings("JavaDoc")// event handlers don't need javadoc...
public class UserListener implements Listener {
    
    @Inject
    private UserHandler handler;
    @Inject
    private VGLEventHandler eventHandler;
    @Inject
    private VectorConverter vectorConverter;
    
    @EventHandler
    public void login(@Nonnull PlayerLoginEvent event) {
        UserLoginEvent e = new UserLoginEvent(event.getPlayer().getUniqueId(), event.getPlayer().getName(), event.getPlayer());
        eventHandler.callEvent(e);
        if (e.isCanceled()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, e.getKickMessage());
        }
    }
    
    @EventHandler
    public void asyncLogin(@Nonnull AsyncPlayerPreLoginEvent event) {
        AsyncUserLoginEvent e = new AsyncUserLoginEvent(event.getUniqueId(), event.getName());
        eventHandler.callEvent(e);
        if (e.isCanceled()) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(e.getKickMessage());
        }
    }
    
    @EventHandler
    public void logout(@Nonnull PlayerQuitEvent event) {
        Optional<User> user = handler.getUser(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            eventHandler.callEvent(new UserLeaveEvent(user.get()));
        } else {
            log.warning("User " + event.getPlayer().getName() + " left the server without having a user object!");
        }
    }
    
    @EventHandler
    public void join(@Nonnull PlayerJoinEvent event) {
        Optional<User> user = handler.getUser(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            eventHandler.callEvent(new UserJoinEvent(user.get()));
        } else {
            log.warning("User " + event.getPlayer().getName() + " tried to join the server without having a user object!");
            event.getPlayer().kickPlayer(Lang.string(LangKey.DATA_NOT_LOADED));
        }
    }
    
    @EventHandler
    public void respawn(@Nonnull PlayerRespawnEvent event) {
        Optional<User> user = handler.getUser(event.getPlayer().getUniqueId());
        if (user.isPresent()) {
            UserRespawnEvent e = new UserRespawnEvent(user.get(), event.getRespawnLocation().getWorld().getName(), vectorConverter.toVGL(event.getRespawnLocation().toVector()));
            eventHandler.callEvent(e);
            event.setRespawnLocation(vectorConverter.fromVGL(e.getRespawnLocation()).toLocation(Bukkit.getWorld(e.getWorld())));
        } else {
            log.warning("User " + event.getPlayer().getName() + " tried to respawn without having a user object!");
        }
    }
    
    @EventHandler
    public void onDamage(@Nonnull EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }
        
        Optional<User> user = handler.getUser(event.getEntity().getUniqueId());
        if (user.isPresent()) {
            UserDamageEvent e = new UserDamageEvent(user.get());
            eventHandler.callEvent(e);
            event.setCancelled(e.isCanceled());
        } else {
            log.warning("User " + event.getEntity().getName() + " tried to be damaged without having a user object!");
        }
    }
}
