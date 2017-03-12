package me.minidigger.voxelgameslib.api.feature.features;

import com.google.gson.annotations.Expose;

import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.bossbar.BossBar;
import me.minidigger.voxelgameslib.api.bossbar.BossBarColor;
import me.minidigger.voxelgameslib.api.bossbar.BossBarStyle;
import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.game.GameJoinEvent;
import me.minidigger.voxelgameslib.api.event.events.game.GameLeaveEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.server.Server;

/**
 * Provides a boss bar instance for other features
 */
public class BossBarFeature extends AbstractFeature {

    @Expose
    private String message = "";
    @Expose
    private BossBarColor color = BossBarColor.BLUE;
    @Expose
    private BossBarStyle style = BossBarStyle.SPLIT_20;
    @Inject
    private Server server;

    private BossBar bossBar;

    /**
     * @return the bossbar that will be used for this phase
     */
    public BossBar getBossBar() {
        return bossBar;
    }

    @Override
    public void start() {
        bossBar = server.createBossBar(message, color, style);

        getPhase().getGame().getPlayers().forEach(user -> bossBar.addUser(user));
    }

    @Override
    public void stop() {
        bossBar.removeAll();
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

    @EventListener
    public void onGameJoin(GameJoinEvent event) {
        if (event.getGame().getUuid().equals(getPhase().getGame().getUuid())) {
            bossBar.addUser(event.getUser());
        }
    }

    @EventListener
    public void onGameLeave(GameLeaveEvent event) {
        if (event.getGame().getUuid().equals(getPhase().getGame().getUuid())) {
            bossBar.removeUser(event.getUser());
        }
    }
}
