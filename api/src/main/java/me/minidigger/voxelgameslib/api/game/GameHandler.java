package me.minidigger.voxelgameslib.api.game;

import com.google.inject.Injector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.exception.GameModeNotAvailableException;
import me.minidigger.voxelgameslib.api.exception.GameStartException;
import me.minidigger.voxelgameslib.api.handler.Handler;
import me.minidigger.voxelgameslib.api.tick.TickHandler;
import me.minidigger.voxelgameslib.api.user.User;

/**
 * Handles all {@link Game} instances and all {@link GameMode}s.
 */
@Singleton
public class GameHandler implements Handler {
    
    @Inject
    private TickHandler tickHandler;
    @Inject
    private Injector injector;
    
    private final List<Game> games = new ArrayList<>();
    private final List<GameMode> modes = new ArrayList<>();
    
    @Override
    public void start() {
        //TODO debug code
        registerGameMode(new GameMode("Test", null));
    }
    
    @Override
    public void stop() {
        games.forEach(Game::stop);
        games.clear();
    }
    
    /**
     * Registers a new {@link GameMode}. Fails silently if that {@link GameMode} is already
     * registered.
     *
     * @param mode the new mode to be registered
     */
    public void registerGameMode(@Nonnull GameMode mode) {
        if (!modes.contains(mode)) {
            modes.add(mode);
        }
    }
    
    /**
     * Starts a new {@link Game} instance of that {@link GameMode}.
     *
     * @param mode the {@link GameMode} that should be started.
     * @return the started {@link Game}
     * @throws GameModeNotAvailableException if that {@link GameMode} is not registered on this
     *                                       server
     * @throws GameStartException            if something goes wrong while starting
     */
    @Nonnull
    public Game startGame(@Nonnull GameMode mode) {
        if (!modes.contains(mode)) {
            throw new GameModeNotAvailableException(mode);
        }
        
        Game game = injector.getInstance(mode.getGameClass());
        games.add(game);
        game.initGame();
        tickHandler.registerTickable(game);
        return game;
    }
    
    /**
     * @return a list with all registered gamemodes
     */
    public List<GameMode> getGameModes() {
        return modes;
    }
    
    /**
     * Gets a list of all games the user is playing in or spectating (if spectating is set to true)
     *
     * @param user     the user which games should be returned
     * @param spectate if we should include games where the user spectates
     * @return the games of that users
     */
    public List<Game> getGames(User user, boolean spectate) {
        Stream<Game> s = games.stream().filter(game -> game.isPlaying(user));
        if (spectate) {
            s = s.filter(game -> game.isSpectating(user));
        }
        return s.collect(Collectors.toList());
    }
}
