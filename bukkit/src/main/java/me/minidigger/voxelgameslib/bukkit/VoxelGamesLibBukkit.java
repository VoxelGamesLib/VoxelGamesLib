package me.minidigger.voxelgameslib.bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import javax.inject.Singleton;

import me.MiniDigger.VoxelGamesLib.api.VoxelGamesLib;
import me.MiniDigger.VoxelGamesLib.api.command.CommandArguments;
import me.MiniDigger.VoxelGamesLib.api.command.CommandHandler;
import me.MiniDigger.VoxelGamesLib.api.command.CommandInfo;
import me.MiniDigger.VoxelGamesLib.api.config.ConfigHandler;
import me.MiniDigger.VoxelGamesLib.api.config.GlobalConfig;
import me.MiniDigger.VoxelGamesLib.api.map.MapScanner;
import me.MiniDigger.VoxelGamesLib.api.role.Role;
import me.MiniDigger.VoxelGamesLib.api.tick.TickHandler;
import me.MiniDigger.VoxelGamesLib.api.user.ConsoleUser;
import me.MiniDigger.VoxelGamesLib.api.user.User;
import me.MiniDigger.VoxelGamesLib.api.world.WorldConfig;
import me.MiniDigger.VoxelGamesLib.api.world.WorldHandler;
import me.MiniDigger.VoxelGamesLib.libs.net.md_5.bungee.api.chat.TextComponent;
import me.minidigger.voxelgameslib.bukkit.command.BukkitCommandHandler;
import me.minidigger.voxelgameslib.bukkit.command.CommandListener;
import me.minidigger.voxelgameslib.bukkit.map.BukkitMapScanner;
import me.minidigger.voxelgameslib.bukkit.tick.BukkitTickHandler;
import me.minidigger.voxelgameslib.bukkit.user.BukkitConsoleUser;
import me.minidigger.voxelgameslib.bukkit.user.BukkitUser;
import me.minidigger.voxelgameslib.bukkit.user.UserListener;
import me.minidigger.voxelgameslib.bukkit.world.BukkitWorldHandler;

@Singleton
public final class VoxelGamesLibBukkit extends JavaPlugin implements Listener {

    private VoxelGamesLibBukkit voxelGamesLibBukkit;
    private VoxelGamesLib voxelGameLib;

    @Override
    public void onEnable() {
        voxelGamesLibBukkit = this;

        // enable guice and the api
        Injector injector = Guice.createInjector(new BukkitInjector());

        voxelGameLib = injector.getInstance(VoxelGamesLib.class);
        voxelGameLib.onEnable(injector);

        // command test
        CommandHandler cmdHandler = injector.getInstance(CommandHandler.class);
        cmdHandler.register(this);
        BukkitConsoleUser sender = new BukkitConsoleUser();
        sender.setUser(Bukkit.getConsoleSender());
        cmdHandler.executeCommand(sender, "test command");

        // config test
        GlobalConfig config = injector.getInstance(GlobalConfig.class);
        System.out.println("loaded config with version " + config.configVersion);

        // register listeners
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(injector.getInstance(CommandListener.class), this);
        this.getServer().getPluginManager().registerEvents(injector.getInstance(UserListener.class), this);
    }

    @Override
    public void onDisable() {
        voxelGameLib.onDisable();
    }

    @CommandInfo(name = "test", perm = "command.test", role = Role.DEFAULT)
    public void command(CommandArguments args) {
        args.getSender().sendMessage(new TextComponent("Command send!"));
    }

    @CommandInfo(name = "chunktest", perm = "command.chunktest", role = Role.ADMIN)
    public void chunktest(CommandArguments args) {
        Location loc = new Location(Bukkit.getWorld(args.getSender().getWorld()), args.getSender().getLocation().getX(), args.getSender().getLocation().getY(), args.getSender().getLocation().getZ());
        args.getSender().sendMessage(new TextComponent("te: " + loc.getChunk().getTileEntities().length));
    }

    private class BukkitInjector extends AbstractModule {

        @Override
        protected void configure() {
            bind(CommandHandler.class).to(BukkitCommandHandler.class);
            bind(User.class).to(BukkitUser.class);
            bind(TickHandler.class).to(BukkitTickHandler.class);
            bind(ConsoleUser.class).to(BukkitConsoleUser.class);
            bind(MapScanner.class).to(BukkitMapScanner.class);
            bind(WorldHandler.class).to(BukkitWorldHandler.class).asEagerSingleton();

            bind(WorldConfig.class).toProvider(WorldHandler.class);
            bind(GlobalConfig.class).toProvider(ConfigHandler.class);

            bind(Gson.class).toInstance(new GsonBuilder().setPrettyPrinting().create());
            bind(VoxelGamesLibBukkit.class).toInstance(voxelGamesLibBukkit);

            bind(File.class).annotatedWith(Names.named("ConfigFolder")).toInstance(getDataFolder());
            bind(File.class).annotatedWith(Names.named("LangFolder")).toInstance(new File(getDataFolder(), "lang"));
            bind(File.class).annotatedWith(Names.named("WorldsFolder")).toInstance(new File(getServer().getWorldContainer(), "worlds"));
            bind(File.class).annotatedWith(Names.named("WorldContainer")).toInstance(getServer().getWorldContainer());
        }
    }
}
