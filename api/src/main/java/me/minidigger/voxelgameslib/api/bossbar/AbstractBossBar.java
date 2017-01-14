package me.minidigger.voxelgameslib.api.bossbar;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.user.User;

/**
 * Implements some basic boss bar stuff like user handling
 */
public abstract class AbstractBossBar<T> implements BossBar<T> {
    
    private List<User> users = new ArrayList<>();
    
    @Override
    public void addUser(@Nonnull User user) {
        users.add(user);
    }
    
    @Override
    public void removeUser(@Nonnull User user) {
        users.remove(user);
    }
    
    @Override
    public void removeAll() {
        new ArrayList<>(getUsers()).forEach(this::removeUser);
    }
    
    @Nonnull
    @Override
    public List<User> getUsers() {
        return users;
    }
}
