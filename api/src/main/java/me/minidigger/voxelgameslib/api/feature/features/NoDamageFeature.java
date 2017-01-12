package me.minidigger.voxelgameslib.api.feature.features;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.user.UserDamageEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;

/**
 * Small feature that disables any damage
 */
public class NoDamageFeature extends AbstractFeature {
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
    public void onDmg(UserDamageEvent event) {
        if (getPhase().getGame().isPlaying(event.getUser())) {
            event.setCanceled(true);
        }
    }
}
