package me.minidigger.voxelgameslib.api.feature.features;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.map.Map;
import me.minidigger.voxelgameslib.api.map.MapInfo;
import me.minidigger.voxelgameslib.api.world.WorldHandler;
import me.minidigger.voxelgameslib.libs.net.md_5.bungee.api.chat.TextComponent;

/**
 * Handles loading and unloading of the map for this phase
 */
public class MapFeature extends AbstractFeature {
    
    @Inject
    private transient WorldHandler worldHandler;
    
    private transient Map map;
    private boolean shouldUnload;
    private String mapGameDataKey = "map";
    
    @Override
    public void start() {
        Object object = getPhase().getGame().getGameData(mapGameDataKey);
        if (object == null || !(object instanceof MapInfo)) {
            //TODO cancel game
            getPhase().getGame().broadcastMessage(new TextComponent("YOU ARE ALL DOOMED"));
            getPhase().getGame().endGame();
            return;
        }
        
        try {
            MapInfo mapInfo = (MapInfo) object;
            map = worldHandler.loadMap(mapInfo.getName());
            worldHandler.loadWorld(map);
        } catch (Exception ex) {
            //TODO cancel game
            ex.printStackTrace();
            getPhase().getGame().broadcastMessage(new TextComponent("YOU ARE ALL DOOMED"));
            getPhase().getGame().endGame();
        }
    }
    
    @Override
    public void stop() {
        
    }
    
    @Override
    public void tick() {
        
    }
    
    @Override
    public void init() {
        
    }
    
    /**
     * @return the gamedata key that is used to store the map info for this phase
     */
    public String getMapGameDataKey() {
        return mapGameDataKey;
    }
    
    /**
     * @param mapGameDataKey the gamedata key that is used to store the map info for this phase
     */
    public void setMapGameDataKey(String mapGameDataKey) {
        this.mapGameDataKey = mapGameDataKey;
    }
    
    /**
     * @param shouldUnload if the world should be unloaded after this phase ends
     */
    public void setShouldUnload(boolean shouldUnload) {
        this.shouldUnload = shouldUnload;
    }
    
    /**
     * @return if the world should be unloaded after this phase ends
     */
    public boolean shouldUnload() {
        return shouldUnload;
    }
    
    /**
     *
     * @return the map this phase will be played on
     */
    public @Nonnull Map getMap() {
        return map;
    }
}
