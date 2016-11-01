package me.minidigger.voxelgameslib.api.game;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.VGLEventHandler;
import me.minidigger.voxelgameslib.api.event.events.game.GameJoinEvent;
import me.minidigger.voxelgameslib.api.event.events.game.GameLeaveEvent;
import me.minidigger.voxelgameslib.api.event.events.user.UserLeaveEvent;
import me.minidigger.voxelgameslib.api.utils.ChatUtil;

@EventListener
@SuppressWarnings("ALL")
public class GameListener {
    
    @Inject
    private GameHandler gameHandler;
    @Inject
    private VGLEventHandler eventHandler;
    
    @EventListener
    public void onLeave(@Nonnull UserLeaveEvent event) {
        System.out.println(gameHandler.getGames(event.getUser(), true).size());
        gameHandler.getGames(event.getUser(), true).forEach((game -> game.leave(event.getUser())));
    }
    
    @EventListener
    public void onL(@Nonnull GameLeaveEvent event) {
        System.out.println(ChatUtil.toPlainText(event.getUser().getDisplayName()) + " left the game " + event.getGame().getGameMode().getName());
    }
    
    @EventListener
    public void onJ(@Nonnull GameJoinEvent event) {
        System.out.println(ChatUtil.toPlainText(event.getUser().getDisplayName()) + " joined the game " + event.getGame().getGameMode().getName());
    }
}
