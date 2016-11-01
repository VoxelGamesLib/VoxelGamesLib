package me.minidigger.voxelgameslib.api.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

/**
 * Created by Martin on 26.10.2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    
    @Nonnull String name();
    
    @Nonnull String[] authors();
    
    @Nonnull String version();
    
    //TODO module info javadoc
}
