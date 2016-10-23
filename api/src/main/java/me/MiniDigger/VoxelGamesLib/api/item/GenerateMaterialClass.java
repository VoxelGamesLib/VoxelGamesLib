package me.MiniDigger.VoxelGamesLib.api.item;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Small tool to generate the material class. uses data by the awesome
 * primsmarinejs guys. go check them out here https://github.com/PrismarineJS/minecraft-data/
 */
class GenerateMaterialClass {

    private static final String blocksJsonUrl = "https://raw.githubusercontent.com/PrismarineJS/minecraft-data/master/data/pc/1.10/blocks.json";
    private static final String itemsJsonUrl = "https://raw.githubusercontent.com/PrismarineJS/minecraft-data/master/data/pc/1.10/items.json";
    private static final File file = new File("Z:\\Dev\\spigot-intellij\\VoxelGamesLib\\api\\src\\main\\java\\me\\MiniDigger\\VoxelGamesLib\\api\\item\\Material.java");

    private static final String[] duplicatedNames = {"BED", "WHEAT", "WOODEN_DOOR", "IRON_DOOR", "REEDS", "CAKE", "NETHER_WART", "BREWING_STAND", "CAULDRON",
            "FLOWER_POT", "SKULL", "SPRUCE_DOOR", "BIRCH_DOOR", "JUNGLE_DOOR", "ACACIA_DOOR", "DARK_OAK_DOOR"};

    public static void main(String[] args) throws IOException, ParseException {
        List<Block> blocks = new ArrayList<>();

        // load blocks
        JSONArray arr = (JSONArray) new JSONParser().parse(new InputStreamReader(new URL(blocksJsonUrl).openStream()));
        for (Object t : arr) {
            JSONObject obj = (JSONObject) t;
            String name = ((String) obj.get("name")).toUpperCase();
            int id = ((Long) obj.get("id")).intValue();
            blocks.add(new Block(name, id));
        }

        // load items
        JSONArray arr2 = (JSONArray) new JSONParser().parse(new InputStreamReader(new URL(itemsJsonUrl).openStream()));
        for (Object t : arr2) {
            JSONObject obj = (JSONObject) t;
            String name = ((String) obj.get("name")).toUpperCase();
            int id = ((Long) obj.get("id")).intValue();
            String newName = name;
            // handle duplicate names -> add ITEM
            if (Stream.of(duplicatedNames).anyMatch(d -> d.equalsIgnoreCase(name))) {
                newName += "_ITEM";
            }

            blocks.add(new Block(newName, id));
        }

        StringBuilder sb = new StringBuilder();
        for (Block block : blocks) {
            sb.append("\t").append(block.name).append("(").append(block.id).append(")").append(",").append("\n");
        }
        String write = sb.toString().substring(0, sb.length() - 2) + ";";

        Scanner s = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while (s.hasNextLine()) {
            lines.add(s.nextLine());
        }
        s.close();

        PrintWriter w = new PrintWriter(file);
        for (String line : lines) {
            w.println(line);
            if (line.equalsIgnoreCase("public enum Material {")) {
                w.println("\n" + write);
                w.println("\n    private int id;\n" +
                        "\n" +
                        "    Material(int id) {\n" +
                        "        this.id = id;\n" +
                        "    }\n" +
                        "\n" +
                        "    public int getId() {\n" +
                        "        return id;\n" +
                        "    }\n" +
                        "\n" +
                        "    public static Material fromId(int id) {\n" +
                        "        for (Material mat : values()) {\n" +
                        "            if (mat.getId() == id) {\n" +
                        "                return mat;\n" +
                        "            }\n" +
                        "        }\n" +
                        "        throw new IllegalArgumentException(\"No enum constant for id \" + id);\n" +
                        "    }");
                w.println("}");
                break;
            }
        }
        w.close();
    }

    private static class Block {

        final String name;
        final int id;

        Block(String name, int id) {
            this.name = name;
            this.id = id;
        }
    }
}
