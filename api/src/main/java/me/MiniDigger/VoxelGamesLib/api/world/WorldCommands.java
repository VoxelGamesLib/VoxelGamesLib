package me.MiniDigger.VoxelGamesLib.api.world;

import me.MiniDigger.VoxelGamesLib.libs.net.md_5.bungee.api.chat.TextComponent;

import java.util.Optional;

import javax.inject.Inject;

import me.MiniDigger.VoxelGamesLib.api.command.CommandArguments;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
import me.MiniDigger.VoxelGamesLib.api.lang.Lang;
import me.MiniDigger.VoxelGamesLib.api.lang.LangKey;
import me.MiniDigger.VoxelGamesLib.api.map.Map;
import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;
import me.MiniDigger.VoxelGamesLib.api.role.Role;

/**
 * Commands related to worlds
 */
public class WorldCommands {

    @Inject
    private WorldHandler handler;

    @CommandInfo(name = "world", perm = "command.world", role = Role.ADMIN)
    public void world(CommandArguments args) {
//TODO remove me once GH-17 is implemented
        args.getSender().sendMessage(new TextComponent("It works!"));
    }

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
            Lang.msg(args.getSender(), LangKey.WORLD_UNKNOWN_MAP, args.getArg(0));
        }
    }

    @CommandInfo(name = "world.tp", perm = "command.world.tp", role = Role.MODERATOR, description = "TPs to a world", min = 1)
    public void tp(CommandArguments args) {
        Optional<Map> o = handler.getMap(args.getArg(0));
        if (o.isPresent()) {
            args.getSender().teleport(new Vector3D(0, 0, 0));
        } else {
            Lang.msg(args.getSender(), LangKey.WORLD_UNKNOWN_MAP, args.getArg(0));
        }
    }
}
