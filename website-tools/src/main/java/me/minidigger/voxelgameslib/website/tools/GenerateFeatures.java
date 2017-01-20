package me.minidigger.voxelgameslib.website.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import com.github.slugify.Slugify;

import org.reflections.Reflections;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import me.minidigger.voxelgameslib.api.feature.FeatureInfo;
import me.minidigger.voxelgameslib.website.model.Feature;

/**
 * Created by Martin on 19.01.2017.
 */
public class GenerateFeatures {
    private static Slugify slugify = new Slugify();
    private static List<Feature> features = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new Reflections().getTypesAnnotatedWith(FeatureInfo.class).forEach(GenerateFeatures::doStuffWith);

        features.sort(Comparator.comparing(Feature::getSlug));

        File outFile = new File(new File(System.getProperty("user.dir")).getParent(), "website/src/main/resources/data/features.json");
        FileWriter fileWriter = new FileWriter(outFile);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(features, fileWriter);
        fileWriter.close();
    }

    private static void doStuffWith(Class<?> clazz) {
        FeatureInfo featureInfo = clazz.getAnnotation(FeatureInfo.class);

        List<String> dependencies = new ArrayList<>();
        try {
            Class[] dep = (Class[]) clazz.getMethod("getDependencies").invoke(clazz.newInstance());
            for (Class c : dep) {
                dependencies.add(c.getName());
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
            System.out.println("could not collect dependencies for clazz " + clazz.getSimpleName());
        }

        List<String> params = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Expose.class)) {
                params.add(field.getName() + " (" + field.getType().getName() + ")");
            }
        }

        Feature feature = new Feature(featureInfo.name(), clazz.getName(), featureInfo.author(), featureInfo.version(),
                featureInfo.description(), dependencies, params, slugify.slugify(clazz.getName()));
        System.out.println(feature);
        features.add(feature);
    }
}