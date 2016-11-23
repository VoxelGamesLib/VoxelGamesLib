package me.minidigger.voxelgameslib.bukkit.server;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;
import me.minidigger.voxelgameslib.api.server.AbstractServer;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.user.UserHandler;

/**
 * Created by Martin on 23.11.2016.
 */
@Log
@Singleton
public class BukkitServer extends AbstractServer {

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
}
