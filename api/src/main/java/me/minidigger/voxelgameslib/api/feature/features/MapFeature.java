package me.minidigger.voxelgameslib.api.feature.features;

import com.google.gson.annotations.Expose;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.exception.GameStartException;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.feature.Feature;
import me.minidigger.voxelgameslib.api.map.Map;
import me.minidigger.voxelgameslib.api.map.MapInfo;
import me.minidigger.voxelgameslib.api.world.WorldHandler;

/**
 * Handles loading and unloading of the map for this phase
 */
public class MapFeature extends AbstractFeature {
    
    @Inject
    private WorldHandler worldHandler;
    
    private Map map;
    @Expose
    private boolean shouldUnload;
    @Expose
    private String mapGameDataKey = "map";
    
    @Override
    public void start() {
        Object object = getPhase().getGame().getGameData(mapGameDataKey);
        if (object == null || !(object instanceof MapInfo)) {
            throw new GameStartException(getPhase().getGame().getGameMode(), "No map data was stored!");
        }
        
        try {
            MapInfo mapInfo = (MapInfo) object;
            map = worldHandler.loadMap(mapInfo.getName());
    
            if (!map.isLoaded()) {
                worldHandler.loadWorld(map);
            }
        } catch (Exception ex) {
            throw new GameStartException(getPhase().getGame().getGameMode(), ex);
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
    
    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Feature>[] getDependencies() {
        return new Class[0];
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
     * @return the map this phase will be played on
     */
    @Nonnull
    public Map getMap() {
        return map;
    }
}
