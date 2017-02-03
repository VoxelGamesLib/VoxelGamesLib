package me.minidigger.voxelgameslib.website.tools;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by Martin on 04.02.2017.
 */
public class GenerateLangFile {

    private static final String langFile = "testserver/plugins/VoxelGamesLibBukkit/lang/en.properties";
    private static final String sourceFile = "api/src/main/java/me/minidigger/voxelgameslib/api/lang/LangKey.java";

    public static void main(String[] args) throws IOException {
        File langFile = new File(new File(System.getProperty("user.dir")).getParent(), GenerateLangFile.langFile);
        File sourceFile = new File(new File(System.getProperty("user.dir")).getParent(), GenerateLangFile.sourceFile);
        File temp = new File(new File(System.getProperty("user.dir")).getParent(), GenerateLangFile.sourceFile + ".temp");

        Properties properties = new Properties();
        properties.load(new FileInputStream(langFile));

        PrintWriter writer = new PrintWriter(new FileWriter(temp));
        Scanner scanner = new Scanner(new FileInputStream(sourceFile));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            for (Object key : properties.keySet()) {
                if (key instanceof String) {
                    if (line.contains((String) key)) {
                        String[] split = line.split("\"");
                        split[1] = (String) properties.get(key);
                        line = Arrays.stream(split).collect(Collectors.joining("\""));
                    }
                }
            }

            writer.println(line);
        }

        writer.close();
        scanner.close();

        Files.copy(temp, sourceFile);
        temp.delete();
    }
}
