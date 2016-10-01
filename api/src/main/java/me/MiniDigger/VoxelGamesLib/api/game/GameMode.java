package me.MiniDigger.VoxelGamesLib.api.game;

import javax.annotation.Nonnull;

/**
 * A {@link GameMode} is a identifier for the type of a {@link Game}.
 */
public class GameMode {

    private String name;
    private Class<Game> gameClass;

    /**
     * Constructs a new {@link GameMode}
     *
     * @param name      the name of this {@link GameMode}
     * @param gameClass the class that implements this {@link GameMode}
     */
    public GameMode(@Nonnull String name, @Nonnull Class<Game> gameClass) {
        this.name = name;
        this.gameClass = gameClass;
    }

    /**
     * @return the name of this {@link GameMode}
     */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * @return the class that implements this {@link GameMode}
     */
    @Nonnull
    public Class<Game> getGameClass() {
        return gameClass;
    }
}
