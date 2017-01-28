package me.minidigger.voxelgameslib.api.game;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.inject.Injector;

import java.lang.reflect.Type;
import java.util.logging.Level;
import javax.inject.Inject;

import lombok.extern.java.Log;

/**
 * Type adapter for the gamemode class
 */
@Log
public class GameModeTypeAdapter implements JsonDeserializer<GameMode> {

    @Inject
    private Injector injector;

    @Override
    public GameMode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject jsonObject = json.getAsJsonObject();

            String name = jsonObject.get("name").getAsString();
            String className = jsonObject.get("className").getAsString();

            Class clazz = Class.forName(className);
            GameMode gameMode = new GameMode(name, clazz);
            injector.injectMembers(gameMode);
            return gameMode;
        } catch (Exception e) {
            log.log(Level.WARNING, "Could not deserialize gamemode:\n" + json.toString(), e);
        }
        return null;
    }
}
