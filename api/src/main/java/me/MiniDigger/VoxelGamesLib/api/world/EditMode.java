package me.MiniDigger.VoxelGamesLib.api.world;

import me.MiniDigger.VoxelGamesLib.api.command.CommandArguments;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
import me.MiniDigger.VoxelGamesLib.api.role.Role;

/**
 * Created by Martin on 19.10.2016.
 */
public class EditMode {

    @CommandInfo(name = "editmode", perm = "command.editmode", role = Role.ADMIN)
    public void editmode(CommandArguments args) {
        //TODO info about edit mode
    }

    @CommandInfo(name = "editmode.on", perm = "command.editmode.on", role = Role.ADMIN)
    public void on(CommandArguments args) {
        //TODO edit mode on
    }

    @CommandInfo(name = "editmode.off", perm = "command.editmode.off", role = Role.ADMIN)
    public void off(CommandArguments args) {
        //TODO edit mode off
    }

    @CommandInfo(name = "editmode.skull", perm = "command.editmode.skull", role = Role.ADMIN)
    public void skull(CommandArguments args) {
        //TODO skull editmode
    }

    @CommandInfo(name = "editmode.chest", perm = "command.editmode.chest", role = Role.ADMIN)
    public void chest(CommandArguments args) {
        //TODO chest editmode
    }
}
