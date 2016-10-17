package me.minidigger.voxelgameslib.bukkit.user;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.MiniDigger.VoxelGamesLib.api.lang.Lang;
import me.MiniDigger.VoxelGamesLib.api.lang.LangKey;
import me.MiniDigger.VoxelGamesLib.api.user.UserHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

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
            // we don't have a locale here since the data was not loaded :/
            event.getPlayer().kickPlayer(Lang.string(LangKey.DATA_NOT_LOADED));
        }
        handler.join(new BukkitUser(event.getPlayer()));
    }
    
    @EventHandler
    public void load(AsyncPlayerPreLoginEvent event) {
        handler.login(event.getUniqueId());
    }
    
    @EventHandler
    public void logout(PlayerQuitEvent event) {
        handler.logout(event.getPlayer().getUniqueId());
    }
}
