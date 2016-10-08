package me.minidigger.voxelgameslib.bukkit.user;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.MiniDigger.VoxelGamesLib.api.user.UserHandler;

/**
 * Created by Martin on 08.10.2016.
 */
@Singleton
public class UserListener implements Listener {

    @Inject
    private UserHandler handler;

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (!handler.hasLoggedIn(event.getPlayer().getUniqueId())) {
            event.getPlayer().kickPlayer("Your data has not been loaded, please join again!"); //TODO better message
        }
        handler.join(new BukkitUser(event.getPlayer()));
    }

    @EventHandler
    public void load(AsyncPlayerPreLoginEvent event) {
        handler.login(event.getUniqueId());
    }

    @EventHandler
    public void logout(PlayerQuitEvent event){
        handler.logout(event.getPlayer().getUniqueId());
    }
}
