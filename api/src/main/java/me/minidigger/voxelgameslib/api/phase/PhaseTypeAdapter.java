package me.minidigger.voxelgameslib.api.phase;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.inject.Injector;

import java.lang.reflect.Type;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;

/**
 * TypeAdapter for the Phase class, redirects gson to the right Phase implementation
 */
@Log
@Singleton
public class PhaseTypeAdapter implements JsonDeserializer<Phase>, JsonSerializer<Phase> {

    @Inject
    private Injector injector;

    @Override
    public Phase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            Class clazz = Class.forName(jsonObject.get("className").getAsString());
            Phase phase = context.deserialize(json, clazz);
            injector.injectMembers(phase);
            return phase;
        } catch (Exception e) {
            log.log(Level.WARNING, "Could not deserialize phase:\n" + json.toString(), e);
        }
        return null;
    }

    @Override
    public JsonElement serialize(Phase src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
