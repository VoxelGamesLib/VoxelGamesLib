package me.minidigger.voxelgameslib.api.persistence;

import me.minidigger.voxelgameslib.api.handler.Handler;

/**
 * Handles saving and loading of data into numerous formats
 */
public class PersistenceHandler implements Handler {

    private PersistenceProvider activeProvider;

    @Override
    public void start() {
        activeProvider = new HibernatePersistenceProvider();
        activeProvider.start();

        ((HibernatePersistenceProvider) activeProvider).test();
    }

    @Override
    public void stop() {
        activeProvider.stop();
    }
}
