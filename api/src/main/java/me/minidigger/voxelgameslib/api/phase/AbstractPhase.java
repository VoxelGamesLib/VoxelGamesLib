package me.minidigger.voxelgameslib.api.phase;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.exception.NoSuchFeatureException;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.game.Game;

/**
 * Simple implementation of a {@link Phase}. Implements the necessary {@link Feature}-handling.
 */
public abstract class AbstractPhase implements Phase {
    
    private String name;
    private Game game;
    private List<Feature> features = new ArrayList<>();
    private Phase nextPhase;
    private boolean allowJoin;
    private boolean allowSpectate;
    
    @Override
    public void setName(@Nonnull String name) {
        this.name = name;
    }
    
    @Nonnull
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void setNextPhase(Phase nextPhase) {
        this.nextPhase = nextPhase;
    }
    
    @Override
    public void setGame(Game game) {
        this.game = game;
    }
    
    @Override
    public void addFeature(Feature feature) {
        features.add(feature);
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
    
    @Override
    public boolean allowJoin() {
        return allowJoin;
    }
    
    @Override
    public void setAllowJoin(boolean allowJoin) {
        this.allowJoin = allowJoin;
    }
    
    @Override
    public boolean allowSpectate() {
        return allowSpectate;
    }
    
    @Override
    public void setAllowSpectate(boolean allowSpectate) {
        this.allowSpectate = allowSpectate;
    }
}
