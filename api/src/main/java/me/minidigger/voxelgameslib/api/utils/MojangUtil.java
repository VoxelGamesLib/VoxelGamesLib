package me.minidigger.voxelgameslib.api.utils;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

import me.minidigger.voxelgameslib.api.exception.VoxelGameLibException;

/**
 * Small util to access the mojang api
 */
public class MojangUtil {

    private static final String NAME_HISTORY_URL = "https://api.mojang.com/user/profiles/%1/names";

    private static final Gson gson = new Gson();

    /**
     * Tries to fetch the current display name for the user
     *
     * @param id the id of the user to check
     * @return the current display name of that user
     * @throws IOException           if something goes wrong
     * @throws VoxelGameLibException if the user has no display name
     */
    public static String getDisplayName(UUID id) throws IOException, VoxelGameLibException, ParseException {
        URL url = new URL(NAME_HISTORY_URL.replace("%1", id.toString().replace("-", "")));
        System.out.println(url.toString());
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(url.openStream())));
        if (scanner.hasNext()) {
            String json = scanner.nextLine();
            System.out.println(json);
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(json);
            if (json.length() > 0) {
                return (String) ((JSONObject) jsonArray.get(0)).get("name");
            }
        }

        throw new VoxelGameLibException("User has no name! " + id);
    }
}
