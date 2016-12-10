package me.minidigger.voxelgameslib.api.log;

import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.handler.Handler;

/**
 * Created by Martin on 10.12.2016.
 */
@Singleton
public class LoggerHandler implements Handler {

    @Override
    public void start() {
        new LoggerTest();
    }

    @Override
    public void stop() {

    }
}
