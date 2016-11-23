package me.minidigger.voxelgameslib.api.fun;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.extern.java.Log;
import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandExecutor;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.TextComponent;

/**
 * Small class to do fun stuff
 */
@Log
@Singleton
@CommandExecutor
public class FunCommands {

    @Inject
    private Gson gson;
    @Inject
    private Server server;

    private TacoStuff tacoStuff;

    /**
     * Loads our precious data
     */
    public void load() {
        try {
            tacoStuff = gson.fromJson(new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/taco.json"))), TacoStuff.class);
            log.info("Tacos loaded!");
        } catch (Exception ex) {
            log.log(Level.WARNING, "Could not load tacos!", ex);
        }
    }

    @CommandInfo(name = "taco", perm = "command.taco", role = Role.PREMIUM, min = 1, usage = "Sends a user a nice taco")
    public void taco(CommandArguments arguments) {
        if (tacoStuff == null) {
            arguments.getSender().sendMessage(new TextComponent("No tacos loaded :("));
            return;
        }

        String message = taco(arguments.getArg(0));
        server.broadcastMessage(new TextComponent("The server " + message));
    }

    private String taco(String user) {
        String message = tacoStuff.templates[0];
        message = message.replace("{user}", user);
        message = message.replace("{quality}", getRandom(tacoStuff.parts.quality));
        message = message.replace("{type}", getRandom(tacoStuff.parts.type));
        message = message.replace("{meat}", getRandom(tacoStuff.parts.meat));
        message = message.replaceFirst("\\{topping}", getRandom(tacoStuff.parts.topping));
        message = message.replaceFirst("\\{topping}", getRandom(tacoStuff.parts.topping));
        message = message.replaceFirst("\\{topping}", getRandom(tacoStuff.parts.topping));
        return message;
    }

    private String getRandom(String[] arr) {
        return arr[ThreadLocalRandom.current().nextInt(arr.length)];
    }

    class TacoStuff {
        String[] templates;
        Parts parts;

        class Parts {
            String[] type;
            String[] quality;
            String[] meat;
            String[] topping;
        }
    }
}
