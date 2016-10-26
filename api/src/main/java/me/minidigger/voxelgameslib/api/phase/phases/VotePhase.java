package me.minidigger.voxelgameslib.api.phase.phases;

import java.util.List;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.game.Game;
import me.minidigger.voxelgameslib.api.phase.Phase;
import me.minidigger.voxelgameslib.api.phase.TimedPhase;

/**
 * Created by Martin on 26.10.2016.
 */
public class VotePhase extends TimedPhase {
    
    /**
     * Constructs a new Phase.
     *
     * @param name      the name of this {@link Phase}
     * @param game      the {@link Game} this {@link Phase} is tied too
     * @param features  a list with all {@link Feature}s that are present in this {@link Phase}
     * @param nextPhase the {@link Phase} that will follow after this {@link Phase} has ended
     * @param ticks     the amount of ticks this phase will tick. After that, the phase will end.
     */
    public VotePhase(@Nonnull String name, @Nonnull Game game, @Nonnull List<Feature> features, @Nonnull Phase nextPhase, int ticks) {
        super(name, game, features, nextPhase, ticks);
    }
    
    //TODO vote phase
}
