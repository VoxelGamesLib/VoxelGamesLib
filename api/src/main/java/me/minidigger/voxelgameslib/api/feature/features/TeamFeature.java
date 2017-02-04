package me.minidigger.voxelgameslib.api.feature.features;

import me.minidigger.voxelgameslib.api.feature.AbstractFeature;
import me.minidigger.voxelgameslib.api.team.Team;

/**
 * Created by Martin on 28.01.2017.
 */
public class TeamFeature extends AbstractFeature {
    @Override
    public void start() {
        // TODO team feature
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
    public Class[] getDependencies() {
        return new Class[0];
    }

    public Team[] getTeamsOrdered() {
        return new Team[0];
    }
}
