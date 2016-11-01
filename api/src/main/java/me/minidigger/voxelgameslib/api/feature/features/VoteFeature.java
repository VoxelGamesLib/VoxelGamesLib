package me.minidigger.voxelgameslib.api.feature.features;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;

import me.minidigger.voxelgameslib.api.command.CommandArguments;
import me.minidigger.voxelgameslib.api.command.CommandInfo;
import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.role.Role;

/**
 * Simple feature that lets ppl vote on maps
 */
public class VoteFeature extends AbstractFeature {
    
    private Map<UUID, String> votes = new HashMap<>();
    
    @Override
    public void start() {
        System.out.println("start vote feature");
    }
    
    @Override
    public void stop() {
        
    }
    
    @Override
    public void tick() {
        
    }
    
    @Override
    public void init() {
        System.out.println("init vote feature");
    }
    
    @CommandInfo(name = "vote", perm = "command.vote", role = Role.DEFAULT)
    public void vote(@Nonnull CommandArguments args) {
        System.out.printf("vote");
        if (args.getNumArgs() == 0) {
            
        } else {
            
        }
    }
}
