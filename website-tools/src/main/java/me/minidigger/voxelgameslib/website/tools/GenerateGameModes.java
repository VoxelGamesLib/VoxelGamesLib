package me.minidigger.voxelgameslib.website.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.github.slugify.Slugify;

import org.reflections.Reflections;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.game.GameInfo;
import me.minidigger.voxelgameslib.website.model.GameMode;
import me.minidigger.voxelgameslib.website.model.Icon;

/**
 * Created by Martin on 17.01.2017.
 */
@Singleton
public class GenerateGameModes {

    private static Slugify slugify = new Slugify();
    private static List<GameMode> gameModes = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new Reflections().getTypesAnnotatedWith(GameInfo.class).forEach(GenerateGameModes::doStuffWith);

        gameModes.sort(Comparator.comparing(GameMode::getSlug));

        FileWriter fileWriter = new FileWriter(new File("Z:\\Dev\\spigot-intellij\\VoxelGamesLib\\website\\src\\main\\resources\\data\\gamemodes.json"));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(gameModes, fileWriter);
        fileWriter.close();
    }

    private static void doStuffWith(Class<?> clazz) {
        GameInfo gameInfo = clazz.getAnnotation(GameInfo.class);

        List<Icon> icons = new ArrayList<>();
        icons.add(new Icon("#", "fa-github"));//TODO github url
        icons.add(new Icon("/gamemodes/" + slugify.slugify(gameInfo.name()), "fa-book"));
        icons.add(new Icon("#", "fa-bug"));//TODO issues url

        //TODO image url
        GameMode gameMode = new GameMode(icons, "img/content/Home-2/team-1.jpg", gameInfo.name(),
                gameInfo.version() + " by " + gameInfo.author(), gameInfo.description(), slugify.slugify(gameInfo.name()));
        System.out.println(gameMode);
        gameModes.add(gameMode);
    }
}
