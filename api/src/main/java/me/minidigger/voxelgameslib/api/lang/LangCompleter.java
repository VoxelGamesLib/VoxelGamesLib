package me.minidigger.voxelgameslib.api.lang;

import java.util.Arrays;
import java.util.List;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandExecutor;
import me.minidigger.voxelgameslib.api.command.CommandUtil;
import me.minidigger.voxelgameslib.api.command.CompleterInfo;


@Singleton
@CommandExecutor
@SuppressWarnings("JavaDoc") // commands don't need javadoc, go read the command's descriptions
public class LangCompleter {
    
    @CompleterInfo(name = "lang")
    public List<String> lang(CommandArguments arguments) {
        return CommandUtil.completeWithSubCommands(arguments.getArg(0), "lang");
    }
    
    @CompleterInfo(name = "lang.set")
    public List<String> set(CommandArguments arguments) {
        return CommandUtil.filterTabCompletions(arguments.getNumArgs() == 0 ? "" : arguments.getArg(0),
                Arrays.stream(Locale.values()).map(Locale::getTag).toArray(String[]::new));
    }
}
