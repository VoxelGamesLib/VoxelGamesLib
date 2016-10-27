package me.minidigger.voxelgameslib.api.game;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.role.Role;

@Singleton
@SuppressWarnings("JavaDoc") // commands don't need javadoc, go read the command's descriptions
public class GameCommands {
    
    @Inject
    private GameHandler gameHandler;
    
    @CommandInfo(name = "game", perm = "command.game", role = Role.DEFAULT)
    public void game(CommandArguments args) {
        // todo game command
    }
    
    @CommandInfo(name = "game.list", perm = "command.game.list", role = Role.DEFAULT, description = "Shows currently running games")
    public void gameList(CommandArguments args) {
        // todo game list command
    }
    
    @CommandInfo(name = "game.listmodes", perm = "command.game.listmodes", role = Role.DEFAULT, description = "Shows currently installed gamemodes")
    public void gameListModes(CommandArguments args) {
        StringBuilder sb = new StringBuilder();
        gameHandler.getGameModes().forEach(m -> sb.append(m.getName()).append(", "));
        sb.replace(sb.length() - 2, sb.length(), ".");
        Lang.msg(args.getSender(), LangKey.GAME_INSTALLED_GAMEMODES, sb.toString());
    }
    
    @CommandInfo(name = "game.start", perm = "command.game.start", role = Role.MODERATOR, description = "Starts a new game")
    public void gameStart(CommandArguments args) {
        // todo game start command
        //   gameHandler.startGame(gamemode);
    }
    
    @CommandInfo(name = "game.join", perm = "command.game.join", role = Role.DEFAULT, description = "Joins a game")
    public void gameJoin(CommandArguments args) {
        // todo game join command
    }
    
    @CommandInfo(name = "game.leave", perm = "command.game.leave", role = Role.DEFAULT, description = "Leave a game")
    public void gameLeave(CommandArguments args) {
        // todo game leave command
    }
}
