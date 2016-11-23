package me.minidigger.voxelgameslib.api.game;

import java.util.Map;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.phase.Phase;

/**
 * Holds all important information of a game, ready to be saved to and loaded from json
 */
public class GameDefinition {
    
    private GameMode gameMode;
    
    private int minPlayers;
    private int maxPlayers;
    
    private Phase firstPhase;
    
    private Map<String, Object> gameData;
    
    /**
     * @return the gamemode for this definition
     */
    @Nonnull
    public GameMode getGameMode() {
        return gameMode;
    }
    
    /**
     * @param gameMode the gamemode for this definition
     */
    public void setGameMode(@Nonnull GameMode gameMode) {
        this.gameMode = gameMode;
    }
    
    /**
     * @return the min amount of players for this game
     */
    public int getMinPlayers() {
        return minPlayers;
    }
    
    /**
     * @param minPlayers the min amount of players for this game
     */
    
    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }
    
    /**
     * @return the max amount of players for this game
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }
    
    /**
     * @param maxPlayers the max amount of players for this game
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    
    /**
     * @return the first phase
     */
    @Nonnull
    public Phase getFirstPhase() {
        return firstPhase;
    }
    
    /**
     * @param firstPhase the first phase
     */
    public void setFirstPhase(Phase firstPhase) {
        this.firstPhase = firstPhase;
    }
    
    /**
     * @param gameData the game data map
     */
    public void setGameData(Map<String, Object> gameData) {
        this.gameData = gameData;
    }
    
    /**
     * @return the game data map
     */
    public Map<String, Object> getGameData() {
        return gameData;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        GameDefinition that = (GameDefinition) o;
        
        if (minPlayers != that.minPlayers) return false;
        if (maxPlayers != that.maxPlayers) return false;
        if (gameMode != null ? !gameMode.equals(that.gameMode) : that.gameMode != null)
            return false;
        if (firstPhase != null ? !firstPhase.equals(that.firstPhase) : that.firstPhase != null)
            return false;
        return gameData != null ? gameData.equals(that.gameData) : that.gameData == null;
    }
    
    @Override
    public int hashCode() {
        int result = gameMode != null ? gameMode.hashCode() : 0;
        result = 31 * result + minPlayers;
        result = 31 * result + maxPlayers;
        result = 31 * result + (firstPhase != null ? firstPhase.hashCode() : 0);
        result = 31 * result + (gameData != null ? gameData.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "GameDefinition{" +
                "gameMode=" + gameMode +
                ", minPlayers=" + minPlayers +
                ", maxPlayers=" + maxPlayers +
                ", firstPhase=" + firstPhase +
                ", gameData=" + gameData +
                '}';
    }
}
