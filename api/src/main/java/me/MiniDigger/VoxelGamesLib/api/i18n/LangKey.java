package me.MiniDigger.VoxelGamesLib.api.i18n;

/**
 * Created by Martin on 12.10.2016.
 *
 * Style note: the members needs to be sorted alphabeticly! TODO figure out how to do that..........
 */
public enum LangKey {

    WORLD_CREATOR_IN_USE("The user %s is currently using the world creator!"),
    WORLD_CREATOR_ENTER_NAME("Click here and enter the name of the world that you want to create."),
    WORLD_CREATOR_ENTER_DISPLAY_NAME("Radius set. Click here and enter the display name for the world."),
    WORLD_CREATOR_WRONG_STEP("You are trying to do the wrong step! You are at step %s, you want to do step %s!"),
    WORLD_CREATOR_DONE("Done creating the world! Restart the game to be able to play the map."),
    WORLD_CREATOR_ENTER_AUTHOR("Name set to %s, click here and enter the author"),
    WORLD_CREATOR_EDIT_MODE_ON("Click here to enter map editing mode"),
    WORLD_CREATOR_EDIT_MODE_OFF("Click here when you are done"),
    WORLD_CREATOR_AUTHOR_SET("Author set to %s."),
    WORLD_CREATOR_GAME_MODE_DONE_BUTTON("Done."),
    WORLD_CREATOR_DONE_QUESTIONMARK("Click here if you are done."),
    WORLD_CREATOR_GAME_MODE_ADDED("Added gamemode, press another or press done to continue."),
    WORLD_CREATOR_ENTER_CENTER("World loaded. Walk to the middle of the world and click here to mark it."),
    WORLD_CREATOR_ENTER_RADIUS("Center set. Click here and enter the radius of this world (in which we should search for markers and load chunks and stuff)."),

    GENERAL_NOT_A_NUMBER("%s is not a valid number!"),
    GENERAL_INVALID_ARGUMENT("u w00t m8? %s is not an accepted argument");

    private String defaultValue;

    LangKey(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
