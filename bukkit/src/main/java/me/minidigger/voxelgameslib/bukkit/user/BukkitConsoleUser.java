package me.minidigger.voxelgameslib.bukkit.user;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.block.Direction;
import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.user.AbstractConsoleUser;
import me.minidigger.voxelgameslib.api.utils.ChatUtil;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Created by Martin on 02.10.2016.
 */
public class BukkitConsoleUser extends AbstractConsoleUser<ConsoleCommandSender> {

    private ConsoleCommandSender user = Bukkit.getConsoleSender();

    @Inject
    private CommandHandler commandHandler;

    @Override
    public void setImplementationType(@Nonnull ConsoleCommandSender consoleCommandSender) {
        user = consoleCommandSender;
    }

    @Nonnull
    @Override
    public ConsoleCommandSender getImplementationType() {
        return user;
    }

    @Override
    public void executeCommand(String cmd) {
        if (!commandHandler.executeCommand(this, cmd)) {
            Bukkit.dispatchCommand(user, cmd);
        }
    }

    @Override
    public void sendMessage(@Nonnull BaseComponent... message) {
        user.sendMessage("[VoxelGamesLib]" + ChatUtil.toPlainText(message));
    }

    @Override
    public void markDisplayNameAsDirty() {
        // ignore
    }

    @Nonnull
    @Override
    public BaseComponent[] getDisplayName() {
        return new ComponentBuilder("CONSOLE").create();
    }

    @Override
    public Direction getFacingDirection() {
        return Direction.SELF;
    }
}
