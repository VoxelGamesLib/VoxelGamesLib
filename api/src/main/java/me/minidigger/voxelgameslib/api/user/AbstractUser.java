package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.config.GlobalConfig;
import me.minidigger.voxelgameslib.api.lang.Locale;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;

/**
 * abstract implementation of the user interface that deals with some stuff
 *
 * @param <T> the implementation type
 */
public abstract class AbstractUser<T> implements User<T> {

    private Role role;
    private Locale locale = Locale.ENGLISH;

    @Inject
    private GlobalConfig config;
    @Inject
    private Injector injector;

    @Nonnull
    @Override
    public Role getRole() {
        return role;
    }

    @Nonnull
    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(@Nonnull Locale locale) {
        this.locale = locale;
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
            return getRole().hasPermission(perm);
        }
        return false;
    }

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
}
