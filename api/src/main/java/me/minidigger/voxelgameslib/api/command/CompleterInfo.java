package me.minidigger.voxelgameslib.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Simple annotation to handle tabcompletion for a command<br> Methods which are annotated with this
 * annotation need to have single {@link CommandArguments} parameter and a {@link java.util.List}<
 * String> as return type
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CompleterInfo {
    
    /**
     * The name of the command that this method should be the completer for. Follows the same naming
     * convention as described in {@link CommandInfo#name()}
     *
     * @return The name of the command
     */
    String name();
    
    /**
     * The aliases of the command that this method should be the completer for. Follows the same
     * naming convention as described in {@link CommandInfo#name()}
     *
     * @return The aliases of the command
     */
    String[] aliases() default {};
}