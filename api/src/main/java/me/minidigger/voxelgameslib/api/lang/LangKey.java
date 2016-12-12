package me.minidigger.voxelgameslib.api.lang;

import javax.annotation.Nonnull;

/**
 * Created by Martin on 12.10.2016.
 * <p>
 * Style note: the members needs to be sorted alphabetical! TODO figure out how to do that..........
 */
public enum LangKey {

    WORLD_CREATOR_IN_USE("The user %s is currently using the world creator!", 1),
    WORLD_CREATOR_ENTER_NAME("Click here and enter the name of the world that you want to create.", 0),
    WORLD_CREATOR_ENTER_DISPLAY_NAME("Radius set. Click here and enter the display name for the world.", 0),
    WORLD_CREATOR_WRONG_STEP("You are trying to do the wrong step! You are at step %s, you want to do step %s!", 2),
    WORLD_CREATOR_DONE("Done creating the world! Restart the game to be able to play the map.", 0),
    WORLD_CREATOR_ENTER_AUTHOR("Name set to %s, click here and enter the author", 1),
    WORLD_CREATOR_EDIT_MODE_ON("Click here to enter map editing mode", 0),
    WORLD_CREATOR_EDIT_MODE_OFF("Click here when you are done", 0),
    WORLD_CREATOR_AUTHOR_SET("Author set to %s. Select the gamemodes this map should be played on.", 1),
    WORLD_CREATOR_GAME_MODE_DONE_BUTTON("Done.", 0),
    WORLD_CREATOR_DONE_QUESTIONMARK("Click here if you are done.", 0),
    WORLD_CREATOR_GAME_MODE_ADDED("Added gamemode, press another or press done to continue.", 0),
    WORLD_CREATOR_ENTER_CENTER("World loaded. Walk to the middle of the world and click here to mark it.", 0),
    WORLD_CREATOR_ENTER_RADIUS("Center set. Click here and enter the radius of this world (in which we should search for markers and load chunks and stuff).", 0),
    WORLD_CREATOR_INFO("TODO: Enter a nice info message about the world creator", 0),//TODO world creator info
    WORLD_CREATOR_MAP_SUMMERY("TODO: Enter a nice summery message %s %s %s %s %s %s", 6),//TODO nice summery about map
    WORLD_CREATOR_SAVE_CONFIG_ERROR("Error while saving the world config, %s:%s", 2),
    WORLD_CREATOR_SAVE_ZIP_ERROR("Error while zipping the world, %s:%s", 2),

    WORLD_UNKNOWN_MAP("Could not find a map called %s :/", 1),

    GENERAL_NOT_A_NUMBER("%s is not a valid number!", 1),
    GENERAL_INVALID_ARGUMENT("u w00t m8? %s is not an accepted argument!", 1),

    LANG_INSTALLED("The following languages are installed and available on this server: %s", 1),
    LANG_USING("You are currently using language %s, use /lang set <language> to set it to another language.", 1),
    LANG_SET("You are now receiving messages in %s!", 1),
    LANG_UNKNOWN("Unknown language %s! Use /lang to see which languages are available!", 1),

    DATA_NOT_LOADED("Your data has not been loaded, please join again!", 0),

    COMMAND_NO_PERMISSION("You don't have the required permission %s to execute this command!", 1),
    COMMAND_NO_CONSOLE("This command can't be executed via console!", 0),
    COMMAND_TO_FEW_ARGUMENTS("You entered to few arguments! Minimum is %s, you entered %s", 2),
    COMMAND_TO_MANY_ARGUMENTS("You entered to many arguments! Maximum is %s, you entered %s", 2),

    EDITMODE_NOT_ENABLED("Edit mode not enabled! Enable to via '/editmode on'", 0),
    EDITMODE_ENABLED("Edit mode enabled!", 0),
    EDITMODE_DISABLED("Edit mode disabled!", 0),
    EDITMODE_ALREADY_ENABLED("Edit mode already enabled!", 0),

    GAME_INSTALLED_GAMEMODES("Installed gamemodes: %s", 1),
    GAME_UNKNOWN_GAMEMODE("Unknown GameMode %s. Is it installed?", 1),
    GAME_PLAYER_JOIN("%s has joined the game", 1),
    GAME_PLAYER_LEAVE("%s has left the game", 1),
    GAME_GAME_STARTED("Game started", 0),
    GAME_CANT_SPECTATE("You can't join this game to spectate right now", 0),

    VOTE_MESSAGE_TOP("You can now vote for a map!", 0),
    VOTE_MESSAGE_MAP("#%s: %s by %s", 3),
    VOTE_MESSAGE_BOT("##############################", 0),
    VOTE_ALREADY_VOTED("You already voted!", 0),
    VOTE_SUBMITTED("Vote for map %s submitted", 1),
    VOTE_UNKNOWN_MAP("Unknown map %s", 1),
    VOTE_END("Map %s by %s won with %s votes", 3),
    VOTE_NO_MAPS_FOUND("Could not find any maps!", 0);

    @Nonnull
    private final String defaultValue;
    private final int args;

    LangKey(@Nonnull String defaultValue, int args) {
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
     * @return the number of arguments this key supports
     */
    public int getArgs() {
        return args;
    }
}
