package me.minidigger.voxelgameslib.api.lang;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.minidigger.voxelgameslib.api.exception.LangException;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.TranslatableComponent;

/**
 * Gives quick access to the lang storage and translation and stuff
 */
public class Lang {
    
    private static LangHandler handler;
    
    static void setLangHandler(@Nonnull LangHandler handler) {
        Lang.handler = handler;
    }
    
    /**
     * Creates an ComponentBuilder based on that LangKey
     *
     * @param key the lang key that should be translated
     * @return the created component builder
     */
    @Nonnull
    public static ComponentBuilder trans(@Nonnull LangKey key) {
        return trans(key, handler.getDefaultLocale());
    }
    
    /**
     * Creates an ComponentBuilder based on that LangKey<br>
     * The specified arguments are used to fill out placeholders
     *
     * @param key  the lang key that should be translated
     * @param args the arguments that should be replaying placeholders
     * @return the created component builder
     */
    @Nonnull
    public static ComponentBuilder trans(@Nonnull LangKey key, @Nullable Object... args) {
        return trans(key, handler.getDefaultLocale(), args);
    }
    
    /**
     * Creates an ComponentBuilder based on that LangKey<br>
     * Allows to specify a locale that should be used to translate
     *
     * @param key the lang key that should be translated
     * @param loc the locale that should be used to translate the key
     * @return the created component builder
     */
    @Nonnull
    public static ComponentBuilder trans(@Nonnull LangKey key, @Nonnull Locale loc) {
        return trans(key, loc, new Object[0]);
    }
    
    /**
     * Creates an ComponentBuilder based on that LangKey<br>
     * Allows to specify a locale that should be used to translate<br>
     * The specified arguments are used to fill out placeholders
     *
     * @param key  the lang key that should be translated
     * @param loc  the locale that should be used to translate the key
     * @param args the arguments that should be replacing placeholders
     * @return the created component builder
     */
    @Nonnull
    public static ComponentBuilder trans(@Nonnull LangKey key, @Nonnull Locale loc, @Nullable Object... args) {
        if (args == null) {
            args = new Object[0];
        }
    
        if (args.length != key.getArgs()) {
            throw new LangException("Wrong arguments for LangKey " + key.name() + ": entered " + args.length + ", expected " + key.getArgs());
        }
    
        LangStorage storage = handler.getStorage(loc);
        String string = storage.get(key);
        return new ComponentBuilder(new TranslatableComponent(string, args).toPlainText());
    }
    
    /**
     * Sends the user a message that contains the translated version (using his local) of the
     * specified key
     *
     * @param user the user that should receive the message
     * @param key  the lang key that should be translated
     */
    public static void msg(@Nonnull User user, @Nonnull LangKey key) {
        user.sendMessage(trans(key).create());
    }
    
    /**
     * Sends the user a message that contains the translated version (using his local) of the
     * specified key<br>
     * The specified arguments are used to fill out placeholders
     *
     * @param user the user that should receive the message
     * @param key  the lang key that should be translated
     * @param args the args that should be replacing placeholders
     */
    public static void msg(@Nonnull User user, @Nonnull LangKey key, @Nullable Object... args) {
        user.sendMessage(trans(key, args).create());
    }
    
    /**
     * Translates the specified lang key into a string
     *
     * @param key the key to translate
     * @return the translated string
     */
    @Nonnull
    public static String string(@Nonnull LangKey key) {
        return string(key, handler.getDefaultLocale());
    }
    
    /**
     * Translates the specified lang key into a string<br>
     * The specified arguments are used to fill out placeholders
     *
     * @param key  the key to translate
     * @param args the args that should be replacing placeholders
     * @return the translated string
     */
    @Nonnull
    public static String string(@Nonnull LangKey key, @Nonnull Object... args) {
        return string(key, handler.getDefaultLocale(), args);
    }
    
    /**
     * Translates the specified lang key into a string<br>
     * Allows to specify a locale that should be used to translate
     *
     * @param key the key to translate
     * @param loc the locale that should be used to translate the key
     * @return the translated string
     */
    @Nonnull
    public static String string(@Nonnull LangKey key, @Nonnull Locale loc) {
        return string(key, loc, new Object[0]);
    }
    
    /**
     * Translates the specified lang key into a string<br>
     * Allows to specify a locale that should be used to translate<br>
     * The specified arguments are used to fill out placeholders
     *
     * @param key  the key to translate
     * @param loc  the locale that should be used to translate the key
     * @param args the args that should be replacing placeholders
     * @return the translated string
     */
    @Nonnull
    public static String string(@Nonnull LangKey key, @Nonnull Locale loc, @Nonnull Object... args) {
        if (args.length != key.getArgs()) {
            throw new LangException("Wrong arguments for LangKey " + key.name() + ": entered " + args.length + ", expected " + key.getArgs());
        }
    
        LangStorage storage = handler.getStorage(loc);
        String string = storage.get(key);
        return String.format(string, args);
    }
}
