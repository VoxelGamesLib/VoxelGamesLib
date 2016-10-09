package me.MiniDigger.VoxelGamesLib.api.i18n;

import javax.inject.Inject;

/**
 * Created by Martin on 09.10.2016.
 */
public class Lang {

    @Inject
    private static LangHandler handler;


    public static String t(String key) {
        return t(key, handler.getDefaultLocale());
    }

    public static String t(String key, String... args) {
        return t(key, handler.getDefaultLocale(), args);
    }

    public static String t(String key, Locale loc) {
        return t(key, loc, new String[0]);
    }

    public static String t(String key, Locale loc, String[] args) {
        LangStorage storage = handler.getStorage(loc);
        String string = storage.get(key);

        //TODO apply args

        return string;
    }
}
