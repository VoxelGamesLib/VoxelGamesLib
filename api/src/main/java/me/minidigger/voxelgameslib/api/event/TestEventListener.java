package me.minidigger.voxelgameslib.api.event;

import me.minidigger.voxelgameslib.api.event.events.VoxelGameLibEnableEvent;
import me.minidigger.voxelgameslib.api.event.events.VoxelGamesLibDisableEvent;

/**
 * Small test eventlistener DEBUG
 */
@EventListener
@SuppressWarnings("JavaDoc")//event listeners don't need javadoc...
public class TestEventListener {
    
    @EventListener
    public void onEnable(VoxelGameLibEnableEvent e) {
        System.out.println("EVENT CALLED!");
    }
    
    @EventListener
    public void onDisable(VoxelGamesLibDisableEvent e) {
        System.out.println("EVENT DISABLED");
    }
    
}
