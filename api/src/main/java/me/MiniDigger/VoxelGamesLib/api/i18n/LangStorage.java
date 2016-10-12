package me.MiniDigger.VoxelGamesLib.api.i18n;

import com.google.inject.name.Named;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

import me.MiniDigger.VoxelGamesLib.api.exception.LangException;

/**
 * Created by Martin on 09.10.2016.
 */
public class LangStorage {

    @Inject
    @Named("LangFolder")
    private File langFolder;

    @Inject
    private LangHandler handler;

    private Locale locale;
    private Properties messages = new Properties();

    public LangStorage(Locale locale) {
        this.locale = locale;
    }

    public Locale getLang() {
        return locale;
    }

    /**
     * Tries to load the messages from the langFolder
     *
     * @throws LangException if something goes wrong while loading
     */
    public void load() {
        try {
            messages.load(new FileInputStream(new File(langFolder, locale.getTag())));
        } catch (IOException e) {
            throw new LangException("Could not find lang file for locale" + locale, e);
        }
    }

    public String get(LangKey key) {
        return messages.getProperty(key.name(), handler.getDefaultStorage().get(key));
    }
}
