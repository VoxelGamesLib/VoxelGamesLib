package me.minidigger.voxelgameslib.api.command;

import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import lombok.extern.java.Log;
import me.minidigger.voxelgameslib.api.handler.Handler;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.api.role.RoleHandler;
import me.minidigger.voxelgameslib.api.user.ConsoleUser;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.utils.Pair;

/**
 * Handles all commands
 */
@Log
@Singleton
public class CommandHandler implements Handler {

    @Inject
    private RoleHandler roleHandler;
    @Inject
    private Injector injector;

    private final Map<String, Pair<Method, Object>> commandMap = new HashMap<>();
    //private final Map<String, Object> commandExecutors = new HashMap<>();

    private final Map<String, Pair<Method, Object>> completerMap = new HashMap<>();
    // private final Map<String, Object> completerExecutors = new HashMap<>();

    @Override
    public void start() {
        registerCommands();
    }

    @Override
    public void stop() {
        // unregister all whats left
        commandMap.values().forEach(c -> unregister(c.getSecond(), false));
        completerMap.values().forEach(c -> unregister(c.getSecond(), false));

        commandMap.clear();
        completerMap.clear();
    }

    /**
     * Registers all commands in the classpath
     */
    public void registerCommands() {
        Set<Class<?>> excutors = new Reflections().getTypesAnnotatedWith(CommandExecutor.class);
        excutors.forEach(aClass -> register(injector.getInstance(aClass)));
    }

    /**
     * Registers all commands (and completers) in that class.
     *
     * @param object a instance of the class where the command executors life. its the same instance
     *               that will be used to execute the executors
     */
    public void register(@Nonnull Object object) {
        for (Method method : object.getClass().getMethods()) {
            if (method.isAnnotationPresent(CommandInfo.class)) {
                CommandInfo commandInfo = method.getAnnotation(CommandInfo.class);

                if (method.getParameterCount() != 1 || !method.getParameterTypes()[0].equals(CommandArguments.class)) {
                    log.warning("Could not register command " + method.getName() + " in class " + object.getClass().getName() + ": Method may only have a single CommandArguments parameter!");
                    continue;
                }

                registerCommand(commandInfo.name(), commandInfo, method, object);
                for (String alias : commandInfo.aliases()) {
                    registerCommand(alias, commandInfo, method, object);
                }
            } else if (method.isAnnotationPresent(CompleterInfo.class)) {
                CompleterInfo completerInfo = method.getAnnotation(CompleterInfo.class);

                if (method.getParameterCount() != 1 || !method.getParameterTypes()[0].equals(CommandArguments.class)) {
                    log.warning("Could not register completer " + method.getName() + " in class " + object.getClass().getName() + ": Method may only have a single CommandArguments parameter!");
                    continue;
                }

                if (method.getReturnType() != List.class) {
                    log.warning("Could not register completer " + method.getName() + " in class " + object.getClass().getName() + ": Method needs to return a List!");
                    continue;
                }

                registerCompleter(completerInfo.name(), completerInfo, method, object);
                for (String alias : completerInfo.aliases()) {
                    registerCompleter(alias, completerInfo, method, object);
                }
            }
        }
    }

    /**
     * Called when a command was registered. May need to be overridden by implementations. <br>
     * <b>Super always needs to be called!</b><br>
     * Will be called for every alias too
     *
     * @param commandLabel the label of the command, including sub commands
     * @param info         the info annotation of that command
     * @param method       the method that will be called on execution
     * @param object       the instance of the class that has the method
     */
    protected void registerCommand(@Nonnull String commandLabel, @Nonnull CommandInfo info, @Nonnull Method method, @Nonnull Object object) {
        commandMap.put(commandLabel, new Pair<>(method, object));
    }

    /**
     * Called when a completer was registered. May need to be overridden by implementations. <br>
     * <b>Super always needs to be called!</b><br>
     * Will be called for every alias too
     *
     * @param commandLabel the label of the command, including sub commands
     * @param info         the info annotation of that completer
     * @param method       the method that will be called on execution
     * @param object       the instance of the class that has the method
     */
    protected void registerCompleter(@Nonnull String commandLabel, @Nonnull CompleterInfo info, @Nonnull Method method, @Nonnull Object object) {
        completerMap.put(commandLabel, new Pair<>(method, object));
    }

    /**
     * Unregisters all commands (and completers) in that class.
     *
     * @param object a instance of the class where the command executors life. its the same instance
     *               that will be used to execute the executors
     * @param remove if the command should be removed form the command list, used to avoid current
     *               modification exceptions
     */
    public void unregister(@Nonnull Object object, boolean remove) {
        for (Method method : object.getClass().getMethods()) {
            if (method.isAnnotationPresent(CommandInfo.class)) {
                CommandInfo commandInfo = method.getAnnotation(CommandInfo.class);

                if (method.getParameterCount() != 1 || !method.getParameterTypes()[0].equals(CommandArguments.class)) {
                    log.warning("Could not unregister command " + method.getName() + " in class " + object.getClass().getName() + ": Method may only have a single CommandArguments parameter!");
                    continue;
                }

                unregisterCommand(commandInfo.name(), commandInfo, method, object, remove);
                for (String alias : commandInfo.aliases()) {
                    unregisterCommand(alias, commandInfo, method, object, remove);
                }
            } else if (method.isAnnotationPresent(CompleterInfo.class)) {
                CompleterInfo completerInfo = method.getAnnotation(CompleterInfo.class);

                if (method.getParameterCount() != 1 || !method.getParameterTypes()[0].equals(CommandArguments.class)) {
                    log.warning("Could not unregister completer " + method.getName() + " in class " + object.getClass().getName() + ": Method may only have a single CommandArguments parameter!");
                    continue;
                }

                if (method.getReturnType() != List.class) {
                    log.warning("Could not unregister completer " + method.getName() + " in class " + object.getClass().getName() + ": Method needs to return a List!");
                    continue;
                }

                unregisterCompleter(completerInfo.name(), completerInfo, method, object);
                for (String alias : completerInfo.aliases()) {
                    unregisterCompleter(alias, completerInfo, method, object);
                }
            }
        }
    }

    /**
     * Called when a command was unregistered. May need to be overridden by implementations. <br>
     * <b>Super always needs to be called!</b><br>
     * Will be called for every alias too
     *
     * @param commandLabel the label of the command, including sub commands
     * @param info         the info annotation of that command
     * @param method       the method that will be called on execution
     * @param object       the instance of the class that has the method
     * @param remove       if the internal lists should be accessed, used to aviod CMEs
     */
    protected void unregisterCommand(@Nonnull String commandLabel, @Nonnull CommandInfo info, @Nonnull Method method, @Nonnull Object object, boolean remove) {
        if (remove) {
            commandMap.remove(commandLabel);
        }
    }

    /**
     * Called when a completer was unregistered. May need to be overridden by implementations. <br>
     * <b>Super always needs to be called!</b><br>
     * Will be called for every alias too
     *
     * @param commandLabel the label of the command, including sub commands
     * @param info         the info annotation of that completer
     * @param method       the method that will be called on execution
     * @param object       the instance of the class that has the method
     */
    protected void unregisterCompleter(@Nonnull String commandLabel, @Nonnull CompleterInfo info, @Nonnull Method method, @Nonnull Object object) {
        completerMap.remove(commandLabel);
    }

    /**
     * Executes a command.
     *
     * @param sender      the sender who execute the command
     * @param commandLine the command line to execute
     * @return true if the command was handled, false if not (that happens if the command is not
     * registered on this command handler)
     */
    public boolean executeCommand(@Nonnull User sender, @Nonnull String commandLine) {
        commandLine = commandLine.trim();
        // generate arg array
        String[] temp = commandLine.split(" ");
        String label = temp[0];
        commandLine = commandLine.replace(label + " ", "");
        String[] args = commandLine.split(" ");

        // loop through all arguments backwards to find a registered command
        for (int i = args.length; i >= 0; i--) {
            // build command name
            StringBuilder buffer = new StringBuilder();
            buffer.append(label.toLowerCase());
            for (int x = 0; x < i; x++) {
                buffer.append(".").append(args[x].toLowerCase());
            }
            String cmdLabel = buffer.toString();

            // if command exists, execute it
            if (commandMap.containsKey(cmdLabel)) {
                Pair<Method, Object> command = commandMap.get(cmdLabel);
                CommandInfo commandInfo = command.getFirst().getAnnotation(CommandInfo.class);
                Optional<Permission> perm = roleHandler.getPermission(commandInfo.perm());
                if (perm.isPresent() && !sender.hasPermission(perm.get())) {
                    Lang.msg(sender, LangKey.COMMAND_NO_PERMISSION, perm.get().getString());
                    return true;
                }
                if (!commandInfo.allowConsole() && sender instanceof ConsoleUser) {
                    Lang.msg(sender, LangKey.COMMAND_NO_CONSOLE);
                    return true;
                }
                try {
                    // fix arguments
                    int subCommand = cmdLabel.split(Pattern.quote(".")).length - 1;
                    String[] newArgs = new String[args.length - subCommand];
                    System.arraycopy(args, subCommand, newArgs, 0, args.length - subCommand);

                    if (commandInfo.min() != -1 && newArgs.length < commandInfo.min()) {
                        Lang.msg(sender, LangKey.COMMAND_TO_FEW_ARGUMENTS, commandInfo.min(), newArgs.length);
                        // TODO send usage
                        return true;
                    }

                    if (commandInfo.max() != -1 && newArgs.length > commandInfo.max()) {
                        Lang.msg(sender, LangKey.COMMAND_TO_FEW_ARGUMENTS, commandInfo.max(), newArgs.length);
                        // TODO send usage
                        return true;
                    }

                    command.getFirst().invoke(command.getSecond(), new CommandArguments(commandInfo, sender, newArgs));
                } catch (@Nonnull IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }

    //TODO tab completion
}
