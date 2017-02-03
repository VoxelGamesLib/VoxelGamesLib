package me.minidigger.voxelgameslib.bukkit.server;

import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.bossbar.BossBar;
import me.minidigger.voxelgameslib.api.bossbar.BossBarColor;
import me.minidigger.voxelgameslib.api.bossbar.BossBarModifier;
import me.minidigger.voxelgameslib.api.bossbar.BossBarStyle;
import me.minidigger.voxelgameslib.api.map.Vector3D;
import me.minidigger.voxelgameslib.api.scoreboard.Scoreboard;
import me.minidigger.voxelgameslib.api.server.AbstractServer;
import me.minidigger.voxelgameslib.api.user.ConsoleUser;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserHandler;
import me.minidigger.voxelgameslib.api.utils.Pair;
import me.minidigger.voxelgameslib.bukkit.bossbar.BossBarColorConverter;
import me.minidigger.voxelgameslib.bukkit.bossbar.BossBarStyleConverter;
import me.minidigger.voxelgameslib.bukkit.bossbar.BukkitBossBar;
import me.minidigger.voxelgameslib.bukkit.scoreboard.BukkitScoreboard;
import me.minidigger.voxelgameslib.bukkit.user.BukkitConsoleUser;
import me.minidigger.voxelgameslib.bukkit.world.VectorConverter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.extern.java.Log;

/**
 * Created by Martin on 23.11.2016.
 */
@Log
@Singleton
public class BukkitServer extends AbstractServer {

    private ConsoleUser user;

    @Inject
    private UserHandler userHandler;
    @Inject
    private BossBarColorConverter bossBarColorConverter;
    @Inject
    private BossBarStyleConverter bossBarStyleConverter;
    @Inject
    private VectorConverter vectorConverter;
    @Inject
    private Injector injector;

    @Override
    public List<User> getOnlineUsers() {
        List<User> users = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Optional<User> user = userHandler.getUser(player.getUniqueId());
            if (user.isPresent()) {
                users.add(user.get());
            } else {
                // log.warning("Could not find a user for player " + player.getDisplayName() + "(" + player.getUniqueId() + ")");
            }
        }
        return users;
    }

    @Override
    public ConsoleUser getConsoleUser() {
        if (user == null) {
            user = new BukkitConsoleUser();
            injector.injectMembers(user);
        }
        return user;
    }

    @Override
    public BossBar createBossBar(String title, BossBarColor color, BossBarStyle style, BossBarModifier... modifiers) {
        org.bukkit.boss.BossBar bossBar = Bukkit.createBossBar(title, bossBarColorConverter.fromVGL(color),
                bossBarStyleConverter.fromVGL(style));
        BossBar bar = new BukkitBossBar();
        //noinspection unchecked
        bar.setImplementationType(bossBar);
        for (BossBarModifier modifier : modifiers) {
            bar.addModifier(modifier);
        }
        return bar;
    }

    @Override
    public Scoreboard createScoreboard(String title) {
        BukkitScoreboard scoreboard = new BukkitScoreboard();
        scoreboard.setImplObject(Bukkit.getScoreboardManager().getNewScoreboard());
        scoreboard.setTitle(title);
        return scoreboard;
    }

    @Override
    public Pair<String, Vector3D> getSpawn() {
        Location spawn = Bukkit.getWorlds().get(0).getSpawnLocation();
        return new Pair<>(spawn.getWorld().getName(), vectorConverter.toVGL(spawn.toVector()));
    }
}
