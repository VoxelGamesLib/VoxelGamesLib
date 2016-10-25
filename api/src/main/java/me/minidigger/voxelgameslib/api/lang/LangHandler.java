package me.minidigger.voxelgameslib.api.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.VoxelGamesLib;
import me.minidigger.voxelgameslib.api.handler.Handler;

import lombok.extern.java.Log;

/**
 * Created by Martin on 09.10.2016.
 */
@Log
@Singleton
public class LangHandler implements Handler {
    
    private final Map<Locale, LangStorage> storages = new HashMap<>();
    private final Locale defaultLocale = Locale.ENGLISH; //TODO move defaultLocale to config
    private LangStorage defaultStorage;
    
    @Inject
    private VoxelGamesLib voxelGameLib;
    
    @Override
    public void start() {
        Lang.setLangHandler(this);
        
        defaultStorage = voxelGameLib.getInjector().getInstance(LangStorage.class);
        defaultStorage.setLocale(defaultLocale);
        defaultStorage.load();
        
        int counter = defaultStorage.processNewValues();
        if (counter > 0) {
            log.info("Migrated lang file " + defaultStorage.getLangFile().getAbsolutePath() + ": Added " + counter + " new keys!");
        }
        
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
        
        int counter = s.processNewValues();
        if (counter > 0) {
            log.info("Migrated lang file " + s.getLangFile().getAbsolutePath() + ": Added " + counter + " new keys!");
        }
        
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
