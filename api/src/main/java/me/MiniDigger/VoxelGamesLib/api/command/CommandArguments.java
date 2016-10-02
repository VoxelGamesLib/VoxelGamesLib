package me.MiniDigger.VoxelGamesLib.api.command;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.user.User;

/**
 * Holds all information about a command that was excuted by a user
 *
 * @author MiniDigger
 * @version 1.0.0
 */
public class CommandArguments {

    private User sender;
    private CommandInfo command;
    private String[] args;

    CommandArguments(@Nonnull CommandInfo command, @Nonnull User sender, @Nonnull String[] args) {
        this.args = args;
        this.command = command;
        this.sender = sender;
    }

    /**
     * @return The {@link User} who executed the command.
     */
    @Nonnull
    public User getSender() {
        return sender;
    }

    /**
     * @param i The index
     * @return The argument at index i
     */
    @Nonnull
    public String getArg(int i) {
        return args[i];
    }

    /**
     * @return The number of arguments the sender entered
     */
    public int getNumArgs() {
        return args.length;
    }

    /**
     * @return All arguments the sender entered
     */
    @Nonnull
    public String[] getArgs() {
        return args;
    }

    /**
     * @return The original command info
     */
    @Nonnull
    public CommandInfo getCommand() {
        return command;
    }
}