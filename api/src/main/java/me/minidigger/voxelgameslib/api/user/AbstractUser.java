package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.config.GlobalConfig;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;

import jskills.Rating;

/**
 * abstract implementation of the user interface that deals with some stuff
 *
 * @param <T> the implementation type
 */
public abstract class AbstractUser<T> implements User<T> {

    private UserData userData;
    private Map<me.minidigger.voxelgameslib.api.game.GameMode, Rating> ratings = new HashMap<>();

    @Inject
    private GlobalConfig config;
    @Inject
    private Injector injector;

    @Nonnull
    @Override
    public UserData getData() {
        return userData;
    }

    @Override
    public void setData(UserData data) {
        this.userData = data;
    }

    @Nonnull
    @Override
    public BaseComponent[] getPrefix() {
        return new ComponentBuilder("PREFIX").create();
    }

    @Nonnull
    @Override
    public BaseComponent[] getSuffix() {
        return new ComponentBuilder("SUFFIX").create();
    }

    @Override
    public boolean hasPermission(@Nonnull Permission perm) {
        if (config.useRoleSystem) {
            return getData().getRole().hasPermission(perm);
        }
        return false;
    }

    /* elo stuff */

    @Nonnull
    @Override
    public Injector getInjector() {
        return injector;
    }

    @Override
    public double getPartialPlayPercentage() {
        return 1.0;
    }

    @Override
    public double getPartialUpdatePercentage() {
        return 1.0;
    }


    @Override
    public Rating getRating(me.minidigger.voxelgameslib.api.game.GameMode mode) {
        Rating rating = ratings.get(mode);
        if (rating == null) {
            rating = mode.getRatingInfo().getDefaultRating();
            // no need to save here
        }
        return rating;
    }

    @Override
    public void saveRating(me.minidigger.voxelgameslib.api.game.GameMode mode, Rating rating) {
        ratings.put(mode, rating);
    }

    @Override
    public Map<me.minidigger.voxelgameslib.api.game.GameMode, Rating> getRatings() {
        return ratings;
    }
}
