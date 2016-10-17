package me.MiniDigger.VoxelGamesLib.api.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Martin on 15.10.2016.
 */
public class ZipUtil {

    public static ZipFile createZip(File file) throws ZipException {
        ZipFile zip = new ZipFile(new File(file.getParent(), file.getName() + ".zip"));
        ArrayList<File> fileList = new ArrayList<>();

        File[] files = file.listFiles();
        if (files == null) {
            return zip;
        }

        Arrays.stream(files).forEach(fileList::add);

        zip.createZipFile(fileList, new ZipParameters());

        return zip;
    }
}
