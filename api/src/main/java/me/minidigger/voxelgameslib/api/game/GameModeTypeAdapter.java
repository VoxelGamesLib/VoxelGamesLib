package me.minidigger.voxelgameslib.api.game;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.logging.Level;

import lombok.extern.java.Log;

/**
 * Type adapter for the gamemode class
 */
@Log
public class GameModeTypeAdapter extends TypeAdapter<GameMode> {
    @Override
    public void write(JsonWriter out, GameMode value) throws IOException {
        out.beginObject();
        out.name("name").value(value.getName());
        out.name("className").value(value.getGameClass().getName());
        out.endObject();
    }

    @Override
    public GameMode read(JsonReader in) throws IOException {
        in.beginObject();
        in.nextName();
        String name = in.nextString();
        in.nextName();
        Class clazz;
        try {
            clazz = Class.forName(in.nextString());
            in.endObject();
            //noinspection unchecked
            return new GameMode(name, clazz);
        } catch (ClassNotFoundException e) {
            log.log(Level.WARNING, "Could not deserialized gamemode " + name, e);
        }
        in.endObject();
        return null;
    }
}
