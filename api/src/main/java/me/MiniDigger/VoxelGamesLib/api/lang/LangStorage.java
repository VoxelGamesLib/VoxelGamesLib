package me.MiniDigger.VoxelGamesLib.api.lang;

import com.google.inject.name.Named;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

import lombok.extern.java.Log;
import me.MiniDigger.VoxelGamesLib.api.exception.LangException;

/**
 * Created by Martin on 09.10.2016.
 */
@Log
public class LangStorage {

    @Inject
    @Named("LangFolder")
    private File langFolder;

    @Inject
    private LangHandler handler;

    private File langFile;

    private Locale locale;
    private final Properties messages = new Properties();
    private LangStorage parentStorage;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        langFile = new File(langFolder, locale.getTag() + ".properties");
    }

    public void setParentStorage(LangStorage parentStorage) {
        this.parentStorage = parentStorage;
    }

    public void saveDefaultValue() {
        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }

        for (LangKey key : LangKey.values()) {
            messages.setProperty(key.name(), key.getDefaultValue());
        }
        try {
            messages.store(new FileWriter(langFile), "This is a command. I don't really know what this is supposed to do, but lets see!\nLets throw in\nsome newlines!");
        } catch (IOException e) {
            throw new LangException("Error while saving default lang values to " + langFile.getAbsolutePath(), e);
        }
    }

    /**
     * Tries to load the messages from the langFolder
     *
     * @throws LangException if something goes wrong while loading
     */
    public void load() {
        if (!langFile.exists()) {
            log.info("Lang file " + langFile.getAbsolutePath() + " does not exist, saving default values");
            saveDefaultValue();
        }
        try {
            messages.load(new FileInputStream(langFile));
        } catch (IOException e) {
            throw new LangException("Could not find lang file for locale" + locale, e);
        }
    }

    public String get(LangKey key) {
        String message = messages.getProperty(key.name());
        if (message == null) {
            if (parentStorage != null) {
                message = parentStorage.get(key);
            }
        }

        if (message == null) {
            throw new LangException("Could not find value for lang key " + key.name()); //TODO do we want to return the default value here?
        }

        return message;
    }
}
