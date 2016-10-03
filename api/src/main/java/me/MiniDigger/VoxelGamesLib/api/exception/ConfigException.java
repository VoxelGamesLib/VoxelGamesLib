package me.MiniDigger.VoxelGamesLib.api.exception;

/**
 * Thrown when something goes wrong related to configs, like loading or saving.
 */
public class ConfigException extends VoxelGameLibException {

    /**
     * @param msg a short message with additional information
     * @param ex the exception that was originally thrown
     */
    public ConfigException(String msg, Exception ex) {
        super(msg, ex);
    }
}
