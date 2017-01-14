package me.minidigger.voxelgameslib.bukkit.log;

import java.io.PrintStream;

import me.minidigger.voxelgameslib.api.log.LoggerHandler;

/**
 * Created by Martin on 08.01.2017.
 */
public class BukkitLogHandler extends LoggerHandler {
    
    @Override
    public void start() {
        super.start();
        
        // fuck everyone
        java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
        global.setUseParentHandlers(false);
        for (java.util.logging.Handler handler : global.getHandlers()) {
            global.removeHandler(handler);
        }
        
        // A BIG FUCK YOU AT Travis Watkins who introduced this nice forwardloghandler into craftbukkit
        global.addHandler(new TheFuckYouForwardHandler());
        System.setOut(new PrintStream(new TheFuckYouLoggerOutputStream(), true));
        System.setErr(new PrintStream(new TheFuckYouLoggerOutputStream(), true));
    }
}
