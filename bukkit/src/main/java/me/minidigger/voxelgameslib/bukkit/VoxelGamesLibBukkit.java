package me.minidigger.voxelgameslib.bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.VoxelGamesLib;
import me.minidigger.voxelgameslib.api.block.Block;
import me.minidigger.voxelgameslib.api.bossbar.BossBar;
import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.command.CommandUtil;
import me.minidigger.voxelgameslib.api.config.ConfigHandler;
import me.minidigger.voxelgameslib.api.config.GlobalConfig;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.feature.FeatureTypeAdapter;
import me.minidigger.voxelgameslib.api.game.GameMode;
import me.minidigger.voxelgameslib.api.game.GameModeTypeAdapter;
import me.minidigger.voxelgameslib.api.item.Item;
import me.minidigger.voxelgameslib.api.item.ItemInstanceCreator;
import me.minidigger.voxelgameslib.api.log.LoggerHandler;
import me.minidigger.voxelgameslib.api.map.MapScanner;
import me.minidigger.voxelgameslib.api.phase.Phase;
import me.minidigger.voxelgameslib.api.phase.PhaseTypeAdapter;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.api.scoreboard.Scoreboard;
import me.minidigger.voxelgameslib.api.scoreboard.StringScoreboardLine;
import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.api.tick.TickHandler;
import me.minidigger.voxelgameslib.api.user.ConsoleUser;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.world.WorldConfig;
import me.minidigger.voxelgameslib.api.world.WorldHandler;
import me.minidigger.voxelgameslib.bukkit.block.BlockListener;
import me.minidigger.voxelgameslib.bukkit.block.BukkitBlock;
import me.minidigger.voxelgameslib.bukkit.bossbar.BukkitBossBar;
import me.minidigger.voxelgameslib.bukkit.command.BukkitCommandHandler;
import me.minidigger.voxelgameslib.bukkit.item.BukkitItem;
import me.minidigger.voxelgameslib.bukkit.log.BukkitLogHandler;
import me.minidigger.voxelgameslib.bukkit.map.BukkitMapScanner;
import me.minidigger.voxelgameslib.bukkit.server.BukkitServer;
import me.minidigger.voxelgameslib.bukkit.tick.BukkitTickHandler;
import me.minidigger.voxelgameslib.bukkit.user.BukkitConsoleUser;
import me.minidigger.voxelgameslib.bukkit.user.BukkitUser;
import me.minidigger.voxelgameslib.bukkit.user.UserListener;
import me.minidigger.voxelgameslib.bukkit.world.BukkitWorldHandler;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.ChatColor;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import lombok.extern.java.Log;

@Log
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
        cmdHandler.executeCommand(sender, "test command");

        // config test
        GlobalConfig config = injector.getInstance(GlobalConfig.class);
        log.info("loaded config with version " + config.configVersion);

        // register listeners
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(injector.getInstance(UserListener.class), this);
        this.getServer().getPluginManager().registerEvents(injector.getInstance(BlockListener.class), this);

        getLogger();
    }

    @Override
    public void onDisable() {
        voxelGameLib.onDisable();
    }

    @CommandInfo(name = "test", perm = "command.test", role = Role.DEFAULT)
    public void command(@Nonnull CommandArguments args) {
        args.getSender().sendMessage(new TextComponent("Command send!"));
        args.getSender().sendMessage(new TextComponent(Arrays.toString(args.getArgs())));
    }

    @CommandInfo(name = "chunktest", perm = "command.chunktest", role = Role.ADMIN)
    public void chunktest(@Nonnull CommandArguments args) {
        Location loc = new Location(Bukkit.getWorld(args.getSender().getWorld()), args.getSender().getLocation().getX(), args.getSender().getLocation().getY(), args.getSender().getLocation().getZ());
        args.getSender().sendMessage(new TextComponent("te: " + loc.getChunk().getTileEntities().length));
    }

    @CommandInfo(name = "chaintest", perm = "command.chaintest", role = Role.ADMIN)
    public void chaintest(CommandArguments args) {
        VoxelGamesLib.newChain().async(() -> System.out.println("Test")).delay(10, TimeUnit.SECONDS).sync(() -> System.out.println("Test")).execute();
    }

    @CommandInfo(name = "scoreboardtest", perm = "command.scoreboardtest", role = Role.ADMIN)
    public void scoreboardtest(CommandArguments args) {
        Scoreboard scoreboard = voxelGameLib.getInjector().getInstance(Server.class).createScoreboard("Test");
        scoreboard.addLine("test", new StringScoreboardLine("Value! " + ChatColor.RED + "COLOR!"));
    }

    @CommandInfo(name = "logtest", perm = "command.logtest", role = Role.ADMIN)
    public void logtest(CommandArguments arguments) {
        log.fine("THIS IS A PERFECTLY FINE MESSAGE. I AM NOT GOING INSANE");
        getLogger().info("test");
    }

    private class BukkitInjector extends AbstractModule {

        @Override
        protected void configure() {
            bind(CommandHandler.class).to(BukkitCommandHandler.class);
            bind(User.class).to(BukkitUser.class);
            bind(Item.class).to(BukkitItem.class);
            bind(TickHandler.class).to(BukkitTickHandler.class);
            bind(ConsoleUser.class).to(BukkitConsoleUser.class);
            bind(MapScanner.class).to(BukkitMapScanner.class);
            bind(BossBar.class).to(BukkitBossBar.class);
            bind(Block.class).to(BukkitBlock.class);
            bind(LoggerHandler.class).to(BukkitLogHandler.class).asEagerSingleton();
            bind(Server.class).to(BukkitServer.class).asEagerSingleton();
            bind(WorldHandler.class).to(BukkitWorldHandler.class).asEagerSingleton();

            bind(WorldConfig.class).toProvider(WorldHandler.class);
            bind(GlobalConfig.class).toProvider(ConfigHandler.class);

            bind(TaskChainFactory.class).toInstance(BukkitTaskChainFactory.create(voxelGamesLibBukkit));
            bind(VoxelGamesLibBukkit.class).toInstance(voxelGamesLibBukkit);

            bind(File.class).annotatedWith(Names.named("ConfigFolder")).toInstance(getDataFolder());
            bind(File.class).annotatedWith(Names.named("LangFolder")).toInstance(new File(getDataFolder().getAbsoluteFile(), "lang"));
            bind(File.class).annotatedWith(Names.named("WorldsFolder")).toInstance(new File(getServer().getWorldContainer().getAbsoluteFile(), "worlds"));
            bind(File.class).annotatedWith(Names.named("WorldContainer")).toInstance(getServer().getWorldContainer().getAbsoluteFile());
            bind(File.class).annotatedWith(Names.named("GameDefinitionFolder")).toInstance(new File(getDataFolder().getAbsoluteFile(), "games"));

            requestStaticInjection(VoxelGamesLib.class);
            requestStaticInjection(CommandUtil.class);
        }

        @Provides
        public Gson getGson(Injector injector) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Phase.class, injector.getInstance(PhaseTypeAdapter.class));
            builder.registerTypeAdapter(Feature.class, injector.getInstance(FeatureTypeAdapter.class));
            builder.registerTypeAdapter(GameMode.class, injector.getInstance(GameModeTypeAdapter.class));
            builder.registerTypeAdapter(Item.class, injector.getInstance(ItemInstanceCreator.class));
            builder.setPrettyPrinting();
            builder.excludeFieldsWithoutExposeAnnotation();
            return builder.create();
        }
    }
}
