package me.minidigger.voxelgameslib.api.user;

import com.google.inject.Injector;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.user.AsyncUserLoginEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserLeaveEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserLoginEvent;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;

@Singleton
@EventListener
@SuppressWarnings("JavaDoc")// no need for javadoc on event listeners
public class UserListener {
    
    @Inject
    private UserHandler handler;
    @Inject
    private Injector injector;
    
    @EventListener
    public void onAsyncLogin(@Nonnull AsyncUserLoginEvent event) {
        handler.login(event.getUuid());
    }
    
    @EventListener
    public void onLogin(@Nonnull UserLoginEvent event) {
        if (!handler.hasLoggedIn(event.getUuid())) {
            // worst case: load data sync
            handler.login(event.getUuid());
            if (!handler.hasLoggedIn(event.getUuid())) {
                // something went horribly wrong
                event.setCanceled(true);
                // we don't have a locale here since the data was not loaded :/
                event.setKickMessage(Lang.string(LangKey.DATA_NOT_LOADED));
                return;
            }
        }
        
        User user = injector.getInstance(User.class);
        //noinspection unchecked
        user.setImplementationType(event.getPlayerObject());
        handler.join(user);
    }
    
    @EventListener
    public void onLeave(@Nonnull UserLeaveEvent event) {
        handler.logout(event.getUser().getUuid());
    }
}
