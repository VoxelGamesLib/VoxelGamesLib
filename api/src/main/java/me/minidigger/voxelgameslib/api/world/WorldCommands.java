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
import me.minidigger.voxelgameslib.api.map.Vector3D;
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
        args.getSender().sendMessage(new TextComponent("It works!"));
    }
    
    @CommandInfo(name = "world.load", perm = "command.world.load", role = Role.ADMIN, description = "Loads a world", min = 1)
    public void load(@Nonnull CommandArguments args) {
        Optional<Map> o = handler.getMap(args.getArg(0));
        Map map = o.orElseGet(() -> handler.loadMap(args.getArg(0)));
        
        handler.loadWorld(map);
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
            args.getSender().teleport(new Vector3D(0, 0, 0));
        } else {
            Lang.msg(args.getSender(), LangKey.WORLD_UNKNOWN_MAP, args.getArg(0));
        }
    }
}
