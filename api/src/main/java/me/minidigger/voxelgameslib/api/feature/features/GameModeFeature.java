package me.minidigger.voxelgameslib.api.feature.features;

import com.google.gson.annotations.Expose;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.game.GameJoinEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.user.GameMode;

/**
 * Created by Martin on 14.01.2017.
 */
public class GameModeFeature extends AbstractFeature {

    @Expose
    private GameMode mode = GameMode.SURVIVAL;

    @Override
    public void start() {
        getPhase().getGame().getPlayers().forEach(u -> u.setGameMode(mode));
    }

    @EventListener
    public void onJoin(GameJoinEvent event) {
        if (event.getGame().getUuid().equals(getPhase().getGame().getUuid())) {
            event.getUser().setGameMode(mode);
        }
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
        return new Class[0];
    }

    /**
     * Sets the gamemode for this phase
     *
     * @param gameMode the new gamemode
     */
    public void setGameMode(GameMode gameMode) {
        this.mode = gameMode;
    }
}
