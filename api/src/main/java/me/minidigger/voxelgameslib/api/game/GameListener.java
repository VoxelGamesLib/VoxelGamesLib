package me.minidigger.voxelgameslib.api.game;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.event.events.game.GameJoinEvent;
import me.minidigger.voxelgameslib.api.event.events.game.GameLeaveEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserLeaveEvent;
import me.minidigger.voxelgameslib.api.utils.ChatUtil;

import lombok.extern.java.Log;

@Log
@EventListener
@SuppressWarnings("JavaDoc")
public class GameListener {
    
    @Inject
    private GameHandler gameHandler;
    @Inject
    private VGLEventHandler eventHandler;
    
    @EventListener
    public void onLeave(@Nonnull UserLeaveEvent event) {
        gameHandler.getGames(event.getUser(), true).forEach((game -> game.leave(event.getUser())));
    }
    
    @EventListener
    public void onL(@Nonnull GameLeaveEvent event) {
        log.finer(ChatUtil.toPlainText(event.getUser().getDisplayName()) + " left the game " + event.getGame().getGameMode().getName());
    }
    
    @EventListener
    public void onJ(@Nonnull GameJoinEvent event) {
        log.finer(ChatUtil.toPlainText(event.getUser().getDisplayName()) + " joined the game " + event.getGame().getGameMode().getName());
    }
}
