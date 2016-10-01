package me.MiniDigger.VoxelGamesLib.api.phase;

import java.util.List;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.feature.Feature;
import me.MiniDigger.VoxelGamesLib.api.feature.NoSuchFeatureException;
import me.MiniDigger.VoxelGamesLib.api.game.Game;

/**
 * Simple implementation of a {@link Phase}. Implements the necessary {@link Feature}-handling.
 */
public abstract class AbstractPhase implements Phase {

    private String name;
    private Game game;
    private List<Feature> features;
    private Phase nextPhase;

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public Feature getFeature(@Nonnull Class<Feature> clazz) {
        return features.stream().filter(f -> f.getClass().equals(clazz)).findFirst().orElseThrow(() -> new NoSuchFeatureException(clazz));
    }

    @Override
    public List<Feature> getFeatures() {
        return features;
    }

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
