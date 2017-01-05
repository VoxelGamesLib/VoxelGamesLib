package me.minidigger.voxelgameslib.bukkit.command;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserHandler;
import me.minidigger.voxelgameslib.bukkit.VoxelGamesLibBukkit;

import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;

import lombok.extern.java.Log;

@Log
@Singleton
public class BukkitCommandHandler extends CommandHandler {
    
    @Inject
    private VoxelGamesLibBukkit plugin;
    @Inject
    private UserHandler userHandler;
    @Inject
    private Server server;
    
    private Map<String, org.bukkit.command.Command> knownCommands;
    
    @Override
    public void start() {
        // hook bukkits shit
        if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
            SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
            CommandMap bukkitCommandMap;
            try {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);
                bukkitCommandMap = (CommandMap) field.get(manager);
            } catch (@Nonnull IllegalAccessException | NoSuchFieldException e) {
                log.severe("Could not get commandMap from the SimplePluginManager, can't register any commands!");
                e.printStackTrace();
                return;
            }
    
            try {
                Field knownCommandField = SimpleCommandMap.class.getDeclaredField("knownCommands");
                knownCommandField.setAccessible(true);
                Object obj = knownCommandField.get(bukkitCommandMap);
                if (obj.getClass().isAssignableFrom(HashMap.class)) {
                    knownCommands = (Map<String, org.bukkit.command.Command>) obj;
                } else {
                    log.severe("Could not get knownCommands from the SimpleCommandMap (returned unexpected object), can't unregister any commands!");
                }
            } catch (@Nonnull NoSuchFieldException | IllegalAccessException e) {
                log.warning("Could get knownCommands from the SimpleCommandMap, can't unregister any commands!");
                e.printStackTrace();
            }
    
        } else {
            // should never occur
            throw new IllegalArgumentException("Specified plugin has no SimplePluginManager?!");
        }
        
        // registers all commands
        super.start();
    }
    
    @Override
    protected void registerCommand(@Nonnull String commandLabel, @Nonnull CommandInfo info, @Nonnull Method method, @Nonnull Object object) {
        super.registerCommand(commandLabel, info, method, object);
        
        // ignore non root commands
        if (commandLabel.contains(".")) {
            return;
        }
        
        knownCommands.put(commandLabel, new BukkitCommand(commandLabel) {
            @Override
            public boolean execute(CommandSender commandSender, String label, String[] args) {
                StringBuilder sb = new StringBuilder(label);
                for (String arg : args) {
                    sb.append(" ").append(arg);
                }
    
                if (commandSender instanceof Player) {
                    Player player = (Player) commandSender;
                    Optional<User> user = userHandler.getUser(((Player) commandSender).getUniqueId());
                    if (user.isPresent()) {
                        executeCommand(user.get(), sb.toString());
                    } else {
                        log.warning(player.getDisplayName() + " tried to execute a command without being a user?!");
                    }
                } else {
                    executeCommand(server.getConsoleUser(), sb.toString());
                }
    
                return true;
            }
            
            @Override
            public List<String> tabComplete(CommandSender sender, String label, String[] args) throws IllegalArgumentException {
                StringBuilder sb = new StringBuilder(label);
                for (String arg : args) {
                    sb.append(" ").append(arg);
                }
                
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    Optional<User> user = userHandler.getUser(((Player) sender).getUniqueId());
                    if (user.isPresent()) {
                        return executeCompleter(user.get(), sb.toString());
                    } else {
                        log.warning(player.getDisplayName() + " tried to execute a completer without being a user?!");
                    }
                } else {
                    return executeCompleter(server.getConsoleUser(), sb.toString());
                }
                
                return new ArrayList<>();
            }
        });
    }
    
    @Override
    protected void unregisterCommand(@Nonnull String commandLabel, @Nonnull CommandInfo info, @Nonnull Method method, @Nonnull Object object, boolean remove) {
        super.unregisterCommand(commandLabel, info, method, object, remove);
        
        // ignore non root commands
        if (commandLabel.contains(".")) {
            return;
        }
        
        knownCommands.remove(commandLabel);
    }
}
