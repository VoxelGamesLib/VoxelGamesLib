package me.minidigger.voxelgameslib.api.log;

import lombok.Getter;
import me.minidigger.voxelgameslib.annotations.log.LoggerInfo;

/**
 * Created by Martin on 10.12.2016.
 */
@Getter
@LoggerInfo(test = "test")
public class LoggerTest {

    private String test;

    public LoggerTest() {
        System.out.println("logger test");
//        System.out.println("logger = " + log + getTest());
    }
}
