package me.MiniDigger.VoxelGamesLib.api.phase;

import java.util.List;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.feature.Feature;
import me.MiniDigger.VoxelGamesLib.api.game.Game;

/**
 * A special {@link Phase} that automatically ends after a specified amount of ticks. 
 */
public class TimedPhase extends AbstractPhase {

    private int ticks;

    /**
     * Constructs a new Phase.
     * 
     * @param name the name of this {@link Phase}
     * @param game the {@link Game} this {@link Phase} is tied too
     * @param features a list with all {@link Feature}s that are present in this {@link Phase}
     * @param nextPhase the {@link Phase} that will follow after this {@link Phase} has ended
     * @param ticks the amount of ticks this phase will tick. After that, the phase will end.
     */
    public TimedPhase(@Nonnull String name, @Nonnull Game game, @Nonnull List<Feature> features, @Nonnull Phase nextPhase, int ticks) {
        super(name, game, features, nextPhase);
        this.ticks = ticks;
    }

    @Override
    public void tick() {
        super.tick();
        ticks--;
        
        if(ticks <= 0){
            getGame().endPhase();
        }
    }
}
