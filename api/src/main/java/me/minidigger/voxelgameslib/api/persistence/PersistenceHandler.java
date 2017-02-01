package me.minidigger.voxelgameslib.api.persistence;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.handler.Handler;

/**
 * Handles saving and loading of data into numerous formats
 */
@Singleton
public class PersistenceHandler implements Handler {

    private PersistenceProvider activeProvider;

    @Override
    public void start() {
        activeProvider = new HibernatePersistenceProvider();
        activeProvider.start();
    }

    @Override
    public void stop() {
        activeProvider.stop();
    }

    @Nonnull
    public PersistenceProvider getProvider() {
        return activeProvider;
    }
}
