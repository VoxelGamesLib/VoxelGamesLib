package me.minidigger.voxelgameslib.api.world;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandExecutor;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.map.Map;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.TextComponent;

/**
 * Commands related to worlds
 */
@Singleton
@CommandExecutor
@SuppressWarnings("JavaDoc") // commands don't need javadoc, go read the command's descriptions
public class WorldCommands {
    
    @Inject
    private WorldHandler handler;
    
    @CommandInfo(name = "world", perm = "command.world", role = Role.ADMIN)
    public void world(@Nonnull CommandArguments args) {
//TODO remove me once GH-17 is implemented
        args.getSender().sendMessage(new TextComponent("It works! You are on " + args.getSender().getWorld()));
    }
    
    @CommandInfo(name = "world.load", perm = "command.world.load", role = Role.ADMIN, description = "Loads a world", min = 1)
    public void load(@Nonnull CommandArguments args) {
        Optional<Map> o = handler.getMap(args.getArg(0));
        Map map = o.orElseGet(() -> handler.loadMap(args.getArg(0)));
        
        handler.loadWorld(map);
    }
    
    @CommandInfo(name = "world.loadlocal", perm = "command.world.loadlocal", role = Role.ADMIN, description = "Loads a local world", min = 1)
    public void loadLocal(@Nonnull CommandArguments args) {
        handler.loadLocalWorld(args.getArg(0));
    }
    
    @CommandInfo(name = "world.unloadlocal", perm = "command.world.unloadlocal", role = Role.ADMIN, description = "Unloads a local world", min = 1)
    public void unloadLocal(@Nonnull CommandArguments args) {
        handler.unloadLocalWorld(args.getArg(0));
    }
    
    @CommandInfo(name = "world.unload", perm = "command.world.unload", role = Role.ADMIN, description = "Unloads a world", min = 1)
    public void unload(@Nonnull CommandArguments args) {
        Optional<Map> o = handler.getMap(args.getArg(0));
        if (o.isPresent()) {
            handler.unloadWorld(o.get());
        } else {
            Lang.msg(args.getSender(), LangKey.WORLD_UNKNOWN_MAP, args.getArg(0));
        }
    }
    
    @CommandInfo(name = "world.tp", perm = "command.world.tp", role = Role.MODERATOR, description = "TPs to a world", min = 1)
    public void tp(@Nonnull CommandArguments args) {
        Optional<Map> o = handler.getMap(args.getArg(0));
        if (o.isPresent()) {
            Map map = o.get();
            System.out.println("teleporting to " + map.getWorldName() + ", " + map.getCenter());
            args.getSender().teleport(map.getWorldName(), map.getCenter());
        } else {
            args.getSender().teleport(args.getArg(0));
            Lang.msg(args.getSender(), LangKey.WORLD_UNKNOWN_MAP, args.getArg(0));
        }
    }
}
