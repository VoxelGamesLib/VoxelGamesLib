package me.minidigger.voxelgameslib.api.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.role.Role;

/**
 * Simple annotation to represent a command<br>
 * Methods which are annotated with this @interface need to have single {@link CommandArguments}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    /**
     * The name of the command. Can be a root level command or a sub command. Command levels are
     * seperated using a '.'<br> Examples:<br> <code> command.subcommand<br> command<br>
     * command.subcommand.subsubcommand<br> </code>
     *
     * @return The name of the command
     */
    @Nonnull
    String name();

    /**
     * The permission node a player needs to have attached for him to be able to execute this
     * command<br>
     *
     * @return The permission node
     */
    @Nonnull
    String perm();

    /**
     * @return The min role this command requires to be executed.
     */
    @Nonnull
    Role role();

    /**
     * The aliases for this command. The aliases follow the same naming convention listed in {@link
     * #name()}
     *
     * @return The aliases for this command, default is an empty array
     */
    @Nonnull
    String[] aliases() default {};

    /**
     * Whether or not the console should be able to execute this command. By default, this returrns
     * true.<br> If possible, every command should be able to be executed by the console, only if
     * the command uses attributes of the sending player this should be set to false
     *
     * @return Whether or not the console should be able to execute this command.
     */
    boolean allowConsole() default true;

    /**
     * The usage info gets printed if the sender failed to enter the correct arguments. command
     * (in diamond brackets) will be replaced with the used command label
     *
     * @return The usage info for this (sub)command
     */
    @Nonnull
    String usage() default "";

    /**
     * The description is used for the help command. It should be a small one liner to explain what
     * this (sub)command does.
     *
     * @return The description of this (sub)command
     */
    @Nonnull
    String description() default "";

    /**
     * @return the minimum amount of arguments required for this command, -1 for disabled (default).
     */
    int min() default -1;

    /**
     * @return the maximum amount of arguments required for this command, -1 for disabled (default)
     */
    int max() default -1;
}
