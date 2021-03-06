package me.minidigger.voxelgameslib.api.world;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.inject.Singleton;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandExecutor;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.game.GameHandler;
import me.minidigger.voxelgameslib.api.game.GameMode;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.map.Map;
import me.minidigger.voxelgameslib.api.map.MapInfo;
import me.minidigger.voxelgameslib.api.map.MapScanner;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.utils.ZipUtil;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.ChatColor;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ClickEvent;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;

import lombok.extern.java.Log;

/**
 * Handles creation of new worlds/maps
 */
@Log
@Singleton
@CommandExecutor
@SuppressWarnings("JavaDoc") // commands don't need javadoc, go read the command's descriptions
public class WorldCreator {

    //TODO world creator completer

    @Inject
    private WorldHandler worldHandler;

    @Inject
    private GameHandler gameHandler;

    @Inject
    private MapScanner mapScanner;

    @Inject
    private WorldConfig config;

    @Inject
    private Gson gson;

    private User editor;

    private int step = 0;

    private String worldName;
    private Vector3D center;
    private int radius;
    private String displayName;
    private String author;
    private List<String> gameModes;

    private Map map;

    @CommandInfo(name = "worldcreator", perm = "command.worldcreator", role = Role.ADMIN)
    public void worldcreator(@Nonnull CommandArguments arguments) {
        Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_INFO);
    }

    @CommandInfo(name = "worldcreator.start", perm = "command.worldcreator.start", role = Role.ADMIN, allowConsole = false)
    public void start(@Nonnull CommandArguments arguments) {
        if (editor != null) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_IN_USE, editor.getData().getDisplayName());
            return;
        }

        editor = arguments.getSender();
        gameModes = new ArrayList<>();

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_WORLD_NAME, arguments.getSender().getData().getLocale()).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/worldcreator world ")).create());

        step = 1;
    }

    @CommandInfo(name = "worldcreator.world", perm = "command.worldcreator.world", role = Role.ADMIN, min = 1, allowConsole = false)
    public void world(@Nonnull CommandArguments arguments) {
        if (step != 1) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 1);
            return;
        }

        worldName = arguments.getArg(0);

        worldHandler.loadLocalWorld(worldName);
        arguments.getSender().teleport(worldName);

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_CENTER, arguments.getSender().getData().getLocale()).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/worldcreator center")).create());

        step = 2;
    }

    @CommandInfo(name = "worldcreator.center", perm = "command.worldcreator.center", role = Role.ADMIN, allowConsole = false)
    public void center(@Nonnull CommandArguments arguments) {
        if (step != 2) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 2);
            return;
        }

        center = arguments.getSender().getLocation();

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_RADIUS, arguments.getSender().getData().getLocale()).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/worldcreator radius ")).create());

        step = 3;
    }

    @CommandInfo(name = "worldcreator.radius", perm = "command.worldcreator.radius", role = Role.ADMIN, min = 1, allowConsole = false)
    public void radius(@Nonnull CommandArguments arguments) {
        if (step != 3) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 3);
            return;
        }

        try {
            radius = Integer.parseInt(arguments.getArg(0));
        } catch (NumberFormatException ex) {
            Lang.msg(arguments.getSender(), LangKey.GENERAL_INVALID_NUMBER);
            return;
        }

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_DISPLAY_NAME, arguments.getSender().getData().getLocale()).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/worldcreator name ")).create());

        step = 4;
    }

    @CommandInfo(name = "worldcreator.name", perm = "command.worldcreator.name", role = Role.ADMIN, min = 1, allowConsole = false)
    public void name(@Nonnull CommandArguments arguments) {
        if (step != 4) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 4);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arguments.getArgs()) {
            sb.append(s).append(" ");
        }
        displayName = sb.toString().trim();

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_ENTER_AUTHOR, arguments.getSender().getData().getLocale(), displayName).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/worldcreator author ")).create());

        step = 5;
    }

    @CommandInfo(name = "worldcreator.author", perm = "command.worldcreator.author", role = Role.ADMIN, min = 1, allowConsole = false)
    public void author(@Nonnull CommandArguments arguments) {
        if (step != 5) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 5);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arguments.getArgs()) {
            sb.append(s).append(" ");
        }
        author = sb.toString().trim();

        Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_AUTHOR_SET, author);
        for (GameMode mode : gameHandler.getGameModes()) {
            arguments.getSender().sendMessage(new ComponentBuilder(mode.getName() + " ").color(ChatColor.YELLOW)
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/worldcreator gamemode " + mode.getName())).create());
        }

        arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_DONE_QUERY, arguments.getSender().getData().getLocale()).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/worldcreator gamemode done")).create());

        step = 6;
    }

    @CommandInfo(name = "worldcreator.gamemode", perm = "command.worldcreator.gamemode", role = Role.ADMIN, min = 1, allowConsole = false)
    public void gamemode(@Nonnull CommandArguments arguments) {
        if (step != 6) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 6);
            return;
        }

        String gamemode = arguments.getArg(0);
        if (gamemode.equalsIgnoreCase("done")) {
            arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_EDIT_MODE_ON, arguments.getSender().getData().getLocale()).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/worldcreator edit on")).create());
            arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_EDIT_MODE_OFF, arguments.getSender().getData().getLocale()).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/worldcreator edit off")).create());
            step = 7;
        } else {
            gameModes.add(gamemode);
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_GAMEMODE_ADDED);
        }
    }

    @CommandInfo(name = "worldcreator.edit", perm = "command.worldcreator.edit", role = Role.ADMIN, min = 1, allowConsole = false)
    public void edit(@Nonnull CommandArguments arguments) {
        if (step != 7) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 7);
            return;
        }

        if (arguments.getArg(0).equalsIgnoreCase("on")) {
            arguments.getSender().executeCommand("editmode on");
        } else if (arguments.getArg(0).equalsIgnoreCase("off")) {
            arguments.getSender().executeCommand("editmode off");
            MapInfo info = new MapInfo(displayName, author, gameModes);
            map = new Map(info, worldName, center, radius);
            map.printSummery(arguments.getSender());
            arguments.getSender().sendMessage(Lang.trans(LangKey.WORLD_CREATOR_DONE_QUERY, arguments.getSender().getData().getLocale()).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/worldcreator done")).create());
            step = 8;
        } else {
            Lang.msg(arguments.getSender(), LangKey.GENERAL_INVALID_ARGUMENT, arguments.getArg(0));
        }
    }

    @CommandInfo(name = "worldcreator.done", perm = "command.worldcreator.done", role = Role.ADMIN, allowConsole = false)
    public void done(@Nonnull CommandArguments arguments) {
        if (step != 8) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_WRONG_STEP, step, 8);
            return;
        }

        mapScanner.scan(map);

        File worldFolder = new File(worldHandler.getWorldContainer(), map.getWorldName());

        try {
            FileWriter fileWriter = new FileWriter(new File(worldFolder, "config.json"));
            gson.toJson(map, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_SAVE_CONFIG_ERROR, e.getMessage(), e.getClass().getName());
            log.log(Level.WARNING, "Error while saving the world config", e);
            return;
        }

        ZipFile zip;
        try {
            zip = ZipUtil.createZip(worldFolder);
        } catch (ZipException e) {
            Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_SAVE_ZIP_ERROR, e.getMessage(), e.getClass().getName());
            log.log(Level.WARNING, "Error while creating the zip", e);
            return;
        }

        try {
            Files.move(zip.getFile(), new File(worldHandler.getWorldsFolder(), zip.getFile().getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!config.maps.contains(map.getInfo())) {
            config.maps.add(map.getInfo());
            worldHandler.saveConfig();
        }

        // TODO debug
//        editor = null;
//        step = 0;
//        worldName = null;
//        center = null;
//        radius = -1;
//        displayName = null;
//        author = null;
//        gameModes = new ArrayList<>();

        Lang.msg(arguments.getSender(), LangKey.WORLD_CREATOR_DONE);
    }
}
