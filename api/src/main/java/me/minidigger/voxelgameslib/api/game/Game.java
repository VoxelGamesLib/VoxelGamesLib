package me.minidigger.voxelgameslib.api.game;

import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.phase.Phase;
import me.minidigger.voxelgameslib.api.tick.Tickable;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;

/**
 * A {@link Game} is the representation of an instance of a {@link GameMode}. Handles everything
 * that is related to that {@link GameMode}: starting, stopping, {@link Phase}s etc.
 */
public interface Game extends Tickable {
    
    /**
     * initialises this game and all phases
     */
    void initGame();
    
    /**
     * Sends a message to every {@link me.minidigger.voxelgameslib.api.user.User} that
     * is related to this game. This could be a participant in the game or a spectator.
     *
     * @param message the message to be send
     */
    void broadcastMessage(@Nonnull BaseComponent... message);
    
    /**
     * Sends a message to everr User that
     * is related to this game. This could be a participant in the game or a spectator.
     *
     * @param key  the message to be send
     * @param args the arguments for the key
     */
    void broadcastMessage(@Nonnull LangKey key, Object... args);
    
    /**
     * Ends the current {@link Phase} and starts the next one.
     */
    void endPhase();
    
    /**
     * Ends the game, handles who has won.
     */
    void endGame();
    
    /**
     * @return the gamemode that is played in this game
     */
    @Nonnull
    GameMode getGameMode();
    
    /**
     * @return returns the {@link Phase} that is currently active
     */
    @Nonnull
    Phase getActivePhase();
    
    /**
     * Lets a user join this game
     *
     * @param user the user that wants to join this game
     */
    void join(User user);
    
    /**
     * lets a user spectate this game
     *
     * @param user the user which wants to spectate
     */
    void spectate(User user);
    
    /**
     * Lets a user leave this game
     *
     * @param user the user that wants to leave this game
     */
    void leave(User user);
    
    /**
     * Checks if that user is playing (not spectating!) this game
     *
     * @param user the user to check
     * @return if the user is playing this game
     */
    boolean isPlaying(User user);
    
    /**
     * Checks if that user is spectating (not playing!) this game
     *
     * @param user the user to check
     * @return if the user is spectating this game
     */
    boolean isSpectating(User user);
    
    /**
     * Creates a new feature class (using guice and stuff)
     *
     * @param featureClass the class of the feature that should be created
     * @param <T>          the feature
     * @param phase        the phase that the new feature should be attached to
     * @return the created feature instance
     */
    <T extends Feature> T createFeature(Class<T> featureClass, Phase phase);
    
    /**
     * Creates a new phase class (using guice and stuff)
     *
     * @param phaseClass the class of the phase that should be created
     * @param <T>        the phase
     * @return the created phase instance
     */
    <T extends Phase> T createPhase(Class<T> phaseClass);
    
    /**
     * @return the minimum amount of players for this game
     */
    int getMinPlayers();
    
    /**
     * @param minPlayers the minimum amount of players for this game
     */
    void setMinPlayers(int minPlayers);
    
    /**
     * @return the maximum amount of players for this game
     */
    int getMaxPlayers();
    
    /**
     * @param maxPlayers the maximum amount of players for this game
     */
    void setMaxPlayers(int maxPlayers);
}
