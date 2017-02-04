package me.minidigger.voxelgameslib.api.matchmaking;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.game.GameMode;
import me.minidigger.voxelgameslib.api.handler.Handler;

/**
 * Created by Martin on 04.02.2017.
 */
@Singleton
public class MatchmakingHandler implements Handler {

    private Map<GameMode, Queue> queues;

    @Override
    public void start() {
        queues = new HashMap<>();
        //TODO match making
    }

    @Override
    public void stop() {
        queues.clear();
        queues = null;
    }
}
