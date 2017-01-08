package me.minidigger.voxelgameslib.api.log;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandExecutor;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.command.CommandUtil;
import me.minidigger.voxelgameslib.api.command.CompleterInfo;
import me.minidigger.voxelgameslib.api.handler.Handler;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.api.server.Server;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.ChatColor;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;

import lombok.extern.java.Log;

/**
 * Handles all logging
 */
@Log
@Singleton
@CommandExecutor
public class LoggerHandler implements Handler {
    
    @Inject
    private Server server;
    
    private LogHandler handler;
    private Logger logger;
    private Level level = Level.INFO;
    
    @Override
    public void start() {
        logger = Logger.getLogger("me.minidigger.voxelgameslib");
        handler = new LogHandler() {
            @Override
            public void publish(LogRecord record) {
                // notify staff on warning or error
                if (record.getLevel().equals(Level.WARNING) || record.getLevel().equals(Level.SEVERE)) {
                    server.broadcastMessage(Role.ADMIN, new ComponentBuilder("[Log] ").color(ChatColor.RED)
                            .append("<" + record.getLevel().getName() + "> ").color(ChatColor.DARK_RED)
                            .append(record.getMessage()).create());
                }
                record.setMessage("[VoxelGamesLib] " + record.getMessage());
            }
        };
        logger.addHandler(handler);
    
        log.info("Logging activated.");
    }
    
    @SuppressWarnings("JavaDoc")
    @CommandInfo(name = "log", perm = "command.log", role = Role.ADMIN)
    public void logcommand(CommandArguments arguments) {
        if (arguments.getNumArgs() == 0) {
            Lang.msg(arguments.getSender(), LangKey.LOG_CURRENT_LEVEL, logger.getLevel().getName());
            return;
        }
        
        try {
            setLevel(Level.parse(arguments.getArg(0)));
            Lang.msg(arguments.getSender(), LangKey.LOG_LEVEL_SET, arguments.getArg(0));
        } catch (IllegalArgumentException ex) {
            Lang.msg(arguments.getSender(), LangKey.LOG_UNKNOWN_LEVEL, arguments.getArg(0));
        }
    }
    
    @SuppressWarnings("JavaDoc")
    @CompleterInfo(name = "log")
    public List<String> logcompleter(CommandArguments arguments) {
        return CommandUtil.filterTabCompletions(arguments.getArg(0), Level.ALL.getName(), Level.CONFIG.getName(),
                Level.FINE.getName(), Level.FINER.getName(), Level.FINEST.getName(), Level.INFO.getName()
                , Level.OFF.getName(), Level.WARNING.getName(), Level.SEVERE.getName());
    }
    
    /**
     * Changes the log level of the logger for the framework
     *
     * @param level the new level
     */
    public void setLevel(Level level) {
        this.level = level;
        Logger.getLogger("me.minidigger").setLevel(level);
//        for (Enumeration<String> e = LogManager.getLogManager().getLoggerNames(); e.hasMoreElements(); ) {
//            Logger logger = Logger.getLogger(e.nextElement());
//            for (java.util.logging.Handler handler : logger.getHandlers()) {
//                handler.setLevel(level);
//            }
//            logger.setLevel(level);
//        }
        log.info("Level changed to " + level.getName());
    }
    
    
    @Override
    public void stop() {
        Logger.getLogger("").removeHandler(handler);
    }
    
    public Level getLevel() {
        return level;
    }
    
    // used to make the code look more clean
    private abstract class LogHandler extends java.util.logging.Handler {
        
        @Override
        public void flush() {
        
        }
        
        @Override
        public void close() throws SecurityException {
        
        }
    }
}
