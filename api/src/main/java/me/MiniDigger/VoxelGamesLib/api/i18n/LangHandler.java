package me.MiniDigger.VoxelGamesLib.api.i18n;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.MiniDigger.VoxelGamesLib.api.VoxelGamesLib;
import me.MiniDigger.VoxelGamesLib.api.handler.Handler;

/**
 * Created by Martin on 09.10.2016.
 */
@Singleton
public class LangHandler implements Handler {

    private Map<Locale, LangStorage> storages = new HashMap<>();
    private Locale defaultLocale = Locale.ENGLISH; //TODO move defaultLocale to config
    private LangStorage defaultStorage;

    @Inject
    private VoxelGamesLib voxelGameLib;

    @Override
    public void start() {
        Lang.setLangHandler(this);

        defaultStorage = voxelGameLib.getInjector().getInstance(LangStorage.class);
        defaultStorage.setLocale(defaultLocale);
        defaultStorage.load();

        registerLocale(Locale.ENGLISH);
        registerLocale(Locale.GERMAN);
    }

    @Override
    public void stop() {

    }

    public void registerLocale(Locale loc) {
        LangStorage s = voxelGameLib.getInjector().getInstance(LangStorage.class);
        s.setLocale(loc);
        s.setParentStorage(defaultStorage);
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

    public Set<Locale> getInstalledLocales() {
        return storages.keySet();
    }
}
