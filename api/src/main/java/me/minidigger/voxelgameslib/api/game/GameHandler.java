package me.minidigger.voxelgameslib.api.game;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.exception.GameModeNotAvailableException;
import me.minidigger.voxelgameslib.api.exception.GameStartException;
import me.minidigger.voxelgameslib.api.handler.Handler;

/**
 * Handles all {@link Game} instances and all {@link GameMode}s.
 */
@com.google.inject.Singleton
public class GameHandler implements Handler {
    
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
        
        try {
            Game game = mode.getGameClass().newInstance();
            games.add(game);
            // TODO register tickable here
            return game;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new GameStartException(mode, e);
        }
    }
    
    /**
     * @return a list with all registered gamemodes
     */
    public List<GameMode> getGameMode() {
        return modes;
    }
}
