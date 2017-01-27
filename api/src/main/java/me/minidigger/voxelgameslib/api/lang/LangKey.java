package me.minidigger.voxelgameslib.api.lang;

import javax.annotation.Nonnull;

/**
 * Created by Martin on 12.10.2016.
 * <p>
 * Style note: the members needs to be sorted alphabetical!
 */
public enum LangKey {

    COMMAND_NO_PERMISSION("You don't have the required permission {perm} to execute this command!", "perm"),
    COMMAND_NO_CONSOLE("This command can't be executed via console!"),
    COMMAND_TOO_FEW_ARGUMENTS("You entered too few arguments! Minimum is {min}, you entered {actual}", "min", "actual"),
    COMMAND_TOO_MANY_ARGUMENTS("You entered too many arguments! Maximum is {max}, you entered {actual}", "min", "actual"),

    DATA_NOT_LOADED("Your data has not been loaded, please join again!"),

    EDITMODE_ALREADY_ENABLED("Edit mode already enabled!"),
    EDITMODE_NOT_ENABLED("Edit mode not enabled! Enable to via '/editmode on'"),
    EDITMODE_ENABLED("Edit mode enabled!"),
    EDITMODE_DISABLED("Edit mode disabled!"),

    GAME_GAMEMODE_INSTALLED("Installed gamemodes: {modes}", "modes"),
    GAME_GAMEMODE_UNKNOWN("Unknown GameMode, {mode}, Is it installed?", "mode"),
    GAME_PLAYER_JOIN("{name} has joined the game", "name"),
    GAME_PLAYER_LEAVE("{name} has left the game", "name"),
    GAME_CANT_SPECTATE("You can't join this game to spectate right now"),
    GAME_GAME_STARTED("Game started"),
    GAME_END("{aqua}Game ended"),

    GAME_GAMELIST_HEADER("{gold}##### Currently running games ####"),
    GAME_GAMELIST_ENTRY("{aqua}# {yellow}{uuid} {aqua}- {yellow}{mode} {aqua}in phase {yellow}{phase} {aqua}with {yellow}{players}({spectators}) {aqua}players", "uuid", "mode", "phase", "players", "spectators"),
    GAME_GAMELIST_FOOTER("{gold}###############################"),

    GENERAL_INVALID_ARGUMENT("u w00t m8? {arg} is not an accepted argument!", "arg"),
    GENERAL_INVALID_NUMBER("{num} is not a valid number!", "num"),

    LANG_CURRENT("You are currently using language {lang}, use /lang set <language> to set it to another language.", "lang"),
    LANG_INSTALLED("The following languages are installed and available on this server: {langs}", "langs"),
    LANG_UNKNOWN("Unknown language {lang}! Use /lang to see which languages are available!", "lang"),
    LANG_UPDATE("You are now receiving messages in {lang}!", "lang"),
    LANG_NOT_ENABLED("However, {lang} is not enabled on this server. Ask the admins very kindly to enable it!", "lang"),

    LOG_LEVEL_CURRENT("{yellow}The current log level is {level}", "level"),
    LOG_LEVEL_SET("{green}Log level was set to {level}", "level"),
    LOG_LEVEL_UNKNOWN("{red}Unknown log level {level}", "level"),

    VOTE_ALREADY_VOTED("You already voted!"),
    VOTE_END("Map {name} by {author} won with {votes} votes", "name", "author", "votes"),
    VOTE_MESSAGE_TOP("### You can now vote for a map! ###"),
    VOTE_MESSAGE_MAP("#{num}: {name} by {author}", "num", "name", "author"),
    VOTE_MESSAGE_BOT("###############################"),
    VOTE_NO_MAPS_FOUND("{red}Could not find any maps!"),
    VOTE_SUBMITTED("Vote for map {map} submitted", "map"),
    VOTE_UNKNOWN_MAP("Unknown map {map}", "map"),

    WORLD_CREATOR_DONE("Done creating the world! Restart the game to be able to play the map."),
    WORLD_CREATOR_AUTHOR_SET("Author set to {author}. Select the gamemodes this map should be played on.", "author"),
    WORLD_CREATOR_DONE_QUERY("Click here if you are done."),
    WORLD_CREATOR_EDIT_MODE_ON("Click here to enter map editing mode"),
    WORLD_CREATOR_EDIT_MODE_OFF("Click here when you are done"),
    WORLD_CREATOR_ENTER_AUTHOR("Name set to {name}, click here and enter the author", "name"),
    WORLD_CREATOR_ENTER_DISPLAY_NAME("Radius set. Click here and enter the display name for the world."),
    WORLD_CREATOR_ENTER_CENTER("World loaded. Walk to the middle of the world and click here to mark it."),
    WORLD_CREATOR_ENTER_RADIUS("Center set. Click here and enter the radius of this world (in which we should search for markers and load chunks and stuff)."),
    WORLD_CREATOR_ENTER_WORLD_NAME("Click here and enter the name of the world that you want to create."),
    WORLD_CREATOR_GAMEMODE_ADDED("Added gamemode, press another or press done to continue."),
    WORLD_CREATOR_GAMEMODE_DONE_BUTTON("Done."),
    WORLD_CREATOR_INFO("TODO: Enter a nice info message about the world creator"),//TODO world creator info
    WORLD_CREATOR_IN_USE("The user {user} is currently using the world creator!", "user"),
    WORLD_CREATOR_MAP_SUMMARY("TODO: Enter a nice summary message"),//TODO nice summery about map
    WORLD_CREATOR_SAVE_CONFIG_ERROR("Error while saving the world config, {msg}:{exception}", "msg", "exception"),
    WORLD_CREATOR_SAVE_ZIP_ERROR("Error while zipping the world,{msg}:{exception}", "msg", "exception"),
    WORLD_CREATOR_WRONG_STEP("You are trying to do the wrong step! You are at step {step}, you want to do step {entered}!", "step", "entered"),

    WORLD_UNKNOWN_MAP("Could not find a map called {map} :/", "map");

    @Nonnull
    private final String defaultValue;

    @Nonnull
    private final String[] args;


    LangKey(@Nonnull String defaultValue, @Nonnull String... args) {
        this.defaultValue = defaultValue;
        this.args = args;
    }

    /**
     * @return the default value for this lang key, in english
     */
    @Nonnull
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return the arguments that this key requires
     */
    @Nonnull
    public String[] getArgs() {
        return args;
    }

}
