package me.minidigger.voxelgameslib.bukkit.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.bossbar.BossBar;
import me.minidigger.voxelgameslib.api.bossbar.BossBarColor;
import me.minidigger.voxelgameslib.api.bossbar.BossBarModifier;
import me.minidigger.voxelgameslib.api.bossbar.BossBarStyle;
import me.minidigger.voxelgameslib.api.scoreboard.Scoreboard;
import me.minidigger.voxelgameslib.api.server.AbstractServer;
import me.minidigger.voxelgameslib.api.user.ConsoleUser;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserHandler;
import me.minidigger.voxelgameslib.bukkit.bossbar.BukkitBossBar;
import me.minidigger.voxelgameslib.bukkit.scoreboard.BukkitScoreboard;
import me.minidigger.voxelgameslib.bukkit.user.BukkitConsoleUser;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import lombok.extern.java.Log;

/**
 * Created by Martin on 23.11.2016.
 */
@Log
@Singleton
public class BukkitServer extends AbstractServer {
    
    private ConsoleUser user = new BukkitConsoleUser();
    
    @Inject
    private UserHandler userHandler;
    
    @Override
    public List<User> getOnlineUsers() {
        List<User> users = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Optional<User> user = userHandler.getUser(player.getUniqueId());
            if (user.isPresent()) {
                users.add(user.get());
            } else {
                log.warning("Could not find a user for player " + player.getDisplayName() + "(" + player.getUniqueId() + ")");
            }
        }
        return users;
    }
    
    @Override
    public ConsoleUser getConsoleUser() {
        return user;
    }
    
    @Override
    public BossBar createBossBar(String title, BossBarColor color, BossBarStyle style, BossBarModifier... modifiers) {
        org.bukkit.boss.BossBar bossBar = Bukkit.createBossBar(title, BarColor.valueOf(color.name()),
                BarStyle.valueOf(style.name().replace("SPILT", "SEGMENTED")));
        BossBar bar = new BukkitBossBar();
        bar.setImplObject(bossBar);
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
}
