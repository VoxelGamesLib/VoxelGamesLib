package me.minidigger.voxelgameslib.bukkit;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Singleton;

import me.MiniDigger.VoxelGamesLib.api.VoxelGamesLib;
import me.MiniDigger.VoxelGamesLib.api.command.CommandArguments;
import me.MiniDigger.VoxelGamesLib.api.command.CommandHandler;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
import me.MiniDigger.VoxelGamesLib.api.message.ChatMessage;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.api.tick.TickHandler;
import me.MiniDigger.VoxelGamesLib.api.user.ConsoleUser;
import me.MiniDigger.VoxelGamesLib.api.user.User;
import me.minidigger.voxelgameslib.bukkit.command.BukkitCommandHandler;
import me.minidigger.voxelgameslib.bukkit.tick.BukkitTickHandler;
import me.minidigger.voxelgameslib.bukkit.user.BukkitConsoleUser;
import me.minidigger.voxelgameslib.bukkit.user.BukkitUser;

@Singleton
public final class VoxelGamesLibBukkit extends JavaPlugin implements Listener {

    private VoxelGamesLibBukkit voxelGamesLibBukkit;
    private VoxelGamesLib voxelGameLib;

    @Override
    public void onEnable() {
        voxelGamesLibBukkit = this;

        Injector injector = Guice.createInjector(new BukkitInjector());

        voxelGameLib = injector.getInstance(VoxelGamesLib.class);
        voxelGameLib.onEnable();

        CommandHandler cmdHandler = injector.getInstance(CommandHandler.class);
        cmdHandler.register(this);
        BukkitConsoleUser sender = new BukkitConsoleUser();
        sender.setUser(Bukkit.getConsoleSender());
        cmdHandler.executeCommand(sender, "test command");

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        voxelGameLib.onDisable();
    }

    @CommandInfo(name = "test", perm = "command.test", role = Role.DEFAULT)
    public void command(CommandArguments args) {
        args.getSender().sendMessage(ChatMessage.fromLegacyFormat("got command!"));
    }

    class BukkitInjector extends AbstractModule {

        @Override
        protected void configure() {
            bind(CommandHandler.class).to(BukkitCommandHandler.class);
            bind(User.class).to(BukkitUser.class);
            bind(TickHandler.class).to(BukkitTickHandler.class);
            bind(VoxelGamesLibBukkit.class).toInstance(voxelGamesLibBukkit);
            bind(ConsoleUser.class).to(BukkitConsoleUser.class);
        }
    }
}
