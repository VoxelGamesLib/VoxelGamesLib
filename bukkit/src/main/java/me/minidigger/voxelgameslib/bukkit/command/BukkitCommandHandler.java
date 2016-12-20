package me.minidigger.voxelgameslib.bukkit.command;

import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;
import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.bukkit.VoxelGamesLibBukkit;

@Log
@Singleton
public class BukkitCommandHandler extends CommandHandler {
    
    @Inject
    private VoxelGamesLibBukkit plugin;
    
    private CommandMap bukkitCommandMap;
    private Map<String, org.bukkit.command.Command> knownCommands;
    
    @Override
    public void start() {
        super.start();
        
        // hook bukkits shit
        if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
            SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();
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
                System.out.println(obj.getClass().getName());
                if (obj.getClass().isAssignableFrom(HashMap.class)) {
                    knownCommands = (Map<String, org.bukkit.command.Command>) obj;

                    // DEBUG
                    knownCommands.put("testcommandtest", new BukkitCommand("testcommandtest") {
                        @Override
                        public boolean execute(CommandSender commandSender, String s, String[] strings) {
                            commandSender.sendMessage("testcommandtest");
                            return true;
                        }
                    });
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
    }
    
    @Override
    protected void registerCommand(@Nonnull String commandLabel, @Nonnull CommandInfo info, @Nonnull Method method, @Nonnull Object object) {
        super.registerCommand(commandLabel, info, method, object);

        //TODO register command
    }
    
    @Override
    protected void unregisterCommand(@Nonnull String commandLabel, @Nonnull CommandInfo info, @Nonnull Method method, @Nonnull Object object, boolean remove) {
        super.unregisterCommand(commandLabel, info, method, object, remove);
        
        //TODO unregister command
    }
}
