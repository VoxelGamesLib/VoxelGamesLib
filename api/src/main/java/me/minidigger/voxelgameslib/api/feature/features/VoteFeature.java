package me.minidigger.voxelgameslib.api.feature.features;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.inject.Inject;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.event.EventListener;
import me.minidigger.voxelgameslib.api.event.events.game.GameJoinEvent;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.lang.Lang;
import me.minidigger.voxelgameslib.api.lang.LangKey;
import me.minidigger.voxelgameslib.api.map.MapInfo;
import me.minidigger.voxelgameslib.api.role.Role;
import me.minidigger.voxelgameslib.api.user.User;
import me.minidigger.voxelgameslib.api.world.WorldConfig;

/**
 * Simple feature that lets ppl vote on maps
 */
public class VoteFeature extends AbstractFeature {
    
    @Inject
    private transient WorldConfig config;
    
    private int maxMaps = 3;
    
    private transient Map<UUID, Integer> votes = new HashMap<>();
    private transient Map<Integer, MapInfo> availableMaps = new HashMap<>();
    
    @Override
    public void start() {
        System.out.println("start vote feature");
        
        String mode = getPhase().getGame().getGameMode().getName();
        int id = 0;
        for (MapInfo info : config.maps) {
            if (info.getGamemodes().contains(mode)) {
                availableMaps.put(id++, info);
            }
            
            if (id == maxMaps - 1) {
                break;
            }
        }
    
        if (availableMaps.size() == 0) {
            getPhase().getGame().broadcastMessage(LangKey.VOTE_NO_MAPS_FOUND);
            getPhase().getGame().endGame();
        }
        
        getPhase().getGame().getPlayers().forEach(this::sendVoteMessage);
    }
    
    @Override
    public void stop() {
        Map<Integer, Integer> votes = new HashMap<>();
        int max = -1;
        int maxMap = -1;
        for (Integer map : this.votes.values()) {
            int old = votes.getOrDefault(map, 0);
            old++;
            if (old > max) {
                max = old;
                maxMap = map;
            }
            votes.put(map, old);
        }
        
        MapInfo winner = availableMaps.get(maxMap);
        if (winner == null) {
            // use first map if nobody won
            winner = availableMaps.values().iterator().next();
        }
        
        getPhase().getGame().putGameData("map", winner);
        getPhase().getGame().broadcastMessage(LangKey.VOTE_END, winner.getName(), winner.getAuthor(), max);
    }
    
    @Override
    public void tick() {
        
    }
    
    @Override
    public void init() {
        
    }
    
    /**
     * Sends the vote message to that user
     *
     * @param user the user that should receive the message
     */
    public void sendVoteMessage(User user) {
        Lang.msg(user, LangKey.VOTE_MESSAGE_TOP);
        for (int id : availableMaps.keySet()) {
            MapInfo info = availableMaps.get(id);
            Lang.msg(user, LangKey.VOTE_MESSAGE_MAP, id, info.getName(), info.getAuthor());
        }
        Lang.msg(user, LangKey.VOTE_MESSAGE_BOT);
    }
    
    @EventListener
    public void onJoin(@Nonnull GameJoinEvent event) {
        if (event.getGame().getUuid().equals(getPhase().getGame().getUuid())) {
            sendVoteMessage(event.getUser());
        }
    }
    
    @CommandInfo(name = "vote", perm = "command.vote", role = Role.DEFAULT)
    public void vote(@Nonnull CommandArguments args) {
        System.out.printf("vote");
        if (args.getNumArgs() == 0) {
            sendVoteMessage(args.getSender());
        } else {
            if (votes.containsKey(args.getSender().getUuid())) {
                Lang.msg(args.getSender(), LangKey.VOTE_ALREADY_VOTED);
            } else {
                int map;
                try {
                    map = Integer.parseInt(args.getArg(0));
                } catch (NumberFormatException ex) {
                    Lang.msg(args.getSender(), LangKey.GENERAL_NOT_A_NUMBER, args.getArg(0));
                    return;
                }
                
                if (availableMaps.get(map) == null) {
                    Lang.msg(args.getSender(), LangKey.VOTE_UNKNOWN_MAP, map);
                    return;
                }
                
                votes.put(args.getSender().getUuid(), map);
                Lang.msg(args.getSender(), LangKey.VOTE_SUBMITTED, map);
            }
        }
    }
    
    @Override
    public Class[] getDependencies() {
        return new Class[0];
    }
}
