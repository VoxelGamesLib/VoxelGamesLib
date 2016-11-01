package me.minidigger.voxelgameslib.api.phase;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.exception.NoSuchFeatureException;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.game.Game;
import me.minidigger.voxelgameslib.api.role.Role;

/**
 * Simple implementation of a {@link Phase}. Implements the necessary {@link Feature}-handling.
 */
public abstract class AbstractPhase implements Phase {
    
    @Inject
    private VGLEventHandler eventHandler;
    @Inject
    private CommandHandler commandHandler;
    
    private String name;
    private Game game;
    @Nonnull
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
    public void setNextPhase(@Nonnull Phase nextPhase) {
        this.nextPhase = nextPhase;
    }
    
    @Override
    public void setGame(@Nonnull Game game) {
        this.game = game;
    }
    
    @Override
    public void addFeature(@Nonnull Feature feature) {
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
        System.out.println("start " + getName());
        features.forEach((feature) -> {
            feature.start();
            eventHandler.registerEvents(feature);
            commandHandler.register(feature);
        });
        
        eventHandler.registerEvents(this);
        commandHandler.register(this);
    }
    
    @Override
    public void stop() {
        System.out.println("stop " + getName());
        features.forEach((feature) -> {
            feature.stop();
            eventHandler.unregisterEvents(feature);
            commandHandler.unregister(feature, true);
        });
    
        eventHandler.unregisterEvents(this);
        commandHandler.unregister(this, true);
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
    
    @SuppressWarnings("JavaDoc")
    @CommandInfo(name = "skip", perm = "command.skip", role = Role.MODERATOR)
    public void skip(@Nonnull CommandArguments arguments) {
        if (getGame().isPlaying(arguments.getSender()) || getGame().isSpectating(arguments.getSender())) {
            System.out.println("skip " + getName());
            getGame().endPhase();
        }
    }
}
