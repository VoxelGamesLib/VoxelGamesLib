package me.minidigger.voxelgameslib.api.exception;

import javax.annotation.Nonnull;

/**
 * A global exception, should not be thrown.
 */
public class VoxelGameLibException extends RuntimeException {

    /**
     * @param message the message that explains the issue
     */
    VoxelGameLibException(@Nonnull String message) {
        super(message);
    }

    /**
     * @param message the message that explains the issue
     * @param e       the root exception that caused this exception
     */
    VoxelGameLibException(@Nonnull String message, Exception e) {
        super(message, e);
    }
}
