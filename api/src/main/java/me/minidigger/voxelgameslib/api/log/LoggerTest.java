package me.minidigger.voxelgameslib.api.log;

import me.minidigger.voxelgameslib.annotations.log.LoggerInfo;

/**
 * Created by Martin on 10.12.2016.
 */
public class LoggerTest {

    @LoggerInfo(test = "test")
    private Logger logger;

    public LoggerTest() {
        System.out.println("logger test");
        System.out.println("logger = " + logger);
    }
}
