package me.minidigger.voxelgameslib.api.feature.features;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.block.BlockBreakEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.feature.FeatureInfo;

@FeatureInfo(name = "NoBlockBreakFeature", author = "MiniDigger", version = "1.0",
        description = "Small feature that blocks block breaking if active")
public class NoBlockBreakFeature extends AbstractFeature {
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
    @SuppressWarnings("unchecked")
    public Class<? extends Feature>[] getDependencies() {
        return new Class[0];
    }

    @EventListener
    @SuppressWarnings("JavaDoc")
    public void onBlockBreak(BlockBreakEvent event) {
        if (getPhase().getGame().isPlaying(event.getUser())) {
            //TODO allow some materials
            event.setCanceled(true);
        }
    }
}
