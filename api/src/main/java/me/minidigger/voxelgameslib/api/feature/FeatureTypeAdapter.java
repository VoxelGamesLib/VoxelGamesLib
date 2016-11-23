package me.minidigger.voxelgameslib.api.feature;

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
 * TypeAdapter for the Feature class, redirects gson to the right Feature implementation
 */
@Log
@Singleton
public class FeatureTypeAdapter implements JsonDeserializer<Feature>, JsonSerializer<Feature> {

    @Inject
    private Injector injector;

    @Override
    public Feature deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            Class clazz = Class.forName(jsonObject.get("name").getAsString());
            Feature feature = context.deserialize(json, clazz);
            injector.injectMembers(feature);
            return feature;
        } catch (Exception e) {
            log.log(Level.WARNING, "Could not deserialize feature:\n" + json.toString(), e);
        }
        return null;
    }

    @Override
    public JsonElement serialize(Feature src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
