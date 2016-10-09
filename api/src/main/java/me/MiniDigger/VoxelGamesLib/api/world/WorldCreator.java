package me.MiniDigger.VoxelGamesLib.api.world;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.MiniDigger.VoxelGamesLib.api.command.CommandArguments;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.api.user.User;

/**
 * Handles creation of new worlds/maps
 */
@Singleton
public class WorldCreator {

    @Inject
    private WorldHandler worldHandler;

    private User editor;

    private int step = 0;

    private String worldName;
    private Vector3D center;
    private int radius;
    private String displayName;
    private String author;
    private List<String> gameModes;

    @CommandInfo(name = "worldcreator", perm = "command.worldcreator", role = Role.ADMIN)
    public void worldcreator(CommandArguments arguments) {
        //TODO show info on how to use the world creator
    }

    @CommandInfo(name = "worldcreator.start", perm = "command.worldcreator.start", role = Role.ADMIN)
    public void start(CommandArguments arguments) {
        if (editor != null) {
            //TODO "user editor is currently creating a world!"
            return;
        }

        //TODO "click here and enter the name of the world you want to create" command worldcreator world <name>

        step = 1;
    }

    @CommandInfo(name = "worldcreator.world", perm = "command.worldcreator.world", role = Role.ADMIN, min = 1)
    public void world(CommandArguments arguments) {
        if (step != 1) {
            // TODO wrong step
        }

        worldHandler.loadLocalWorld(arguments.getArg(0));

        //TODO "world loaded, click here to mark the middle of the world" command worldcreator center

        step = 2;
    }

    @CommandInfo(name = "worldcreator.center", perm = "command.worldcreator.center", role = Role.ADMIN)
    public void center(CommandArguments arguments) {
        if (step != 2) {
            // TODO wrong step
        }

        //TODO "center set, click here and enter the radius of this world (in which we should search for markers and load chunks and stuff" command worldcreator radius <radius>

        step = 3;
    }

    @CommandInfo(name = "worldcreator.radius", perm = "command.worldcreator.radius", role = Role.ADMIN, min = 1)
    public void radius(CommandArguments arguments) {
        if (step != 3) {
            // TODO wrong step
        }

        try {
            radius = Integer.parseInt(arguments.getArg(0));
        } catch (NumberFormatException ex) {
            //TODO "not a number"
            return;
        }

        //TODO "radius set, click here to enter the display name" command worldcreator name <displayname>

        step = 4;
    }

    @CommandInfo(name = "worldcreator.name", perm = "command.worldcreator.name", role = Role.ADMIN, min = 1)
    public void name(CommandArguments arguments) {
        if (step != 4) {
            // TODO wrong step
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arguments.getArgs()) {
            sb.append(s).append(" ");
        }
        displayName = sb.toString();

        //TODO "name set to displayName, click here to enter the author" command worldcreator author <author>

        step = 5;
    }

    @CommandInfo(name = "worldcreator.author", perm = "command.worldcreator.author", role = Role.ADMIN, min = 1)
    public void author(CommandArguments arguments) {
        if (step != 5) {
            // TODO wrong step
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arguments.getArgs()) {
            sb.append(s).append(" ");
        }
        author = sb.toString();

        //TODO "author set to author"
        //TODO print gamemode list, command worldcreator gamemode <gamemode>
        //TODO print done button, command worldcreator gamemode done

        step = 6;
    }

    @CommandInfo(name = "worldcreator.gamemode", perm = "command.worldcreator.gamemode", role = Role.ADMIN, min = 1)
    public void gamemode(CommandArguments arguments) {
        if (step != 6) {
            // TODO wrong step
        }

        String gamemode = arguments.getArg(0);
        if (gamemode.equalsIgnoreCase("done")) {
            //TODO "press here to enter map editing mode" command worldcreator edit on
            //TODO "press here when you are done command worldcreator edit off
            step = 7;
        } else {
            gameModes.add(gamemode);
            //TODO "added gamemode, press anotherone or press done to continue"
        }
    }

    @CommandInfo(name = "worldcreator.edit", perm = "command.worldcreator.edit", role = Role.ADMIN, min = 1)
    public void edit(CommandArguments arguments) {
        if (step != 7) {
            // TODO wrong step
        }

        if (arguments.getArg(0).equalsIgnoreCase("on")) {
            // TODO implement editing mode
        } else if (arguments.getArg(0).equalsIgnoreCase("off")) {
            //TODO print summery of all stuff
            //TODO "click here if you are done" command worldcreator done
            step = 8;
        } else {
            //TODO "u w00t m8?"
        }
    }

    @CommandInfo(name = "worldcreator.done", perm = "command.worldcreator.done", role = Role.ADMIN)
    public void done(CommandArguments arguments) {
        if (step != 8) {
            // TODO wrong step
        }

        // TODO map scan
        // TODO save stuff to json
        // TODO zip world
        // TODO move zip to worlds folder
        // TODO add map into world config

        editor = null;
        step = 0;
        worldName = null;
        center = null;
        radius = -1;
        displayName = null;
        author = null;
        gameModes = new ArrayList<>();

        // TODO "DONE"
    }
}
