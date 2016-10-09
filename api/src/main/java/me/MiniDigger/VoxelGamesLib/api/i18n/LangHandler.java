package me.MiniDigger.VoxelGamesLib.api.i18n;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import me.MiniDigger.VoxelGamesLib.api.handler.Handler;

/**
 * Created by Martin on 09.10.2016.
 */
@Singleton
public class LangHandler implements Handler {

    private Map<Locale, LangStorage> storages = new HashMap<>();
    private Locale defaultLocale = Locale.ENGLISH; //TODO move defualtLocale to config
    private LangStorage defaultStorage;

    @Override
    public void start() {
        defaultStorage = new LangStorage(defaultLocale);
        defaultStorage.load();

        registerLocale(Locale.ENGLISH);
        registerLocale(Locale.GERMAN);
    }

    @Override
    public void stop() {

    }

    public void registerLocale(Locale loc) {
        LangStorage s = new LangStorage(loc);
        s.load();
        storages.put(loc, s);
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public LangStorage getStorage(Locale loc) {
        return storages.getOrDefault(loc, defaultStorage);
    }

    public LangStorage getDefaultStorage() {
        return defaultStorage;
    }
}
