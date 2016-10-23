package me.MiniDigger.VoxelGamesLib.api.world;

import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.MiniDigger.VoxelGamesLib.api.command.CommandArguments;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
import me.MiniDigger.VoxelGamesLib.api.item.Hand;
import me.MiniDigger.VoxelGamesLib.api.item.Item;
import me.MiniDigger.VoxelGamesLib.api.item.ItemBuilder;
import me.MiniDigger.VoxelGamesLib.api.item.Material;
import me.MiniDigger.VoxelGamesLib.api.lang.Lang;
import me.MiniDigger.VoxelGamesLib.api.lang.LangKey;
import me.MiniDigger.VoxelGamesLib.api.role.Role;

/**
 * Commands related to the edit mode
 */
@Singleton
@SuppressWarnings("JavaDoc") // commands don't need javadoc, go read the command's descriptions
public class EditMode {

    @Inject
    private Injector injector;

    private List<UUID> editMode = new ArrayList<>();

    @CommandInfo(name = "editmode", perm = "command.editmode", role = Role.ADMIN)
    public void editmode(CommandArguments args) {
        //TODO info about edit mode
    }

    @CommandInfo(name = "editmode.on", perm = "command.editmode.on", role = Role.ADMIN)
    public void on(CommandArguments args) {
        if (!editMode.contains(args.getSender().getUUID())) {
            editMode.add(args.getSender().getUUID());
            Lang.msg(args.getSender(), LangKey.EDITMODE_ENABLED);
        } else {
            Lang.msg(args.getSender(), LangKey.EDITMODE_ALREADY_ENABLED);
        }
    }

    @CommandInfo(name = "editmode.off", perm = "command.editmode.off", role = Role.ADMIN)
    public void off(CommandArguments args) {
        if (editMode.contains(args.getSender().getUUID())) {
            editMode.remove(args.getSender().getUUID());
            Lang.msg(args.getSender(), LangKey.EDITMODE_DISABLED);
        } else {
            Lang.msg(args.getSender(), LangKey.EDITMODE_NOT_ENABLED);
        }
    }

    @CommandInfo(name = "editmode.skull", perm = "command.editmode.skull", role = Role.ADMIN, min = 1)
    public void skull(CommandArguments args) {
        if (editMode.contains(args.getSender().getUUID())) {
            String name = args.getArg(0);
            Item skull = new ItemBuilder(Material.SKULL_ITEM, injector).variation((byte) 3).name(name).tag("SkullOwner", name).build();
            args.getSender().setItemInHand(Hand.MAINHAND, skull);
        } else {
            Lang.msg(args.getSender(), LangKey.EDITMODE_NOT_ENABLED);
        }
    }

    @CommandInfo(name = "editmode.chest", perm = "command.editmode.chest", role = Role.ADMIN, min = 1)
    public void chest(CommandArguments args) {
        if (editMode.contains(args.getSender().getUUID())) {
            String name = args.getArg(0);
            Item chest = new ItemBuilder(Material.CHEST, injector).name(name).build();
            args.getSender().setItemInHand(Hand.MAINHAND, chest);
        } else {
            Lang.msg(args.getSender(), LangKey.EDITMODE_NOT_ENABLED);
        }
    }
}
