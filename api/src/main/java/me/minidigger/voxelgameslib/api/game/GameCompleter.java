package me.minidigger.voxelgameslib.api.game;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandExecutor;
import me.minidigger.voxelgameslib.api.command.CommandUtil;
import me.minidigger.voxelgameslib.api.command.CompleterInfo;

import lombok.extern.java.Log;

@Log
@Singleton
@CommandExecutor
@SuppressWarnings("JavaDoc") // commands don't need javadoc, go read the command's descriptions
public class GameCompleter {
    
    @CompleterInfo(name = "game")
    public List<String> game(@Nonnull CommandArguments args) {
        return CommandUtil.completeWithSubCommands(args.getArg(0), "game");
    }
    
    @CompleterInfo(name = "game.list")
    public List<String> list(@Nonnull CommandArguments args) {
        return new ArrayList<>();
    }
    
    @CompleterInfo(name = "game.listmodes")
    public List<String> listmodes(@Nonnull CommandArguments args) {
        return new ArrayList<>();
    }
    
    @CompleterInfo(name = "game.start")
    public List<String> start(@Nonnull CommandArguments args) {
        return new ArrayList<>(); //TODO game.start completer
    }
    
    @CompleterInfo(name = "game.join")
    public List<String> join(@Nonnull CommandArguments args) {
        return new ArrayList<>(); //TODO game.join completer
    }
    
    @CompleterInfo(name = "game.leave")
    public List<String> leave(@Nonnull CommandArguments args) {
        return new ArrayList<>(); //TODO game.leave completer
    }
}
