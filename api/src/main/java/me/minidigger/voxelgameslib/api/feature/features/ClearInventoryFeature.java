package me.minidigger.voxelgameslib.api.feature.features;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.game.GameJoinEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.user.User;

/**
 * Simple feature that clears the inventory of all players when the game starts (or a new player
 * joins)
 */
public class ClearInventoryFeature extends AbstractFeature {
    @Override
    public void start() {
        getPhase().getGame().getPlayers().forEach(User::clearInventory);
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

    @EventListener
    @SuppressWarnings("JavaDoc")
    public void onJoin(GameJoinEvent event) {
        if (event.getGame().getUuid().equals(getPhase().getGame().getUuid())) {
            event.getUser().clearInventory();
        }
    }
}
