package me.MiniDigger.VoxelGamesLib.api.exception;

import java.io.IOException;

/**
 * Thrown when something is wrong related to lang stuff
 */
public class LangException extends VoxelGameLibException {
    
    public LangException(String message) {
        super(message);
    }
    
    public LangException(String message, IOException ex) {
        super(message, ex);
    }
}
