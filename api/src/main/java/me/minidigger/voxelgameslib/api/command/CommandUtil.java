package me.minidigger.voxelgameslib.api.command;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * Util methods related to the command system
 */
public class CommandUtil {
    
    /**
     * Filters the list to only include entries that start with the prefix
     *
     * @param list   The list that should be filtered
     * @param prefix The prefix every entry should start with
     * @return The filtered list
     */
    @Nonnull
    public static List<String> filterTabCompletions(@Nonnull List<String> list, @Nonnull String prefix) {
        return list.stream().filter(s -> s.toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toList());
    }
}