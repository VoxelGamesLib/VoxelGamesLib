package me.minidigger.voxelgameslib.api.module;

import javax.annotation.Nonnull;

/**
 * Created by Martin on 26.10.2016.
 */
public interface Module {

    void enable();

    void disable();

    @Nonnull
    ModuleInfo getModuleInfo();
    //TODO module javadoc
}
