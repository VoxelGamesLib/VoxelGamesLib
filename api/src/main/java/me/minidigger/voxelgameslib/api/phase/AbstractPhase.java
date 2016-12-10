package me.minidigger.voxelgameslib.api.phase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandHandler;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.exception.DependencyGraphException;
import me.minidigger.voxelgameslib.api.exception.NoSuchFeatureException;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.game.Game;
import me.minidigger.voxelgameslib.api.graph.Graph;
import me.minidigger.voxelgameslib.api.role.Role;

/**
 * Simple implementation of a {@link Phase}. Implements the necessary {@link Feature}-handling.
 */
public abstract class AbstractPhase implements Phase {

    @Inject
    private transient VGLEventHandler eventHandler;
    @Inject
    private transient CommandHandler commandHandler;

    private transient String name;
    private transient Game game;
    private String className;
    @Nonnull
    private List<Feature> features = new ArrayList<>();

    private boolean allowJoin;
    private boolean allowSpectate;

    private transient Phase nextPhase;

    public AbstractPhase() {
        className = getClass().getName();
    }

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
    public void setGame(@Nonnull Game game) {
        this.game = game;
    }

    @Override
    public void addFeature(@Nonnull Feature feature) {
        System.out.println("add " + feature.getClass().getSimpleName() + " feature");
        features.add(feature);
    }

    @Nonnull
    @Override
    public Game getGame() {
        return game;
    }

    @Nonnull
    @Override
    public <T extends Feature> T getFeature(@Nonnull Class<T> clazz) {
        return (T) features.stream().filter(f -> f.getClass().equals(clazz)).findFirst().orElseThrow(() -> new NoSuchFeatureException(clazz));
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
    public void init() {
        System.out.println("init " + getName());
    }

    @Override
    public void start() {
        checkDependencies();
        features.forEach((feature) -> {
            System.out.println("start " + feature.getName());
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
            System.out.println("stop " + feature.getName());
            feature.stop();
            new Throwable().printStackTrace();
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

    private void checkDependencies() {
        List<Feature> orderedFeatures = new ArrayList<>();
        System.out.println("before");
        System.out.println(features);
        try {
            Graph<Class> graph = new Graph<>(clazz -> {
                Optional<Feature> f = features.stream().filter(feature -> feature.getClass().equals(clazz)).findAny();
                f.ifPresent(orderedFeatures::add);
            });
            for (Feature feature : getFeatures()) {
                for (Class dependency : feature.getDependencies()) {
                    if (dependency.equals(feature.getClass())) {
                        System.out.println(feature.getName() + " tried to depend on itself...");
                        continue;
                    }
                    graph.addDependency(feature.getClass(), dependency);
                }
            }
            graph.generateDependencies();
        } catch (DependencyGraphException ex) {
            System.out.println("error while trying to generate dependency graph: " + ex.getMessage());
            ex.printStackTrace();
            getGame().stop();
            return;
        }

        Collections.reverse(orderedFeatures);
        features = orderedFeatures;
        System.out.println("after");
        System.out.println(features);
    }
}
