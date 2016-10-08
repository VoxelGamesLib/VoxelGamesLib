package me.MiniDigger.VoxelGamesLib.api.world;

import java.util.Optional;

import javax.inject.Inject;

import me.MiniDigger.VoxelGamesLib.api.command.CommandArguments;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
import me.MiniDigger.VoxelGamesLib.api.map.Map;
import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;
import me.MiniDigger.VoxelGamesLib.api.role.Role;

/**
 * Commands related to worlds
 */
public class WorldCommands {

    @Inject
    private WorldHandler handler;

    @CommandInfo(name = "world.load", perm = "command.world.load", role = Role.ADMIN, description = "Loads a world", min = 1)
    public void load(CommandArguments args) {
        Optional<Map> o = handler.getMap(args.getArg(0));
        Map map;
        if (o.isPresent()) {
            map = o.get();
        } else {
            map = handler.loadMap(args.getArg(0));
        }

        handler.loadWorld(map);
    }

    @CommandInfo(name = "world.unload", perm = "command.world.unload", role = Role.ADMIN, description = "Unloads a world", min = 1)
    public void unload(CommandArguments args) {
        Optional<Map> o = handler.getMap(args.getArg(0));
        if (o.isPresent()) {
            handler.unloadWorld(o.get());
        } else {
            // TODO send unknown map message
        }
    }

    @CommandInfo(name = "world.tp", perm = "command.world.tp", role = Role.MODERATOR, description = "TPs to a world", min = 1)
    public void tp(CommandArguments args) {
        Optional<Map> o = handler.getMap(args.getArg(0));
        if (o.isPresent()) {
           args.getSender().teleport(new Vector3D(0,0,0));
        }
    }
}
