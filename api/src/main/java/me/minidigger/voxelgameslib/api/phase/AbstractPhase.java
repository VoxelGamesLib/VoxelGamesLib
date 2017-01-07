package me.minidigger.voxelgameslib.api.phase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
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
    
    private String name;
    private transient Game game;
    private String className;
    @Nonnull
    private List<Feature> features = new ArrayList<>();
    
    private boolean allowJoin;
    private boolean allowSpectate;
    
    private transient Phase nextPhase;
    private transient boolean isRunning;
    
    public AbstractPhase() {
        className = getClass().getName().replace(PhaseTypeAdapter.DEFAULT_PATH + ".", "");
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
        //noinspection unchecked
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
        System.out.println("start phase " + getName() + ": size " + features.size());
        if (features.size() == 3) {
            System.out.println(features.get(0) + " ---- " + features.get(1) + " ---- " + features.get(2));
            System.out.println(features.get(0).getName() + " ---- " + features.get(1).getName() + " ---- " + features.get(2).getName());
        }
        if (!checkDependencies()) {
            game.endGame();
            return;
        }
        for (Feature feature : features) {
            System.out.println("start " + feature.getName());
            try {
                feature.start();
            } catch (Exception ex) {
                System.out.println("error while starting " + feature.getName());
                ex.printStackTrace();
                game.endGame();
                return;
            }
            eventHandler.registerEvents(feature);
            commandHandler.register(feature);
        }
        
        eventHandler.registerEvents(this);
        commandHandler.register(this);
    }
    
    @Override
    public void stop() {
        System.out.println("stop phase " + getName());
        for (Feature feature : features) {
            System.out.println("stop " + feature.getName());
            try {
                feature.stop();
            } catch (Exception ex) {
                System.out.println("error while stopping " + feature.getName());
                ex.printStackTrace();
                return;
            }
            eventHandler.unregisterEvents(feature);
            commandHandler.unregister(feature, true);
        }
        
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
    
    @Override
    public void setRunning(boolean running) {
        isRunning = running;
    }
    
    @Override
    public boolean isRunning() {
        return isRunning;
    }
    
    private boolean checkDependencies() {
        List<Class> orderedFeatures = new ArrayList<>();
        List<Class> added = new ArrayList<>();
        try {
            Graph<Class> graph = new Graph<>(orderedFeatures::add);
    
            // add all dependencies to the graph
            for (Feature feature : getFeatures()) {
                for (Class dependency : feature.getDependencies()) {
                    if (dependency.equals(feature.getClass())) {
                        System.out.println(feature.getName() + " tried to depend on itself...");
                        continue;
                    }
                    graph.addDependency(feature.getClass(), dependency);
    
                    added.add(feature.getClass());
                    added.add(dependency);
                }
            }
    
            // add features that have no dependency connection to any other feature. they can't be left out alone!
            for (Feature feature : getFeatures()) {
                if (!added.contains(feature.getClass())) {
                    orderedFeatures.add(feature.getClass());
                }
            }
            added.clear();
    
            // do the magic!
            graph.generateDependencies();
        } catch (DependencyGraphException ex) {
            System.out.println("error while trying to generate dependency graph: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    
        if (features.size() != orderedFeatures.size()) {
            throw new RuntimeException("WTF HAPPENED HERE?!" + features.size() + " " + orderedFeatures.size());
        }
    
        // reverse order because dependencies need to be run before dependend features
        Collections.reverse(orderedFeatures);
        // remap classes to features
        features = orderedFeatures.stream().map((Function<Class, Feature>) this::getFeature).collect(Collectors.toList());
        
        return true;
    }
}
