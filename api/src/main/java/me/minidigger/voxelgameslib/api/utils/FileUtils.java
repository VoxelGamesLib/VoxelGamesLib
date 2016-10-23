package me.minidigger.voxelgameslib.api.utils;

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
            File[] files = f.listFiles();
            if (files != null) {
                for (File c : files) {
                    delete(c);
                }
            }
        }
        if (!f.delete()) {
            f.deleteOnExit();
        }
    }
}
