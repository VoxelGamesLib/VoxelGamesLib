package me.MiniDigger.VoxelGamesLib.api.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

import java.io.File;

/**
 * Created by Martin on 15.10.2016.
 */
public class ZipUtil {

    public static ZipFile createZip(File file) throws ZipException {
        ZipFile zip = new ZipFile(new File(file.getParent(), file.getName() + ".zip"));
        for (File f : file.listFiles()) {
            if (file.isDirectory()) {
                addFile(zip, f);
            } else {
                zip.addFile(f, new ZipParameters());
            }
        }
        return zip;
    }

    public static void addFile(ZipFile zip, File file) throws ZipException {
        for (File f : file.listFiles()) {
            if (file.isDirectory()) {
                addFile(zip, f);
            } else {
                zip.addFile(f, new ZipParameters());
            }
        }
    }
}
