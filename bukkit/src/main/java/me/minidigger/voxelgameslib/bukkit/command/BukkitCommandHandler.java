package me.minidigger.voxelgameslib.bukkit.command;

import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;
import me.MiniDigger.VoxelGamesLib.api.command.CommandHandler;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
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
            } catch (IllegalAccessException | NoSuchFieldException e) {
                log.severe("Could get commandMap from the SimplePluginManager, can't register any commands!");
                e.printStackTrace();
                return;
            }

            try {
                final Field knownCommandField = SimpleCommandMap.class.getDeclaredField("knownCommands");
                knownCommandField.setAccessible(true);
                Object obj = knownCommandField.get(bukkitCommandMap);
                if (obj.getClass().isAssignableFrom(Map.class)) {
                    knownCommands = (Map<String, org.bukkit.command.Command>) obj;
                } else {
                    log.warning("Could get knownCommands from the SimpleCommandMap (returned unexpected object), can't unregister any commands!");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
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
