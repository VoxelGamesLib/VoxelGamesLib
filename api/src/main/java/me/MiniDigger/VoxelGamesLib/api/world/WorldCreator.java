package me.MiniDigger.VoxelGamesLib.api.world;

import com.google.inject.Singleton;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.MiniDigger.VoxelGamesLib.api.command.CommandArguments;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
import me.MiniDigger.VoxelGamesLib.api.game.GameHandler;
import me.MiniDigger.VoxelGamesLib.api.game.GameMode;
import me.MiniDigger.VoxelGamesLib.api.lang.Lang;
import me.MiniDigger.VoxelGamesLib.api.lang.LangKey;
import me.MiniDigger.VoxelGamesLib.api.map.Vector3D;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.api.user.User;
import me.MiniDigger.VoxelGamesLib.api.utils.ChatUtil;

/**
 * Handles creation of new worlds/maps
 */
@Singleton
public class WorldCreator {

    @Inject
    private WorldHandler worldHandler;

    @Inject
    private GameHandler gameHandler;

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
        Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_INFO);
    }

    @CommandInfo(name = "worldcreator.start", perm = "command.worldcreator.start", role = Role.ADMIN, allowConsole = false)
    public void start(CommandArguments arguments) {
        if (editor != null) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_IN_USE, ChatUtil.toPlainText(editor.getDisplayName()));
            return;
        }

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_NAME, arguments.getSender().getLocale()).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "worldcreator world ")).create());

        step = 1;
    }

    @CommandInfo(name = "worldcreator.world", perm = "command.worldcreator.world", role = Role.ADMIN, min = 1, allowConsole = false)
    public void world(CommandArguments arguments) {
        if (step != 1) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 1);
            return;
        }

        worldHandler.loadLocalWorld(arguments.getArg(0));
        arguments.getSender().teleport(arguments.getArg(0));

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_CENTER, arguments.getSender().getLocale()).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "worldcreator center")).create());

        step = 2;
    }

    @CommandInfo(name = "worldcreator.center", perm = "command.worldcreator.center", role = Role.ADMIN, allowConsole = false)
    public void center(CommandArguments arguments) {
        if (step != 2) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 2);
            return;
        }

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_RADIUS, arguments.getSender().getLocale()).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "worldcreator radius ")).create());

        step = 3;
    }

    @CommandInfo(name = "worldcreator.radius", perm = "command.worldcreator.radius", role = Role.ADMIN, min = 1, allowConsole = false)
    public void radius(CommandArguments arguments) {
        if (step != 3) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 3);
            return;
        }

        try {
            radius = Integer.parseInt(arguments.getArg(0));
        } catch (NumberFormatException ex) {
            Lang.msg(arguments.getSender(), LangKey.GENERAL_NOT_A_NUMBER);
            return;
        }

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_DISPLAY_NAME, arguments.getSender().getLocale()).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "worldcreator name ")).create());

        step = 4;
    }

    @CommandInfo(name = "worldcreator.name", perm = "command.worldcreator.name", role = Role.ADMIN, min = 1, allowConsole = false)
    public void name(CommandArguments arguments) {
        if (step != 4) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 4);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arguments.getArgs()) {
            sb.append(s).append(" ");
        }
        displayName = sb.toString();

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_AUTHOR, arguments.getSender().getLocale()).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "worldcreator author ")).create());

        step = 5;
    }

    @CommandInfo(name = "worldcreator.author", perm = "command.worldcreator.author", role = Role.ADMIN, min = 1, allowConsole = false)
    public void author(CommandArguments arguments) {
        if (step != 5) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 5);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arguments.getArgs()) {
            sb.append(s).append(" ");
        }
        author = sb.toString();

        Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_AUTHOR_SET, author);
        for (GameMode mode : gameHandler.getGameMode()) {
            arguments.getSender().sendMessage(new ComponentBuilder(mode.getName() + " ").color(ChatColor.YELLOW)
                    .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "worldcreator gamemode " + mode.getName())).create());
        }

        Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_GAME_MODE_DONE_BUTTON);

        step = 6;
    }

    @CommandInfo(name = "worldcreator.gamemode", perm = "command.worldcreator.gamemode", role = Role.ADMIN, min = 1, allowConsole = false)
    public void gamemode(CommandArguments arguments) {
        if (step != 6) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 6);
            return;
        }

        String gamemode = arguments.getArg(0);
        if (gamemode.equalsIgnoreCase("done")) {
            arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_EDIT_MODE_ON, arguments.getSender().getLocale()).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "worldcreator edit on")).create());
            arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_EDIT_MODE_OFF, arguments.getSender().getLocale()).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "worldcreator edit off")).create());
            step = 7;
        } else {
            gameModes.add(gamemode);
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_GAME_MODE_ADDED);
        }
    }

    @CommandInfo(name = "worldcreator.edit", perm = "command.worldcreator.edit", role = Role.ADMIN, min = 1, allowConsole = false)
    public void edit(CommandArguments arguments) {
        if (step != 7) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 7);
            return;
        }

        if (arguments.getArg(0).equalsIgnoreCase("on")) {
            // TODO implement editing mode
        } else if (arguments.getArg(0).equalsIgnoreCase("off")) {
            //TODO print summery of all stuff
            arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_DONE_QUESTIONMARK, arguments.getSender().getLocale()).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "worldcreator done")).create());
            step = 8;
        } else {
            Lang.msg(arguments.getSender(), LangKey.GENERAL_INVALID_ARGUMENT, arguments.getArg(0));
        }
    }

    @CommandInfo(name = "worldcreator.done", perm = "command.worldcreator.done", role = Role.ADMIN, allowConsole = false)
    public void done(CommandArguments arguments) {
        if (step != 8) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 8);
            return;
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

        Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_DONE);
    }
}
