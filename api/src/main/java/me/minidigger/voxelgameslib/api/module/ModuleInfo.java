package me.minidigger.voxelgameslib.api.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Martin on 26.10.2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    
    String name();
    
    String[] authors();
    
    String version();
    
    //TODO module info javadoc
}
