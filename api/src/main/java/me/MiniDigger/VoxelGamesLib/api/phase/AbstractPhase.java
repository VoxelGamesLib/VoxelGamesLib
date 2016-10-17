package me.MiniDigger.VoxelGamesLib.api.phase;

import me.MiniDigger.VoxelGamesLib.api.exception.NoSuchFeatureException;
import me.MiniDigger.VoxelGamesLib.api.feature.Feature;
import me.MiniDigger.VoxelGamesLib.api.game.Game;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Simple implementation of a {@link Phase}. Implements the necessary {@link Feature}-handling.
 */
public abstract class AbstractPhase implements Phase {
    
    private final String name;
    private final Game game;
    private final List<Feature> features;
    private final Phase nextPhase;
    
    /**
     * Constructs a new Phase.
     *
     * @param name      the name of this {@link Phase}
     * @param game      the {@link Game} this {@link Phase} is tied too
     * @param features  a list with all {@link Feature}s that are present in this {@link Phase}
     * @param nextPhase the {@link Phase} that will follow after this {@link Phase} has ended
     */
    public AbstractPhase(@Nonnull String name, @Nonnull Game game, @Nonnull List<Feature> features, @Nonnull Phase nextPhase) {
        this.name = name;
        this.game = game;
        this.features = features;
        this.nextPhase = nextPhase;
    }
    
    @Nonnull
    @Override
    public String getName() {
        return name;
    }
    
    @Nonnull
    @Override
    public Game getGame() {
        return game;
    }
    
    @Nonnull
    @Override
    public Feature getFeature(@Nonnull Class<Feature> clazz) {
        return features.stream().filter(f -> f.getClass().equals(clazz)).findFirst().orElseThrow(() -> new NoSuchFeatureException(clazz));
    }
    
    @Nonnull
    @Override
    public List<Feature> getFeatures() {
        return features;
    }
    
    @Nonnull
    @Override
    public Phase getNextPhase() {
        return nextPhase;
    }
    
    @Override
    public void start() {
        features.forEach(Feature::start);
    }
    
    @Override
    public void stop() {
        features.forEach(Feature::stop);
    }
    
    @Override
    public void tick() {
        features.forEach(Feature::tick);
    }
}
