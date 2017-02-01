package me.minidigger.voxelgameslib.api.persistence;

import java.util.Optional;
import java.util.UUID;

import me.minidigger.voxelgameslib.api.handler.Handler;
import me.minidigger.voxelgameslib.api.user.User;

/**
 * Created by Martin on 29.01.2017.
 */
public interface PersistenceProvider extends Handler {

    void saveUser(User user);

    Optional<User> loadUser(UUID id);
}
