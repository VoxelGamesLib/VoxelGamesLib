package me.minidigger.voxelgameslib.bukkit.bossbar;

import javax.inject.Singleton;

import me.minidigger.voxelgameslib.api.bossbar.BossBarStyle;
import me.minidigger.voxelgameslib.api.convert.Converter;

import org.bukkit.boss.BarStyle;

/**
 * Created by Martin on 12.01.2017.
 */
@Singleton
public class BossBarStyleConverter implements Converter<BossBarStyle, BarStyle> {
    @Override
    public BarStyle fromVGL(BossBarStyle bossBarStyle) {
        return BarStyle.valueOf(bossBarStyle.name().replace("SPLIT", "SEGMENTED"));
    }

    @Override
    public BossBarStyle toVGL(BarStyle barStyle) {
        return BossBarStyle.valueOf(barStyle.name().replace("SEGMENTED", "SPLIT"));
    }
}
