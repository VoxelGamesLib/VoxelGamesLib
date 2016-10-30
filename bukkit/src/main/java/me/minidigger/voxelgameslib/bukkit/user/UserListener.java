package me.minidigger.voxelgameslib.bukkit.user;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.user.UserHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Martin on 08.10.2016.
 */
@Singleton
public class UserListener implements Listener {
    
    @Inject
    private UserHandler handler;
    
    @EventHandler
    public void join(PlayerLoginEvent event) {
        if (!handler.hasLoggedIn(event.getPlayer().getUniqueId())) {
            // worst case: load data sync
            handler.login(event.getPlayer().getUniqueId());
            if (!handler.hasLoggedIn(event.getPlayer().getUniqueId())) {
                // something went horribly wrong
                // we don't have a locale here since the data was not loaded :/
                event.getPlayer().kickPlayer(Lang.string(LangKey.DATA_NOT_LOADED));
            }
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
