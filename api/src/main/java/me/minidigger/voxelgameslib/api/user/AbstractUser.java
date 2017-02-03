package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;

import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.config.GlobalConfig;
import me.minidigger.voxelgameslib.api.role.Permission;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.BaseComponent;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.ComponentBuilder;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.chat.ComponentSerializer;

/**
 * abstract implementation of the user interface that deals with some stuff
 *
 * @param <T> the implementation type
 */
public abstract class AbstractUser<T> implements User<T> {

    private UserData userData;

    @Inject
    private GlobalConfig config;
    @Inject
    private Injector injector;

    private BaseComponent[] displayName;

    @Nonnull
    @Override
    public UserData getData() {
        return userData;
    }

    @Override
    public void setData(UserData data) {
        this.userData = data;
    }

    @Override
    public boolean hasPermission(@Nonnull Permission perm) {
        if (config.useRoleSystem) {
            return getData().getRole().hasPermission(perm);
        }
        return false;
    }

    @Override
    public BaseComponent[] getDisplayName() {
        if (displayName == null) {
            displayName = Stream.of(ComponentSerializer.parse(getData().getPrefix()),
                    new ComponentBuilder(getData().getDisplayName()).create(),
                    ComponentSerializer.parse(getData().getPrefix()))
                    .flatMap(Stream::of)
                    .toArray(BaseComponent[]::new);
        }
        return displayName;
    }

    @Override
    public void markDisplayNameAsDirty() {
        displayName = null;
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
