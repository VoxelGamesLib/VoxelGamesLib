package me.minidigger.voxelgameslib.bukkit.command;

import java.util.Optional;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Martin on 08.10.2016.
 */
@Singleton
public class CommandListener implements Listener {
    
    @Inject
    private UserHandler userHandler;
    @Inject
    private CommandHandler commandHandler;
    
    @EventHandler
    public void onCommand(@Nonnull PlayerCommandPreprocessEvent event) {
        Optional<User> userOptional = userHandler.getUser(event.getPlayer().getUniqueId());
        if (!userOptional.isPresent()) {
            return;
        }
        
        User user = userOptional.get();
        boolean result = commandHandler.executeCommand(user, event.getMessage().replaceFirst(Pattern.quote("/"), ""));
        if (result) {
            event.setCancelled(true);
        }
    }
}
