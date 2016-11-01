package me.minidigger.voxelgameslib.api.event.events.game;

import me.minidigger.voxelgameslib.api.game.Game;

/**
 * Called when a new game starts
 */
public class GameStartEvent extends GameEvent {
    
    /**
     * @param game the game that was started
     */
    public GameStartEvent(Game game) {
        super(game);
    }
}
