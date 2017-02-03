package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.user.AsyncUserLoginEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserJoinEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserLeaveEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserLoginEvent;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.server.Server;

@Singleton
@EventListener
@SuppressWarnings("JavaDoc")// no need for javadoc on event listeners
public class UserListener {

    @Inject
    private UserHandler handler;
    @Inject
    private Injector injector;
    @Inject
    private Server server;

    @EventListener
    public void onAsyncLogin(@Nonnull AsyncUserLoginEvent event) {
        if (!handler.login(event.getUuid())) {
            // something went horribly wrong
            event.setCanceled(true);
            // we don't have a locale here since the data was not loaded :/
            event.setKickMessage(Lang.string(LangKey.DATA_NOT_LOADED));
        }
    }

    @EventListener
    public void onLogin(@Nonnull UserLoginEvent event) {
        if (!handler.hasLoggedIn(event.getUuid())) {
            // worst case: load data sync
            boolean login = handler.login(event.getUuid());
            if (!login || !handler.hasLoggedIn(event.getUuid())) {
                // something went horribly wrong
                event.setCanceled(true);
                // we don't have a locale here since the data was not loaded :/
                event.setKickMessage(Lang.string(LangKey.DATA_NOT_LOADED));
                return;
            }
        }

        handler.join(event.getUuid(), event.getPlayerObject());
    }

    @EventListener
    public void onJoin(@Nonnull UserJoinEvent event) {
        // tp to spawn
        event.getUser().teleport(server.getSpawn().getFirst(), server.getSpawn().getSecond());
    }

    @EventListener
    public void onLeave(@Nonnull UserLeaveEvent event) {
        handler.logout(event.getUser().getUuid());
    }
}
