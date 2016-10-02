package me.minidigger.voxelgameslib.bukkit;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.MiniDigger.VoxelGamesLib.api.VoxelGamesLib;
import me.MiniDigger.VoxelGamesLib.api.command.CommandArguments;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
import me.MiniDigger.VoxelGamesLib.api.message.ChatMessage;

public final class VoxelGamesLibBukkit extends JavaPlugin implements Listener {

    private VoxelGamesLib voxelGameLib = new VoxelGamesLib();

    @Override
    public void onEnable() {
        voxelGameLib.onEnable();

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        voxelGameLib.onDisable();
    }

    @CommandInfo(name = "test")
    public void command(CommandArguments args) {
        args.getSender().sendMessage(ChatMessage.fromLegacyFormat("got command!"));
    }
}
