package me.minidigger.voxelgameslib.api.feature;

import me.minidigger.voxelgameslib.api.game.GameMode;
import me.minidigger.voxelgameslib.api.phase.Phase;
import me.minidigger.voxelgameslib.api.tick.Tickable;

/**
 * A {@link Feature} is a piece of logic that is active during a {@link Phase}. It typically listens
 * to certain events and reacts based of that. A {@link Feature} is a small module that can be
 * reused in a variety of {@link GameMode}s
 */
public interface Feature extends Tickable {
    
    /**
     * @return the {@link Phase} this {@link Feature} is attached too.
     */
    Phase getPhase();
}
