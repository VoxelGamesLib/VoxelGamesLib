package me.minidigger.voxelgameslib.api.feature.features;

import com.google.gson.annotations.Expose;

import java.util.concurrent.TimeUnit;

import me.minidigger.voxelgameslib.api.VoxelGamesLib;
import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.game.GameJoinEvent;
import me.minidigger.voxelgameslib.api.event.events.game.GameLeaveEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.scoreboard.Scoreboard;

import co.aikar.taskchain.TaskChain;

/**
 * Small feature that handles stuff related to the lobby phase
 */
public class LobbyFeature extends AbstractFeature {

    private Scoreboard scoreboard;
    private boolean starting = false;
    private TaskChain startTask;
    @Expose
    private int startDelay = 30;

    @Override
    public void start() {
        scoreboard = getPhase().getFeature(ScoreboardFeature.class).getScoreboard();

        scoreboard.createAndAddLine("lobby-line", getPhase().getGame().getPlayers().size() + "/" + getPhase().getGame().getMinPlayers());
        scoreboard.createAndAddLine("Waiting for players...");
    }

    @Override
    public void stop() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void init() {

    }

    @Override
    public Class[] getDependencies() {
        return new Class[]{ScoreboardFeature.class};
    }

    @EventListener
    public void onJoin(GameJoinEvent event) {
        if (event.getGame().getUuid().equals(getPhase().getGame().getUuid())) {
            scoreboard.getLine("lobby-line").ifPresent(line -> line.setValue(getPhase().getGame().getPlayers().size() + "/" + getPhase().getGame().getMinPlayers()));

            if (getPhase().getGame().getPlayers().size() >= getPhase().getGame().getMinPlayers() && !starting) {
                starting = true;
                getPhase().getGame().broadcastMessage(LangKey.GAME_STARTING);
                VoxelGamesLib.newChain().delay(startDelay, TimeUnit.SECONDS).abortIf(!starting).sync(() -> getPhase().getGame().endPhase()).execute();
            }
        }
    }

    @EventListener
    public void onLeave(GameLeaveEvent event) {
        if (event.getGame().getUuid().equals(getPhase().getGame().getUuid())) {
            scoreboard.getLine("lobby-line").ifPresent(line -> line.setValue(getPhase().getGame().getPlayers().size() + "/" + getPhase().getGame().getMinPlayers()));
            if (getPhase().getGame().getPlayers().size() < getPhase().getGame().getMinPlayers() && starting) {
                starting = false;
                // TODO Cancel task here
                getPhase().getGame().broadcastMessage(LangKey.GAME_START_ABORTED);
            }
        }
    }
}
