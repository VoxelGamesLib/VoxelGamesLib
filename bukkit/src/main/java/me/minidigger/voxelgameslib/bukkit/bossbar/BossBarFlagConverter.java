package me.minidigger.voxelgameslib.bukkit.bossbar;

import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.bossbar.BossBarModifier;
import me.minidigger.voxelgameslib.api.convert.Converter;

import org.bukkit.boss.BarFlag;

/**
 * Created by Martin on 12.01.2017.
 */
@Singleton
public class BossBarFlagConverter implements Converter<BossBarModifier, BarFlag> {
    @Override
    public BarFlag fromVGL(BossBarModifier bossBarModifier) {
        return BarFlag.valueOf(bossBarModifier.name());
    }
    
    @Override
    public BossBarModifier toVGL(BarFlag barFlag) {
        return BossBarModifier.valueOf(barFlag.name());
    }
}
