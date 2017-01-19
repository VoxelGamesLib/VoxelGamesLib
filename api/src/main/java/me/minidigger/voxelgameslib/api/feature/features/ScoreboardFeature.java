package me.minidigger.voxelgameslib.api.feature.features;

import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.game.GameJoinEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.feature.FeatureInfo;
import me.minidigger.voxelgameslib.api.scoreboard.Scoreboard;
import me.minidigger.voxelgameslib.api.server.Server;

@FeatureInfo(name = "ScoreboardFeature", author = "MiniDigger", version = "1.0",
        description = "Handles the scoreboard for all other features")
public class ScoreboardFeature extends AbstractFeature {

    @Inject
    private Server server;

    private Scoreboard scoreboard;

    @Override
    public void start() {
        getPhase().getGame().getPlayers().forEach(scoreboard::addUser);
        getPhase().getGame().getSpectators().forEach(scoreboard::addUser);
    }

    @Override
    public void stop() {
        scoreboard.removeAllLines();
        scoreboard.removeAllUsers();
    }

    @Override
    public void tick() {

    }

    @Override
    public void init() {
        scoreboard = server.createScoreboard(getPhase().getGame().getGameMode().getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Feature>[] getDependencies() {
        return new Class[0];
    }

    @EventListener
    @SuppressWarnings("JavaDoc")
    public void onJoin(GameJoinEvent event) {
        if (event.getGame().getUuid().equals(getPhase().getGame().getUuid())) {
            scoreboard.addUser(event.getUser());
        }
    }

    /**
     * @return the scoreboard instance that will be used for this phase
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
