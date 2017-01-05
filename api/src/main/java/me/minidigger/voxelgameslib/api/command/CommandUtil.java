package me.minidigger.voxelgameslib.api.command;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.api.utils.ChatUtil;

/**
 * Util methods related to the command system
 */
public class CommandUtil {

    @Inject
    private static CommandHandler commandHandler;
    @Inject
    private static Server server;

    /**
     * Filters the list to only include entries that start with the prefix
     *
     * @param prefix      The prefix every entry should start with
     * @param completions the possible completions
     * @return The filtered list
     */
    @Nonnull
    public static List<String> filterTabCompletions(@Nonnull String prefix, @Nonnull String... completions) {
        return Arrays.stream(completions).filter(s -> s.toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toList());
    }

    /**
     * Creates a list of filtered completions that contain all sub commands of {@code command} and
     * start with {@code prefix}
     *
     * @param prefix  The prefix every entry should start with
     * @param command The command which subcommands should be considered for inclusion in the
     *                result
     * @return the filtered list
     */
    @Nonnull
    public static List<String> completeWithSubCommands(@Nonnull String prefix, @Nonnull String command) {
        List<String> list = commandHandler.getCommandMap().keySet().stream().filter(s -> s.startsWith(command + ".")).collect(Collectors.toList());
        IntStream.range(0, list.size()).forEach(i -> list.set(i, list.get(i).replace(command + ".", "")));
        return filterTabCompletions(prefix, list.toArray(new String[list.size()]));
    }

    /**
     * Returns a list of players which names start with prefix
     *
     * @param prefix the prefix
     * @return the resulting list
     */
    @Nonnull
    public static List<String> completeWithPlayerNames(@Nonnull String prefix) {
        return server.getOnlineUsers().stream().map(user -> ChatUtil.toPlainText(user.getDisplayName()))
                .filter(name -> name.toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toList());
    }
}



























