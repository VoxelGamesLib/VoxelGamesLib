package me.MiniDigger.VoxelGamesLib.api.i18n;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TranslatableComponent;

import me.MiniDigger.VoxelGamesLib.api.exception.LangException;
import me.MiniDigger.VoxelGamesLib.api.user.User;

/**
 * Created by Martin on 09.10.2016.
 */
public class Lang {

    private static LangHandler handler;

    public static void setLangHandler(LangHandler handler) {
        Lang.handler = handler;
    }

    public static ComponentBuilder t(LangKey key) {
        return t(key, handler.getDefaultLocale());
    }

    public static ComponentBuilder t(LangKey key, Object... args) {
        return t(key, handler.getDefaultLocale(), args);
    }

    public static ComponentBuilder t(LangKey key, Locale loc) {
        return t(key, loc, new Object[0]);
    }

    public static ComponentBuilder t(LangKey key, Locale loc, Object... args) {
        if (args.length != key.getArgs()) {
            throw new LangException("Wrong arguments for LangKey " + key.name() + ": entered " + args.length + ", expected " + key.getArgs());
        }

        LangStorage storage = handler.getStorage(loc);
        String string = storage.get(key);
        //TODO check if this actually works
        return new ComponentBuilder(new TranslatableComponent(string, args).toPlainText());
    }

    public static void m(User user, LangKey key) {
        user.sendMessage(t(key).create());
    }

    public static void m(User user, LangKey key, Object... args) {
        user.sendMessage(t(key, args).create());
    }
}
