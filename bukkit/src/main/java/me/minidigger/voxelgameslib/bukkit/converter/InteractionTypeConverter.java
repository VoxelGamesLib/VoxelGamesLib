package me.minidigger.voxelgameslib.bukkit.converter;

import me.minidigger.voxelgameslib.api.convert.Converter;
import me.minidigger.voxelgameslib.api.event.events.user.UserInteractEvent;

import org.bukkit.event.block.Action;

/**
 * Created by Martin on 05.02.2017.
 */
public class InteractionTypeConverter implements Converter<UserInteractEvent.Type, Action> {
    @Override
    public Action fromVGL(UserInteractEvent.Type type) {
        switch (type) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                return Action.valueOf(type.name());
            case PRESSURE_PLATE:
                return Action.PHYSICAL;
        }

        throw new RuntimeException("Need more definitions!");
    }

    @Override
    public UserInteractEvent.Type toVGL(Action action) {
        switch (action) {
            case RIGHT_CLICK_BLOCK:
            case RIGHT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
            case LEFT_CLICK_AIR:
                return UserInteractEvent.Type.valueOf(action.name());
            case PHYSICAL:
                return UserInteractEvent.Type.PRESSURE_PLATE;
        }

        throw new RuntimeException("Need more definitions!");
    }
}
