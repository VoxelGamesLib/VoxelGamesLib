package me.MiniDigger.VoxelGamesLib.api.command;

import com.google.inject.Singleton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import lombok.extern.java.Log;
import me.MiniDigger.VoxelGamesLib.api.handler.Handler;
import me.MiniDigger.VoxelGamesLib.api.role.Permission;
import me.MiniDigger.VoxelGamesLib.api.role.RoleHandler;
import me.MiniDigger.VoxelGamesLib.api.user.ConsoleUser;
import me.MiniDigger.VoxelGamesLib.api.user.User;

@Log
@Singleton
public class CommandHandler implements Handler {

    @Inject
    private RoleHandler roleHandler;

    private Map<String, Method> commands = new HashMap<>();
    private Map<String, Object> commandExecutors = new HashMap<>();

    private Map<String, Method> completer = new HashMap<>();
    private Map<String, Object> completerExecutors = new HashMap<>();

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        // unregister all whats left
        commandExecutors.values().forEach(this::unregister);
        completerExecutors.values().forEach(this::unregister);

        commands.clear();
        commandExecutors.clear();
        completer.clear();
        commandExecutors.clear();
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
        commands.put(commandLabel, method);
        commandExecutors.put(commandLabel, object);
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
        completer.put(commandLabel, method);
        completerExecutors.put(commandLabel, object);
    }

    /**
     * Unregisters all commands (and completers) in that class.
     *
     * @param object a instance of the class where the command executors life. its the same instance
     *               that will be used to execute the executors
     */
    public void unregister(@Nonnull Object object) {
        for (Method method : object.getClass().getMethods()) {
            if (method.isAnnotationPresent(CommandInfo.class)) {
                CommandInfo commandInfo = method.getAnnotation(CommandInfo.class);

                if (method.getParameterCount() != 1 || !method.getParameterTypes()[0].equals(CommandArguments.class)) {
                    log.warning("Could not unregister command " + method.getName() + " in class " + object.getClass().getName() + ": Method may only have a single CommandArguments parameter!");
                    continue;
                }

                unregisterCommand(commandInfo.name(), commandInfo, method, object);
                for (String alias : commandInfo.aliases()) {
                    unregisterCommand(alias, commandInfo, method, object);
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
     */
    protected void unregisterCommand(@Nonnull String commandLabel, @Nonnull CommandInfo info, @Nonnull Method method, @Nonnull Object object) {
        commands.remove(commandLabel);
        commandExecutors.remove(commandLabel);
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
        completer.remove(commandLabel);
        completerExecutors.remove(commandLabel);
    }

    /**
     * Executes a command.
     *
     * @return true if the command was handled, false if not (that happens if the command is not
     * registered on this command handler)
     */
    public boolean executeCommand(@Nonnull User sender, @Nonnull String commandLine) {
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
            if (commands.containsKey(cmdLabel)) {
                Method commandMethod = commands.get(cmdLabel);
                CommandInfo commandInfo = commandMethod.getAnnotation(CommandInfo.class);
                Optional<Permission> perm = roleHandler.getPermission(commandInfo.perm());
                if (perm.isPresent() && !sender.hasPermission(perm.get())) {
                    //TODO Send no permission message
                    return true;
                }
                if (!commandInfo.allowConsole() && !(sender instanceof ConsoleUser)) {
                    // TODO send no console message
                    return true;
                }
                try {
                    // fix arguments
                    int subCommand = cmdLabel.split(Pattern.quote(".")).length - 1;
                    String[] newArgs = new String[args.length - subCommand];
                    System.arraycopy(args, subCommand, newArgs, 0, args.length - subCommand);

                    commandMethod.invoke(commandExecutors.get(cmdLabel), new CommandArguments(commandInfo, sender, newArgs));
                } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        return false;
    }
}
