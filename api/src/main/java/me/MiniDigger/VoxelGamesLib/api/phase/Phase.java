package me.MiniDigger.VoxelGamesLib.api.phase;

import java.util.List;

import javax.annotation.Nonnull;

import me.MiniDigger.VoxelGamesLib.api.feature.Feature;
import me.MiniDigger.VoxelGamesLib.api.feature.NoSuchFeatureException;
import me.MiniDigger.VoxelGamesLib.api.game.Game;
import me.MiniDigger.VoxelGamesLib.api.tick.Tickable;

/**
 * A {@link Phase} is directly tied to a {@link Game}. A {@link Phase} is a collection of
 * {@link Feature}. A {@link Phase} is directly linked to the next {@link Phase}.
 */
public interface Phase extends Tickable {

    /**
     * @return the name of this {@link Phase}
     */
    String getName();

    /**
     * @return the {@link Game}, this {@link Phase} is tied to
     */
    Game getGame();

    /**
     * Searched for a instance of the specified class.
     * 
     * @param clazz the class of the {@link Feature}
     * @return the instane of the class, if present.
     * @throws NoSuchFeatureException if this phase doesn't has that feature registered
     */
    Feature getFeature(@Nonnull Class<Feature> clazz);

    /**
     * @return a list with all {@link Feature}s that are present in this {@link Phase}
     */
    List<Feature> getFeatures();

    /**
     * @return the {@link Phase} that will follow after this {@link Phase} has ended.
     */
    Phase getNextPhase();
}
