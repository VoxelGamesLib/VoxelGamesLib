package me.MiniDigger.VoxelGamesLib.api.utils;

import java.io.File;

/**
 * Collection of file related utils
 */
public class FileUtils {

    /**
     * Deletes a file (or folder)
     *
     * @param f the file to delete
     */
    public static void delete(File f) {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                delete(c);
            }
        }
        if (!f.delete()) {
            f.deleteOnExit();
        }
    }
}
