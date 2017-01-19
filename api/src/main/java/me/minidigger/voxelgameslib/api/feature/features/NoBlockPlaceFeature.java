package me.minidigger.voxelgameslib.api.feature.features;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.block.BlockPlaceEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.feature.FeatureInfo;

@FeatureInfo(name = "NoBlockPlaceFeature", author = "MiniDigger", version = "1.0",
        description = "Small feature that blocks block placing if active")
public class NoBlockPlaceFeature extends AbstractFeature {
    @Override
    public void start() {

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
    public void onBlockBreak(BlockPlaceEvent event) {
        if (getPhase().getGame().isPlaying(event.getUser())) {
            //TODO allow some materials
            event.setCanceled(true);
        }
    }
}
