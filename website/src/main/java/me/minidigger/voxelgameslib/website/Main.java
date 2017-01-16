package me.minidigger.voxelgameslib.website;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.minidigger.voxelgameslib.website.model.GameMode;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.internalServerError;
import static spark.Spark.notFound;
import static spark.Spark.staticFiles;

/**
 * Created by Martin on 16.01.2017.
 */
public class Main {

    private static List<GameMode> gameModes;
    private static boolean themeChooser = false;

    public static void main(String[] args) {
        // to serve images, styles etc
        staticFiles.location("/public");
        staticFiles.expireTime(TimeUnit.MINUTES.toSeconds(10));

        loadData();

        get("/index", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("themeChooser", themeChooser);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/gamemodes", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("themeChooser", themeChooser);
            model.put("gamemodes", gameModes);
            return new ModelAndView(model, "gamemodes.hbs");
        }, new HandlebarsTemplateEngine());

        notFound((req, res) -> {
            res.status(404);
            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
            Map<String, Object> model = new HashMap<>();
            model.put("error-title", "Not Found");
            model.put("error-description", "The content you were looking for was not found. Oh no!");
            String html = engine.render(new ModelAndView(model, "error.hbs"));
            res.body(html);
            return null;
        });

        internalServerError((req, res) -> {
            res.status(500);
            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
            Map<String, Object> model = new HashMap<>();
            model.put("error-title", "Internal Server Error");
            model.put("error-description", "There was a Internal Server Error. Oh no!");
            String html = engine.render(new ModelAndView(model, "error.hbs"));
            res.body(html);
            return null;
        });

        exception(Exception.class, (ex, req, res) -> {
            res.status(404);
            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
            Map<String, Object> model = new HashMap<>();
            model.put("error-title", "Error: " + ex.getClass().getSimpleName());
            model.put("error-description", "There was a Error. Oh no!<br>" + ex.getClass() + ": " + ex.getMessage());
            String html = engine.render(new ModelAndView(model, "error.hbs"));
            res.body(html);
        });
    }

    private static void loadData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        //noinspection unchecked
        gameModes = gson.fromJson(new InputStreamReader(Main.class.getResourceAsStream("/data/gamemodes.json")), List.class);
    }
}
