package me.minidigger.voxelgameslib.api.event.events.game;

import me.minidigger.voxelgameslib.api.event.Event;
import me.minidigger.voxelgameslib.api.game.Game;

/**
 * Parent class for game events
 */
public class GameEvent implements Event {
    
    private Game game;
    
    GameEvent(Game game) {
        this.game = game;
    }
    
    /**
     * @return the game involved with this event
     */
    public Game getGame() {
        return game;
    }
}
