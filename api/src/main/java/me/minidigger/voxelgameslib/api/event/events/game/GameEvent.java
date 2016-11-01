package me.minidigger.voxelgameslib.api.event.events.game;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.event.Event;
import me.minidigger.voxelgameslib.api.game.Game;

/**
 * Parent class for game events
 */
public class GameEvent implements Event {
    
    private Game game;
    
    GameEvent(@Nonnull Game game) {
        this.game = game;
    }
    
    /**
     * @return the game involved with this event
     */
    @Nonnull
    public Game getGame() {
        return game;
    }
}
