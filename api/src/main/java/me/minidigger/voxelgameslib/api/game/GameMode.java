package me.minidigger.voxelgameslib.api.game;

import javax.annotation.Nonnull;

/**
 * A {@link GameMode} is a identifier for the type of a {@link Game}.
 */
public class GameMode {
    
    private final String name;
    private final Class<? extends Game> gameClass;
    
    /**
     * Constructs a new {@link GameMode}
     *
     * @param name      the name of this {@link GameMode}
     * @param gameClass the class that implements this {@link GameMode}
     */
    public GameMode(@Nonnull String name, @Nonnull Class<? extends Game> gameClass) {
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
    public Class<? extends Game> getGameClass() {
        return gameClass;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        GameMode gameMode = (GameMode) o;
        
        if (name != null ? !name.equals(gameMode.name) : gameMode.name != null) return false;
        return gameClass != null ? gameClass.equals(gameMode.gameClass) : gameMode.gameClass == null;
        
    }
    
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (gameClass != null ? gameClass.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "GameMode{" +
                "name='" + name + '\'' +
                ", gameClass=" + gameClass +
                '}';
    }
}
