package me.minidigger.voxelgameslib.bukkit.log;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

/**
 * Created by Martin on 08.01.2017.
 */
public class TheFuckYouForwardHandler extends ConsoleHandler {
    private static final SimpleDateFormat hourFormat = new SimpleDateFormat("kk:mm:ss:SSS");
    private final PrintStream sout = new PrintStream(new FileOutputStream(FileDescriptor.out));
    
    public void publish(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(hourFormat.format(new Date(record.getMillis()))).append(" ").append(record.getLevel().getName()).append("]: ");
        sb.append(record.getMessage());
        sout.println(sb.toString());
    }
    
    public void flush() {
    }
    
    public void close() throws SecurityException {
    }
}
