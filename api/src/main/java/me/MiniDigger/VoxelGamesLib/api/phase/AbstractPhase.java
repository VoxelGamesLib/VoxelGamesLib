package me.MiniDigger.VoxelGamesLib.api.phase;

import java.util.List;

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
     * @param name the name of this {@link Phase}
     * @param game the {@link Game} this {@link Phase} is tied too
     * @param features a list with all {@link Feature}s that are present in this {@link Phase}
     * @param nextPhase the {@link Phase} that will follow after this {@link Phase} has ended
     */
    public AbstractPhase(String name, Game game, List<Feature> features, Phase nextPhase) {
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
    public Feature getFeature(Class<Feature> clazz) {
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
        features.stream().forEach(Feature::start);
    }

    @Override
    public void stop() {
        features.stream().forEach(Feature::stop);
    }

    @Override
    public void tick() {
        features.stream().forEach(Feature::tick);
    }
}
